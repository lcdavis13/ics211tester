package ics211tester;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.lang.reflect.Method;

public class DynamicPackageLoader {

    public static void main(String[] args) {
        String student1Path = "submissions/student1"; // Path to the first student's package
        String student2Path = "submissions/student2"; // Path to the second student's package

        compileAndTestStudentPackage(student1Path);
        compileAndTestStudentPackage(student2Path);
    }

    public static void compileAndTestStudentPackage(String packagePath) {
        try {
            // Compile the student's package
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            int compilationResult = compiler.run(null, null, null, "-d", packagePath, packagePath + "/edu/ics211/h01/Dates.java");

            if (compilationResult == 0) {
                System.out.println("Compilation successful for " + packagePath);

                // Load and test the student's package
                File packageDir = new File(packagePath);
                URL packageURL = packageDir.toURI().toURL();
                URLClassLoader classLoader = new URLClassLoader(new URL[]{packageURL});

                // Load the student's class
                Class<?> studentClass = classLoader.loadClass("edu.ics211.h01.Dates");

                // Get the computeDateTime method from the student's class
                Method computeDateTimeMethod = studentClass.getDeclaredMethod("computeDateTime", long.class, boolean.class);

                // Determine if the method is static
                boolean isStaticMethod = (computeDateTimeMethod.getModifiers() & java.lang.reflect.Modifier.STATIC) != 0;

                // If the method is static, invoke it on the class; otherwise, create an instance and invoke it
                String result;
                if (isStaticMethod) {
                    result = (String) computeDateTimeMethod.invoke(null, 12345L, true);
                } else {
                    Object studentInstance = studentClass.newInstance();
                    result = (String) computeDateTimeMethod.invoke(studentInstance, 12345L, true);
                }

                // You can add assertions or other testing logic here as needed
                System.out.println("computeDateTime result: " + result);

                classLoader.close(); // Close the class loader to release resources
            } else {
                System.out.println("Compilation failed for " + packagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
