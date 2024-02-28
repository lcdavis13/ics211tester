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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestRunner {

    private static List<String> sortedTestNames = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        String packageName = "ics211tester.tests";
        String csvFile = "test-results.csv";
        String submissionFolder = "submissions/h01/";
        String submissionPackageFolder = "Submission attachment(s)/";
        String packageFolder = "src/edu/ics211/h01/";
        String indexFile = "submission-index.txt";

        // Create the CSV file if it doesn't exist
        if (!Files.exists(Paths.get(csvFile))) {
            Files.createFile(Paths.get(csvFile));
        }

        TestResultListener listener = new TestResultListener();
        File folder = new File(submissionFolder);
        File[] subfolders = folder.listFiles(File::isDirectory);

        // Read the index of the submission to test
        int index = readIndex(indexFile, subfolders.length);

        if (index <= subfolders.length) {
            // Clear previous results
            listener.clearResults();

            try {
                // RUN TEST SUITE FOR PREVIOUSLY COPIED FILES
                String testFolder = (index > 0) ? subfolders[index - 1].getName() : "header row";
                System.out.println("Testing " + testFolder);

                // Run the tests and collect results
                runTests(packageName, listener);

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

                if (index < subfolders.length) {
                    File subfolder = subfolders[index];
                    
	                // COPY FILES FOR NEXT TEST
	                File srcFolder = new File(subfolder, submissionPackageFolder);
	
	                // Copy .java files from subfolder to destination
	                copyJavaFiles(srcFolder, new File(packageFolder));
	
	                // Update the index for the next execution
	                writeIndex(indexFile, index + 1);
	                
	                
	                // WE'RE DONE - WAIT SO ECLIPSE HAS A CHANCE TO DETECT THE NEW FILES
	                
	                // Delay to allow eclipse to detect the new files
	                TimeUnit.SECONDS.sleep(2);
	
	                System.out.println("Copied source files for " + subfolder.getName());
	                System.out.println("=============CONTINUE==============");
                }
	            else {
	                System.out.println("All submissions have been tested.");
	            }
            } 
            catch (Exception e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }
        } 
        else {
            System.out.println("All submissions have been tested.");
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

    private static void writeHeader(String csvFile, String firstColumnName, List<String> testNames) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append(firstColumnName).append(',');
            for (String testName : testNames) {
                writer.append(testName).append(',');
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

    private static void copyJavaFiles(File sourceDir, File destDir) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        // Clean out existing files
        for (File file : destDir.listFiles()) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }

        // Copy new files
        for (File file : sourceDir.listFiles((dir, name) -> name.endsWith(".java"))) {
            Files.copy(file.toPath(), Paths.get(destDir.getPath(), file.getName()));
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
