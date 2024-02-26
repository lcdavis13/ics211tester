package ics211tester;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.util.HashMap;
import java.util.Map;

public class TestResultListener implements TestExecutionListener {
    private final Map<String, String> results = new HashMap<>();

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest()) {
            String testName = testIdentifier.getDisplayName();
            String description = parseDescription(testName);
            String result = testExecutionResult.getStatus().toString();
            results.put(description, result);
        }
    }

    private String parseDescription(String testName) {
        // Assuming the description is the part of the test name after the last colon
        int lastColonIndex = testName.lastIndexOf(":");
        return (lastColonIndex != -1) ? testName.substring(lastColonIndex + 2) : testName;
    }

    public Map<String, String> getResults() {
        return results;
    }

    public void clearResults() {
        results.clear();
    }
}
