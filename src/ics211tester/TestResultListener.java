package ics211tester;
import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;

import java.util.HashMap;
import java.util.Map;

public class TestResultListener implements TestExecutionListener {
    private final Map<String, String> results = new HashMap<>();
    private final Map<String, String> descriptions = new HashMap<>();
    private final StringBuilder failedDescription = new StringBuilder();

    @Override
    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
        if (testIdentifier.isTest()) {
            String testName = testIdentifier.getLegacyReportingName();
            String result = testExecutionResult.getStatus().toString();
            String description = testIdentifier.getDisplayName();
			if (testName.equals(description)) {
				description = "";
			}
            results.put(testName, result);
            descriptions.put(testName, description);
            if (testExecutionResult.getStatus() == TestExecutionResult.Status.FAILED && !description.isEmpty()) {
                failedDescription.append(description).append("; ");
            }
        }
    }

    public String getFailedDescriptions() {
        return failedDescription.toString();
    }

    public Map<String, String> getResults() {
        return results;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void clearResults() {
        results.clear();
        descriptions.clear();
        failedDescription.setLength(0); // Clear the StringBuilder
    }
}
