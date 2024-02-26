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
            String result = testExecutionResult.getStatus().toString();
            results.put(testName, result);
        }
    }

    public Map<String, String> getResults() {
        return new HashMap<>(results);
    }

    public void clearResults() {
        results.clear();
    }
}