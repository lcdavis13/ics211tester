package ics211tester;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestRunner {

    public static void main(String[] args) throws IOException {
        String packageName = "ics211tester.tests";
        String csvFile = "test-results.csv";
        String submissionFolder = "submissions/h01/"; 
        String submissionPackageFolder = "Submission attachment(s)/";
        String packageFolder = "src/edu/ics211/h01/"; 

        // Create the CSV file if it doesn't exist
        if (!Files.exists(Paths.get(csvFile))) {
            Files.createFile(Paths.get(csvFile));
        }

        TestResultListener listener = new TestResultListener();
        File folder = new File(submissionFolder);

        // Iterate through each subfolder
        for (File subfolder : folder.listFiles(File::isDirectory)) {
            // Clear previous results
            listener.clearResults();
            
            File srcFolder = new File(subfolder, submissionPackageFolder);

            // Copy .java files from subfolder to destination
            copyJavaFiles(srcFolder, new File(packageFolder));

            // Run the tests and collect results
            runTests(packageName, listener);

            // Write the header to the CSV file if it's the first subfolder
            if (subfolder.equals(folder.listFiles(File::isDirectory)[0])) {
                writeHeader(csvFile, "Submission", listener.getResults().keySet());
            }

            // Write the results to the CSV file, including the subfolder name
            writeResults(csvFile, subfolder.getName(), listener.getResults());
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

    private static void writeHeader(String csvFile, String firstColumnName, Set<String> testNames) throws IOException {
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
            writer.append(subfolderName).append(',');
            for (String result : results.values()) {
                writer.append(result).append(',');
            }
            writer.append('\n');
        }
    }

    private static void copyJavaFiles(File sourceDir, File destDir) throws IOException {
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        
        //clean out existing files
        for(File file: destDir.listFiles()) 
            if (!file.isDirectory()) 
                file.delete();

        //copy new files
        for (File file : sourceDir.listFiles((dir, name) -> name.endsWith(".java"))) {
//        	System.out.println(System.getProperty("user.dir"));
//        	System.out.println(file.getPath());
//        	System.out.println(destDir.getPath());
            Files.copy(file.toPath(), Paths.get(destDir.getPath(), file.getName()));
        }
    }
}
