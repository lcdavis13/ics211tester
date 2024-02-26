package ics211tester;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        	System.out.println(subfolder.getName());
        	
            // Clear previous results
            listener.clearResults();

            try {
	            File srcFolder = new File(subfolder, submissionPackageFolder);
	
//	            // Copy .java files from subfolder to destination
//	            copyJavaFiles(srcFolder, new File(packageFolder));
//	
//	            // Recompile the package
//	            recompilePackage(packageFolder);

	            // Compile the Java files in the submission directory
	            compileJavaFiles(srcFolder, false);
	
	         // Run the tests and collect results
	            runTests(packageName, listener, srcFolder);



	            // Write the header to the CSV file if it's the first subfolder
	            if (subfolder.equals(folder.listFiles(File::isDirectory)[0])) {
	                writeHeader(csvFile, "Submission", listener.getResults().keySet());
	            }

	            // Write the results to the CSV file, including the subfolder name
	            writeResults(csvFile, subfolder.getName(), listener.getResults());
            }
            catch (Exception e)
            {
            	System.out.println("EXCEPTION: " + e.getMessage());
            }
        	//break;
        }
        
        System.out.println("done");
    }
    
    private static void compileJavaFiles(File sourceDir, boolean verbose) throws IOException, InterruptedException {
        // Get the path of the source directory
        String sourceDirPath = sourceDir.getAbsolutePath();

        // List all Java files in the source directory
        File[] javaFiles = sourceDir.listFiles((dir, name) -> name.endsWith(".java"));
        if (javaFiles == null || javaFiles.length == 0) {
            throw new IOException("No Java files found in " + sourceDirPath);
        }

        // Build the command to compile the Java files
        String[] command = new String[javaFiles.length + 1];
        command[0] = "javac";
        for (int i = 0; i < javaFiles.length; i++) {
            command[i + 1] = javaFiles[i].getAbsolutePath();
        }

        // Execute the compilation command
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true); // Merge stdout and stderr
        Process process = builder.start();

        // Read and print the output of the compilation process if verbose is true
        if (verbose) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        }

        // Wait for the compilation process to finish and check the exit code
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("Compilation failed. Exit code: " + exitCode);
        }
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

    private static void runTests(String packageName, TestResultListener listener, File submissionDir) {
        try {
            // Create a URL array with the submission directory
            URL[] urls = {submissionDir.toURI().toURL()};

            // Create a new URLClassLoader with the submission URL
            URLClassLoader classLoader = new URLClassLoader(urls, TestRunner.class.getClassLoader());

            // Get all classes in the package
            File packageDir = new File(submissionDir, packageName.replace('.', '/'));
            if (packageDir.exists()) {
                for (File file : packageDir.listFiles((dir, name) -> name.endsWith(".class"))) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    try {
                        // Load the class and print its name
                        Class<?> cls = classLoader.loadClass(className);
                        System.out.println("Loaded class: " + cls.getName());
                    } catch (ClassNotFoundException e) {
                        System.err.println("Class not found: " + className);
                    }
                }
            }

            // Set the context class loader to the custom class loader
            Thread.currentThread().setContextClassLoader(classLoader);

            LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                    .selectors(selectPackage(packageName))
                    .build();

            Launcher launcher = LauncherFactory.create();
            launcher.registerTestExecutionListeners(listener);

            // Execute the tests
            launcher.execute(request);
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
