package ics211tester;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import org.eclipse.jdt.core.compiler.CompilationProgress;
import org.eclipse.jdt.core.compiler.batch.BatchCompiler;

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
        	System.out.println(subfolder);
        	
            // Clear previous results
            listener.clearResults();

            try {
                File srcFolder = new File(subfolder, submissionPackageFolder);

                // Copy .java files from subfolder to destination
                copyJavaFiles(srcFolder, new File(packageFolder));
                System.out.println("a");

                // Recompile the package
                recompilePackage(packageFolder);
                System.out.println("a");

                // Recompile the unit tests
                recompileUnitTests();
                System.out.println("a");

                // Run the tests and collect results
                runTests(packageName, listener);
                System.out.println("a");

	            // Write the results to the CSV file, including the subfolder name
	            writeResults(csvFile, subfolder.getName(), listener.getResults());
	            System.out.println("a");
            } catch (Exception e) {
                System.out.println("EXCEPTION: " + e.getMessage());
            }
        }

        
        System.out.println("done");
    }

    private static void recompilePackage(String packageFolder) throws IOException {
        // Compile the .java files in the package folder
        ProcessBuilder builder = new ProcessBuilder("javac", packageFolder + "*.java");
        Process process = builder.start();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private static void recompileUnitTests() throws IOException {
        String unitTestsFolder = "src/ics211tester/tests/";
        // Adjust the path to the ecj.jar file according to your setup
        String ecjJarPath = "path/to/ecj.jar";
        // Path to your Eclipse project's .classpath file
        String eclipseClasspathFile = ".classpath";

        // Extract classpath from Eclipse .classpath file
        String classpath = extractClasspathFromEclipse(eclipseClasspathFile);

        ProcessBuilder builder = new ProcessBuilder("java", "-jar", ecjJarPath, "-classpath", classpath, unitTestsFolder + "*.java");
        builder.redirectErrorStream(true); // Combine stdout and stderr
        Process process = builder.start();

        // Read the output to prevent the buffer from filling up
        try (InputStream inputStream = process.getInputStream()) {
            while (inputStream.read() != -1) {
                // You can log the output if needed
            }
        }

        try {
            if (process.waitFor() != 0) {
                throw new RuntimeException("Compilation of unit tests failed.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            throw new RuntimeException("Compilation of unit tests was interrupted.", e);
        }
    }

    private static String extractClasspathFromEclipse(String eclipseClasspathFile) throws IOException {
        // Read the .classpath file
        Path path = Paths.get(eclipseClasspathFile);
        List<String> lines = Files.readAllLines(path);

        StringBuilder classpath = new StringBuilder();
        for (String line : lines) {
            if (line.contains("kind=\"lib\"")) {
                int pathStart = line.indexOf("path=\"") + 6;
                int pathEnd = line.indexOf("\"", pathStart);
                String libPath = line.substring(pathStart, pathEnd);
                classpath.append(libPath).append(System.getProperty("path.separator"));
            }
        }
        return classpath.toString();
    }











    private static void runTests(String packageName, TestResultListener listener) {
        try {
            File packageDir = new File("src/edu/ics211/h01/");
            File testsDir = new File("src/ics211tester/tests/");
            URL[] urls = {packageDir.toURI().toURL(), testsDir.toURI().toURL()};

            try (URLClassLoader classLoader = new URLClassLoader(urls, TestRunner.class.getClassLoader())) {
                LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                        .selectors(selectPackage(packageName))
                        .build();

                Launcher launcher = LauncherFactory.create();
                launcher.registerTestExecutionListeners(listener);

                Thread.currentThread().setContextClassLoader(classLoader);
                launcher.execute(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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