package ics211tester;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Set;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestRunner {

    public static void main(String[] args) throws IOException {
        String packageName = "ics211tester.tests";
        String csvFile = "test-results.csv";
        int numberOfRuns = 5;

        // Create the CSV file if it doesn't exist
        if (!Files.exists(Paths.get(csvFile))) {
            Files.createFile(Paths.get(csvFile));
        }

        TestResultListener listener = new TestResultListener();

        for (int i = 0; i < numberOfRuns; i++) {
            // Clear previous results
            listener.clearResults();

            // Run the tests and collect results
            runTests(packageName, listener);

            // Write the results to the CSV file
            if (i == 0) {
                writeHeader(csvFile, listener.getResults().keySet());
            }
            writeResults(csvFile, listener.getResults());

            // Perform any operations between test runs
            performOperations();
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

    private static void writeHeader(String csvFile, Set<String> testNames) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile)) {
            for (String testName : testNames) {
                writer.append(testName).append(',');
            }
            writer.append('\n');
        }
    }

    private static void writeResults(String csvFile, Map<String, String> results) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            for (String result : results.values()) {
                writer.append(result).append(',');
            }
            writer.append('\n');
        }
    }

    private static void performOperations() {
       System.out.println("a");
    }
}
