//package ics211tester.tests;
//
//import org.junit.platform.launcher.TestExecutionListener;
//import org.junit.platform.launcher.TestIdentifier;
//import org.junit.platform.engine.TestExecutionResult;
//import org.junit.platform.launcher.TestPlan;
//
//import java.io.BufferedWriter;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class TestLogger implements TestExecutionListener {
//
//    private static final Path FILE_PATH = Paths.get("test-results.csv");
//    private final Map<String, String> testResults = new LinkedHashMap<>();
//
//    @Override
//    public void testPlanExecutionStarted(TestPlan testPlan) {
//    	System.out.println("hello world");
//        testResults.clear();
//    }
//
//    @Override
//    public void executionFinished(TestIdentifier testIdentifier, TestExecutionResult testExecutionResult) {
//        if (testIdentifier.isTest()) {
//            // Assuming test passed, you can modify this based on the actual result
//            testResults.put(testIdentifier.getDisplayName(), "PASSED");
//        }
//    }
//
//    @Override
//    public void executionStarted(TestIdentifier testIdentifier) {
//    	System.out.println("hello test case");
//    }
//
//    @Override
//    public void testPlanExecutionFinished(TestPlan testPlan) {
//        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, Files.exists(FILE_PATH) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
//            if (Files.size(FILE_PATH) == 0) {
//                // Write header row if file is empty
//                writer.write(String.join(",", testResults.keySet()));
//                writer.newLine();
//            }
//            writer.write(String.join(",", testResults.values()));
//            writer.newLine();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
