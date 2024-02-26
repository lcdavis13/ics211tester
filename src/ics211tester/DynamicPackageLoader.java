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
import org.junit.platform.console.ConsoleLauncher;

import ics211tester.tests.DatesTest;

public class DynamicPackageLoader {

    public static void main(String[] args) {
        String assignmentsFolder = "submissions/01"; // Folder containing student submissions

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
    
    private static String toString(Object[] a) {
        if (a == null)
            return "null";

        int iMax = a.length - 1;
        if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        for (int i = 0; ; i++) {
            b.append(String.valueOf(a[i]));
            if (i == iMax)
                return b.toString();
            b.append(" ");
        }
    }

    public static void testStudentPackage(String studentPath) {
        try {
            String classpath = buildClasspathForStudent(studentPath);
            String[] command = buildJavaCommand(classpath, studentPath);
            System.out.println("Testing " + studentPath);
            System.out.println(toString(command));
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.inheritIO().start();
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("Tests successful for " + studentPath);
            } else {
                System.out.println("Tests failed for " + studentPath);
            }
        } catch (Exception e) {
            System.out.println("Error while testing student package: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static String[] buildJavaCommand(String classpath, String studentPath) {
        return new String[]{
                "java",
                "-cp",
                classpath,
                "org.junit.platform.console.ConsoleLauncher",
                "--select-package", "ics211tester.tests",
                // Additional options as necessary, e.g., "--include-classname=.*Test"
        };
    }

    private static String buildClasspathForStudent(String studentPath) {
        // This should include the path to the JUnit Platform Console Launcher,
        // JUnit Jupiter API, JUnit Jupiter Engine, and any other dependencies, 
        // plus the path to the compiled student submission and your test classes.
        return "./src/ics211tester" + File.pathSeparator + studentPath + File.pathSeparator + "./junit-platform-console-standalone.jar";
    }

}
