package ics211tester.tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ics211.h01.HW1;

import java.util.stream.Stream;

public class HW1Test {

    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static List<TestCase> allTestCases;

    @BeforeAll
    public static void runMainOnce(@TempDir Path tempDir) throws IOException {
        allTestCases = testCases();
        String[] args = new String[allTestCases.size()];
        for (int i = 0; i < allTestCases.size(); i++) {
            TestCase testCase = allTestCases.get(i);
            Path filePath = tempDir.resolve(testCase.fileName);
            if (!testCase.isNonExistent) {
                if (testCase.content != null) {
                    Files.writeString(filePath, testCase.content);
                } else {
                    Files.createFile(filePath); // Ensure file exists even if it's empty
                }
            }
            args[i] = filePath.toString(); // Use the absolute path of the file
        }

        System.setOut(new PrintStream(outContent));
        HW1.main(args);
    }

    @AfterAll
    public static void restoreStreams() {
        System.setOut(originalOut);
    }

    static Stream<String> computeDateTimeOutputScenarios() {
        return Stream.of(
            "Jan 1 00:00:00", // For 0 seconds
            "illegal number of seconds" // For -1, 31536000, and 31622400 seconds
            // Add more expected strings based on your specific scenarios
        );
    }

    @ParameterizedTest(name = "HW1: {0}: {index}")
    @MethodSource("computeDateTimeOutputScenarios")
    public void testComputeDateTimeOutput(String expectedOutput) {
        assertTrue(outContent.toString().contains(expectedOutput),
            "Expected to find: {" + expectedOutput + "} as a substring of {" + outContent + "}");
    }

    private static class TestCase {
        String fileName;
        String content;
        String expectedOutputSubstring;
        boolean isNonExistent;

        TestCase(String fileName, String content, String expectedOutputSubstring, boolean isNonExistent) {
            this.fileName = fileName;
            this.content = content;
            this.expectedOutputSubstring = expectedOutputSubstring;
            this.isNonExistent = isNonExistent;
        }

        public String toString() {
            return "Reader: " + fileName;
        }
    }

    static List<TestCase> testCases() {
        return List.of(
            new TestCase("alphabet.txt", "a b c d e f g h i j k l m n o p q r s t u v w x y z", "26", false),
            new TestCase("alphaNums.txt", "a b c d e f g h i j k l m n o p q r s t u v w x y z 0 1 2 3 4 5 6 7 8 9", "36", false),
            new TestCase("nonexistentfile.txt", null, "nonexistentfile.txt not found", true)
        );
    }

    @ParameterizedTest(name = "HW1: {0}: {index}")
    @MethodSource("testCases")
    public void testFileReadingOutput(TestCase testCase) {
        assertTrue(outContent.toString().toLowerCase().contains(testCase.expectedOutputSubstring.toLowerCase()),
            "Didn't find " + testCase.expectedOutputSubstring + " in: " + outContent.toString());
    }
}
