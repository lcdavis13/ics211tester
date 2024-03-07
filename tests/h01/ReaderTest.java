package ics211tester.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.io.TempDir;

import edu.ics211.h01.Reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ReaderTest {

    private static Reader reader;

    @BeforeEach
    public void setUp() {
        reader = new Reader();
    }

    private static class TestCase {
        String fileName;
        String content;
        Integer expectedWordCount;
        boolean isNonExistent;

        TestCase(String fileName, String content, Integer expectedWordCount, boolean isNonExistent) {
            this.fileName = fileName;
            this.content = content;
            this.expectedWordCount = expectedWordCount;
            this.isNonExistent = isNonExistent;
        }

        public String toString() {
            return "2.Reader: " + fileName;
        }
    }

    static Stream<TestCase> testCases() {
        return Stream.of(
            new TestCase("instructionExample.txt", "the quick brown fox jumped over the lazy dog", 9, false),
            new TestCase("simpleTest.txt", "a new test case that isn't about a quick brown fox", 11, false),
            new TestCase("emptyFile.txt", "", 0, false),
            //new TestCase("nonexistentfile.txt", null, -1, true),
            new TestCase("file name with spaces.txt", "a new test case that isn't about a quick brown fox", 11, false),
            new TestCase("testWithSpaceEnd.txt", "a test that ends with a space ", 7, false),
            new TestCase("testWithSpaceStart.txt", " a test that starts with a space", 7, false),
            new TestCase("testWithSpaceStartEnd.txt", " a test that starts with a space ", 7, false)
        );
    }

    @ParameterizedTest(name = "{0}: {index}")
    @MethodSource("testCases")
    public void runTests(TestCase testCase, @TempDir Path tempDir) throws IOException {
        Path filePath = tempDir.resolve(testCase.fileName);
        if (!testCase.isNonExistent) {
            if (testCase.content != null) {
                Files.writeString(filePath, testCase.content);
            } else {
                Files.createFile(filePath); // Ensure file exists even if it's empty
            }
        }
        int num = -9;
        try {
        	num = reader.numWords(filePath.toString());
        }
        catch (Exception e)
        {
        	assertEquals(1, 0, "exception " + e);
        }
        
        assertEquals(testCase.expectedWordCount.intValue(), num, "Test Failed for: " + testCase.fileName);
    }
}
