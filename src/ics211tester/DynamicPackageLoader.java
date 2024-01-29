package ics211tester;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;

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
                    
                    // Test the student's package
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

            // Load the student's class
            Class<?> studentClass = classLoader.loadClass("edu.ics211.h01.Dates");

            // Get the computeDateTime method from the student's class
            Method computeDateTimeMethod = null;
            try {
                computeDateTimeMethod = studentClass.getDeclaredMethod("computeDateTime", long.class, boolean.class);
            } catch (NoSuchMethodException e) {
                System.out.println("computeDateTime method not found in " + studentClass.getName());
                return; // Skip testing if method not found
            }

            // Determine if the method is static
            boolean isStaticMethod = (computeDateTimeMethod.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0;

            // If the method is static, invoke it on the class; otherwise, create an instance and invoke it
            String result;
            try {
                if (isStaticMethod) {
                    result = (String) computeDateTimeMethod.invoke(null, 12345L, true);
                } else {
                    Object studentInstance = studentClass.getDeclaredConstructor().newInstance();
                    result = (String) computeDateTimeMethod.invoke(studentInstance, 12345L, true);
                }

                // You can add assertions or other testing logic here as needed
                System.out.println("computeDateTime result: " + result);
            } catch (Exception e) {
                System.out.println("Error while testing " + studentClass.getName() + ": " + e.getMessage());
            } finally {
                classLoader.close(); // Close the class loader to release resources
            }
        } catch (Exception e) {
            System.out.println("Error while testing student package: " + e.getMessage());
        }
    }

}
