package ics211tester.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;

import edu.ics211.h11.BinaryStringTree;

public class HW11Test {
	@Test
	void testBinaryStringTree(@TempDir Path tempDir) {
		fileName = "test.txt";
        Path filePath = tempDir.resolve(fileName);
        if (!testCase.isNonExistent) {
            if (testCase.content != null) {
                Files.writeString(filePath, testCase.content);
            } else {
                Files.createFile(filePath); // Ensure file exists even if it's empty
            }
        }
        
		BinaryStringTree tree = new BinaryStringTree(filePath.toString());
		assertEquals(1, tree.occurrences("hello"));
		assertEquals(2, tree.occurrences("world"));
		assertEquals(0, tree.occurrences("foo"));
		assertEquals(0, tree.occurrences("bar"));
	}
}
