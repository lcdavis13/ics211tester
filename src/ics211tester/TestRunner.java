package ics211tester;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.*;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class TestRunner {

    public static void main(String[] args) throws IOException {
        String packageName = "ics211tester.tests";
        String csvFile = "test-results.csv";
        int numberOfRuns = 5;

        // Create the CSV file and write the header
        Set<String> testNames = getTestNames(packageName);
        writeHeader(csvFile, testNames);

        for (int i = 0; i < numberOfRuns; i++) {
            // Run the tests and collect results
            TestResultListener listener = runTests(packageName, testNames);

            // Write the results to the CSV file
            writeResults(csvFile, listener.getResults());
            
            System.out.println("a");
        }
    }

    private static Set<String> getTestNames(String packageName) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage(packageName))
                .build();

        Launcher launcher = LauncherFactory.create();
        TestPlan testPlan = launcher.discover(request);

        Set<String> testNames = new LinkedHashSet<>();
        testPlan.getRoots().forEach(root -> testPlan.getDescendants(root).forEach(descendant -> {
            if (descendant.isTest()) {
                testNames.add(descendant.getDisplayName());
            }
        }));

        return testNames;
    }

    private static void writeHeader(String csvFile, Set<String> testNames) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile)) {
            for (String testName : testNames) {
            	System.out.println(testName);
                writer.append(testName).append(',');
            }
            writer.append('\n');
        }
    }

    private static TestResultListener runTests(String packageName, Set<String> testNames) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectPackage(packageName))
                .build();

        Launcher launcher = LauncherFactory.create();
        TestResultListener listener = new TestResultListener(testNames);
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        return listener;
    }

    private static void writeResults(String csvFile, Set<String> results) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile, true)) {
            for (String result : results) {
                writer.append(result).append(',');
            }
            writer.append('\n');
        }
    }

    static class TestResultListener implements TestExecutionListener {
        private final Set<String> testNames;
        private final Set<String> results;

        public TestResultListener(Set<String> testNames) {
            this.testNames = new LinkedHashSet<>(testNames);
            this.results = new LinkedHashSet<>();
        }

        @Override
        public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
            if (testIdentifier.isTest()) {
                String displayName = testIdentifier.getDisplayName();
                String result = displayName + ": " + testExecutionResult.getStatus().toString();
                results.add(result);
            }
        }

        public Set<String> getResults() {
            return results;
        }
    }
}
