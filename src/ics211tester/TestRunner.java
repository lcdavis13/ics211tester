//Enabling Automatic File Refresh in Eclipse
//To set auto-refresh, go to window → preferences → general → workspace and check the "Refresh using native hooks or polling" check-box.

package ics211tester;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestRunner {

	private static List<String> sortedTestNames = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String assignment = "h06";
        String[] filenames = {"SortedArrayList.java"};

        boolean breakAfterTest = false;
        String packageName = "edu.ics211." + assignment;
        String testPackageName = "ics211tester.tests";
        String csvFile = "test-results.csv";
        String submissionFolder = "submissions/" + assignment + "/";
        String submissionPackageFolder = "Submission attachment(s)/";
        String packageFolder = "src/edu/ics211/" + assignment + "/";
        String statusFile = "submission-status.txt"; // File to keep track of the index and the last action

        boolean write_descriptions = true;

        // Create the CSV file if it doesn't exist
        if (!Files.exists(Paths.get(csvFile))) {
            Files.createFile(Paths.get(csvFile));
        }

        TestResultListener listener = new TestResultListener();
        File folder = new File(submissionFolder);
        File[] subfolders = folder.listFiles(File::isDirectory);

        // Read the index and the last action from the status file
        Status status = readStatus(statusFile, subfolders.length);

        if (status.index <= subfolders.length) {
            // Clear previous results
            listener.clearResults();

            try {
            	boolean do_test = "test".equals(status.nextAction) || status.nextAction.isEmpty();
            	boolean do_copy = "copy".equals(status.nextAction) || breakAfterTest == false;
            	
                // Determine the action based on the last action
                 if (do_test) {
                     // RUN TEST SUITE FOR PREVIOUSLY COPIED FILES
                     String testFolder = (status.index > 0) ? subfolders[status.index - 1].getName() : "header row";
                     System.out.println("Testing " + testFolder);

                     // Run the tests and collect results
                     runTests(testPackageName, listener);

                     // Write the header to the CSV file if it's the first subfolder
                     if (status.index == 0) {
                         Set<String> testNames = listener.getResults().keySet();
                         sortedTestNames.addAll(new TreeSet<>(testNames));
                         writeHeader(csvFile, "Submission", "Notes", sortedTestNames);
                         
						if (write_descriptions) {
                             writeDescriptions(csvFile, listener.getDescriptions());
                         }
                     } else {
                         // Load sorted test names from the CSV file
                         loadSortedTestNames(csvFile);
                         // Write the results to the CSV file, using the previous subfolder name
                         writeResults(csvFile, testFolder, listener.getResults(), listener.getFailedDescriptions());
                     }

                     // Update the last action to "testing"
                     status.nextAction = "copy";
                     writeStatus(statusFile, status);

                     System.out.println("Testing completed for " + testFolder);
                 }
                 if (do_copy) {
                     // COPY FILES FOR NEXT TEST
                     if (status.index < subfolders.length) {
                         File subfolder = subfolders[status.index];

                         File srcFolder = new File(subfolder, submissionPackageFolder);

                         // Copy .java files from subfolder to destination
                         copyJavaFiles(srcFolder, new File(packageFolder), filenames);
                         changePackageDeclarations(new File(packageFolder), packageName);
                         makeCertainThingsPublic(new File(packageFolder), new String[]{"String sortChars", "SortType", "void swap"});

                         // Update the index for the next execution
                         status.index++;
                         status.nextAction = "test";
                         writeStatus(statusFile, status);

                         // Delay to allow eclipse to detect the new files
                         TimeUnit.SECONDS.sleep(2);

                         System.out.println("Copied source files for " + subfolder.getName());
                         System.out.println("=============RUN TESTS==============");
                     } else {
                         System.out.println("All submissions have been tested.");
                     }
                 }
            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }
        } else {
            System.out.println("All submissions have been tested.");
        }
    }

    // Method to read the status (index and last action) from the file
    private static Status readStatus(String statusFile, int maxIndex) throws IOException {
        Path path = Paths.get(statusFile);
        if (!Files.exists(path)) {
            Files.createFile(path);
            return new Status(0, "");
        } else {
            String content = new String(Files.readAllBytes(path), StandardCharsets.UTF_8).trim();
            String[] parts = content.split(";");
            try {
                int index = Integer.parseInt(parts[0]);
                String lastAction = parts.length > 1 ? parts[1] : "";
                return new Status(Math.min(index, maxIndex), lastAction);
            } catch (NumberFormatException e) {
                return new Status(0, "");
            }
        }
    }

    // Method to write the status (index and last action) to the file
    private static void writeStatus(String statusFile, Status status) throws IOException {
        String content = status.index + ";" + status.nextAction;
        Files.write(Paths.get(statusFile), content.getBytes(StandardCharsets.UTF_8));
    }

    // Status class to hold the index and last action
    private static class Status {
        int index;
        String nextAction;

        Status(int index, String lastAction) {
            this.index = index;
            this.nextAction = lastAction;
        }
    }


    private static void runTests(String packageName, TestResultListener listener) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage(packageName))
                .build();

        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }

    private static void writeHeader(String csvFile, String firstColumnName, String lastColumnName, List<String> testNames) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append(firstColumnName + ",");
            for (int i = 0; i < testNames.size(); i++) {
                writer.append(testNames.get(i));
                writer.append(',');
            }
            writer.append(lastColumnName + '\n');
        }
    }


    private static void writeResults(String csvFile, String subfolderName, Map<String, String> results, String failedDescriptions) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append("\"").append(subfolderName).append("\"").append(',');
            //StringBuilder failedTestsDesc = new StringBuilder();
            for (String testName : sortedTestNames) {
                String result = results.getOrDefault(testName, "");
                if (result.contains("FAILED")) {
                    result = "0";
                    //failedTestsDesc.append(failedDescriptions.get(testName)).append("; ");
                } else if (result.contains("SUCCESSFUL")) {
                    result = "1";
                }
                writer.append(result).append(',');
            }
            writer.append("\"").append(failedDescriptions.trim()).append("\"").append('\n');
        }
    }
    

    private static void writeDescriptions(String csvFile, Map<String, String> results) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append(',');
            for (String testName : sortedTestNames) {
                String result = results.getOrDefault(testName, "");
                writer.append("\"").append(result).append("\"").append(',');
            }
            writer.append('\n');
        }
    }


    private static void copyJavaFiles(File sourceDir, File destDir, String[] filenames) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        // Clean out existing files
        for (File file : destDir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        // Convert filenames array to a list for easier checking
        List<String> filenameList = Arrays.asList(filenames);

        // Check if the source directory contains all the filenames
        boolean containsAllFilenames = containsAllFilenames(sourceDir, filenameList);

        // Copy new files
        copyJavaFilesRecursively(sourceDir, destDir, filenameList, containsAllFilenames);
    }

    private static void copyJavaFilesRecursively(File sourceDir, File destDir, List<String> filenames, boolean copyAll) throws IOException {
        for (File file : sourceDir.listFiles()) {
            if (file.isDirectory()) {
                // Check if this directory contains all the filenames
                boolean containsAllFilenames = containsAllFilenames(file, filenames);
                // Recursively search in subdirectories
                copyJavaFilesRecursively(file, destDir, filenames, containsAllFilenames || copyAll);
            } else if (copyAll || (file.getName().endsWith(".java") && filenames.contains(file.getName()))) {
                // Copy file if we are in the correct directory or if it's a Java file and its name is in the list
                Files.copy(file.toPath(), Paths.get(destDir.getPath(), file.getName()), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }


    private static boolean containsAllFilenames(File directory, List<String> filenames) {
        // Get all Java files in the directory
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".java"));
        if (files == null) {
            return false;
        }

        // Convert to a list of filenames
        List<String> fileNamesInDir = Arrays.stream(files).map(File::getName).collect(Collectors.toList());

        // Check if the directory contains all filenames
        return fileNamesInDir.containsAll(filenames);
    }
    
    public static void changePackageDeclarations(File folder, String newPackage) {
        //File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        String updatedContent = replacePackageDeclaration(content, newPackage);

                        if (!updatedContent.equals(content)) {
                            Files.write(file.toPath(), updatedContent.getBytes());
                            System.out.println("Updated package declaration in: " + file.getName());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String replacePackageDeclaration(String content, String newPackage) {
        Pattern pattern = Pattern.compile("^\\s*package\\s+.*?;", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) {
            return matcher.replaceFirst("package " + newPackage + ";");
        } else {
            return "package " + newPackage + ";\n\n" + content;
        }
    }
    
    public static void makeCertainThingsPublic(File folder, String[] functionNames) {
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    try {
                        String content = new String(Files.readAllBytes(file.toPath()));
                        String updatedContent = replacePrivateDeclaration(content, functionNames);

                        if (!updatedContent.equals(content)) {
                            Files.write(file.toPath(), updatedContent.getBytes());
                            System.out.println("Made some things public in: " + file.getName());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static String replacePrivateDeclaration (String content, String[] declarations) {
    	// Escape special characters in declaration names for regex
        String[] escapedDeclarations = new String[declarations.length];
        for (int i = 0; i < declarations.length; i++) {
            escapedDeclarations[i] = Pattern.quote(declarations[i]);
        }

        // Build a regex pattern to match the function, class, and enum declarations
        String regex = "(?m)^(\\s*)((private|protected)?\\s*)(static\\s+)?((class|enum)\\s+)?(\\w+\\s+)?(" + String.join("|", escapedDeclarations) + ")(\\s*\\(|\\s+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            // Replace the access modifier with "public" or add "public" if not present
            String replacement = matcher.group(1) + "public " + (matcher.group(4) != null ? matcher.group(4) : "") + (matcher.group(5) != null ? matcher.group(5) : "") + (matcher.group(7) != null ? matcher.group(7) : "") + matcher.group(8) + matcher.group(9);
            matcher.appendReplacement(sb, replacement);
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    
    private static void loadSortedTestNames(String csvFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            if (header != null) {
                // Split the header and remove the first column (Submission) and the last column (Failed Test Descriptions)
                String[] names = header.split(",");
                sortedTestNames = Arrays.asList(Arrays.copyOfRange(names, 1, names.length - 1));
            }
        }
    }

}
