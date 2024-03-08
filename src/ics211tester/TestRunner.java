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
	    String assignment = "h03";
	    String[] filenames = {"EdiblePlant.java"};
	    
	    String packageName = "edu.ics211." + assignment;
	    String testPackageName = "ics211tester.tests";
	    String csvFile = "test-results.csv";
	    String submissionFolder = "submissions/" + assignment + "/";
	    String submissionPackageFolder = "Submission attachment(s)/";
	    String packageFolder = "src/edu/ics211/" + assignment + "/";
	    String indexFile = "submission-index.txt";
	    String lastActionFile = "last-action.txt";  // File to keep track of the last action

	    // Create the CSV file if it doesn't exist
	    if (!Files.exists(Paths.get(csvFile))) {
	        Files.createFile(Paths.get(csvFile));
	    }

	    TestResultListener listener = new TestResultListener();
	    File folder = new File(submissionFolder);
	    File[] subfolders = folder.listFiles(File::isDirectory);

	    // Read the index of the submission to test
	    int index = readIndex(indexFile, subfolders.length);

	    // Read the last action from the file
	    String lastAction = readLastAction(lastActionFile);

	    if (index <= subfolders.length) {
	        // Clear previous results
	        listener.clearResults();

	        try {
	            // Determine the action based on the last action
	            if ("copying".equals(lastAction) || lastAction.isEmpty()) {
	                // RUN TEST SUITE FOR PREVIOUSLY COPIED FILES
	                String testFolder = (index > 0) ? subfolders[index - 1].getName() : "header row";
	                System.out.println("Testing " + testFolder);

	                // Run the tests and collect results
	                runTests(testPackageName, listener);

	                // Write the header to the CSV file if it's the first subfolder
	                if (index == 0) {
	                    Set<String> testNames = listener.getResults().keySet();
	                    sortedTestNames.addAll(new TreeSet<>(testNames));
	                    writeHeader(csvFile, "Submission", sortedTestNames);
	                } else {
	                    // Load sorted test names from the CSV file
	                    loadSortedTestNames(csvFile);
	                    // Write the results to the CSV file, using the previous subfolder name
	                    writeResults(csvFile, testFolder, listener.getResults());
	                }

	                // Update the last action to "testing"
	                writeLastAction(lastActionFile, "testing");

	                System.out.println("Testing completed for " + testFolder);
	            } else if ("testing".equals(lastAction)) {
	                // COPY FILES FOR NEXT TEST
	                if (index < subfolders.length) {
	                    File subfolder = subfolders[index];

	                    File srcFolder = new File(subfolder, submissionPackageFolder);

	                    // Copy .java files from subfolder to destination
	                    copyJavaFiles(srcFolder, new File(packageFolder), filenames);
	                    changePackageDeclarations(new File(packageFolder), packageName);

	                    // Update the index for the next execution
	                    writeIndex(indexFile, index + 1);

	                    // Update the last action to "copying"
	                    writeLastAction(lastActionFile, "copying");

	                    // Delay to allow eclipse to detect the new files
	                    TimeUnit.SECONDS.sleep(2);

	                    System.out.println("Copied source files for " + subfolder.getName());
	                    System.out.println("=============CONTINUE==============");
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

	// Method to read the last action from the file
	private static String readLastAction(String lastActionFile) throws IOException {
	    Path path = Paths.get(lastActionFile);
	    if (Files.exists(path)) {
	        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8).trim();
	    }
	    return "";
	}

	// Method to write the last action to the file
	private static void writeLastAction(String lastActionFile, String action) throws IOException {
	    Files.write(Paths.get(lastActionFile), action.getBytes(StandardCharsets.UTF_8));
	}


    private static void runTests(String packageName, TestResultListener listener) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage(packageName))
                .build();

        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);
    }

    private static void writeHeader(String csvFile, String firstColumnName, List<String> testNames) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append(firstColumnName);
            if (!testNames.isEmpty()) {
                writer.append(',');
            }
            for (int i = 0; i < testNames.size(); i++) {
                writer.append(testNames.get(i));
                if (i < testNames.size() - 1) {
                    writer.append(',');
                }
            }
            writer.append('\n');
        }
    }


    private static void writeResults(String csvFile, String subfolderName, Map<String, String> results) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            writer.append("\"").append(subfolderName).append("\"").append(',');
            for (String testName : sortedTestNames) {
                String result = results.getOrDefault(testName, "");
                writer.append(result).append(',');
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

        // Copy new files
        copyJavaFilesRecursively(sourceDir, destDir, filenameList, false);
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


    private static int readIndex(String indexFile, int maxIndex) throws IOException {
        Path path = Paths.get(indexFile);
        if (!Files.exists(path)) {
            Files.createFile(path);
            return 0;
        } else {
            String content = Files.readString(path);
            try {
                int index = Integer.parseInt(content);
                return Math.min(index, maxIndex);
            } catch (NumberFormatException e) {
                return 0;
            }
        }
    }

    private static void writeIndex(String indexFile, int index) throws IOException {
        Files.writeString(Paths.get(indexFile), String.valueOf(index));
    }
    
    private static void loadSortedTestNames(String csvFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String header = reader.readLine();
            if (header != null) {
                String[] names = header.split(",");
                sortedTestNames = Arrays.asList(Arrays.copyOfRange(names, 1, names.length));
            }
        }
    }
}
