package ics211tester;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.engine.discovery.ClassNameFilter;
import java.util.Arrays;

import ics211tester.tests.DatesTest;

public class DynamicPackageLoader {

    public static void main(String[] args) {
        String assignmentsFolder = "submissions"; // Folder containing student submissions

        // Check if the "assignments" folder exists
        File assignmentsDir = new File(assignmentsFolder);
        if (!assignmentsDir.exists() || !assignmentsDir.isDirectory()) {
            System.err.println("The 'assignments' folder does not exist or is not a directory.");
            return;
        }

        // List all subfolders (student submissions) in the "assignments" folder
        File[] studentSubmissions = assignmentsDir.listFiles(File::isDirectory);

        if (studentSubmissions != null) {
            for (File studentFolder : studentSubmissions) {
                if (studentFolder.isDirectory()) {
                    String studentPath = studentFolder.getPath();
                    
                    // Compile the student's package
                    compileStudentPackage(studentPath);
                    
                    // Test the student's package using JUnit 5
                    testStudentPackage(studentPath);
                }
            }
        } else {
            System.err.println("No student submissions found in the 'assignments' folder.");
        }
    }

    public static void compileStudentPackage(String packagePath) {
        try {
            // Compile the student's package
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compilationResult = compiler.run(null, null, null, "-d", packagePath, packagePath + "/edu/ics211/h01/Dates.java");

            if (compilationResult == 0) {
                System.out.println("Compilation successful for " + packagePath);
            } else {
                System.out.println("Compilation failed for " + packagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void testStudentPackage(String packagePath) {
        try {
            // Load and test the student's package
            File packageDir = new File(packagePath);
            URL packageURL = packageDir.toURI().toURL();
            URLClassLoader classLoader = new URLClassLoader(new URL[]{packageURL});

            // Use JUnit 5 Launcher to run the JUnit 5 test class
            Launcher launcher = LauncherFactory.create();
            SummaryGeneratingListener listener = new SummaryGeneratingListener();
            launcher.registerTestExecutionListeners(listener);

            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
                    .request()
                    .selectors(
                            DiscoverySelectors.selectClass(DatesTest.class), // Add more test classes as needed
                            DiscoverySelectors.selectPackage("ics211tester.tests") // Replace with your package prefix
                    )
                    .build();

            launcher.execute(request);

            // Report test results
            TestExecutionSummary summary = listener.getSummary();
            
         // Create a PrintWriter from System.out
            PrintWriter printWriter = new PrintWriter(System.out);

            // Print the summary to the PrintWriter
            summary.printTo(printWriter);

            // Flush and close the PrintWriter to ensure output is displayed
            printWriter.flush();
            printWriter.close();

            classLoader.close(); // Close the class loader to release resources
        } catch (Exception e) {
            System.out.println("Error while testing student package: " + e.getMessage());
        }
    }
}
