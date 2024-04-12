package ics211tester.tests;

import edu.ics211.h05.HW05;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HW05Test {

	String[] first31ternaries;
	String first31ternariesString;
	String[] first31ternariesOffBy1;
	
	String[] outFirst31ternaries;
	String outFirst31ternariesString;
	
	@BeforeEach
	void setUp() throws Exception {
		first31ternaries = new String[]{ "0", "1", "2", "10", "11", "12", "20", "21", "22", "100", "101", "102", "110", "111",
				"112", "120", "121", "122", "200", "201", "202", "210", "211", "212", "220", "221", "222", "1000", "1001",
				"1002", "1010" };
		first31ternariesString = Arrays.toString(first31ternaries);
		first31ternariesOffBy1 = new String[]{ "1", "2", "10", "11", "12", "20", "21", "22", "100", "101", "102", "110", "111",
				"112", "120", "121", "122", "200", "201", "202", "210", "211", "212", "220", "221", "222", "1000", "1001",
				"1002", "1010", "1011" };
		
		outFirst31ternaries = HW05.firstTernaryNumbers(31);
		outFirst31ternariesString = Arrays.toString(outFirst31ternaries);
	}
	
    public static void assertContains(String str, String substr) {
        assertTrue(contains(str, substr), substr + " not in " + str);
    }
    public static boolean contains(String str, String substr) {
        String regex = "(^|\\s|\\p{Punct})" + substr + "($|\\s|\\p{Punct})";
        return str.matches(".*" + regex + ".*");
    }
    
    public static void assertContainsAny(String str, String[] substrArray) {
        assertTrue(containsAny(str, substrArray), "No match in " + str);
    }
	public static boolean containsAny(String str, String[] substrArray) {
        // Join the substrings with '|' to create a single regex pattern
        String joinedSubstr = String.join("|", substrArray);
        // Regular expression to check if any substring is separate
        String regex = "(^|\\s|\\p{Punct})(" + joinedSubstr + ")(\\s|\\p{Punct}|$)";
		return str.matches(".*" + regex + ".*");
	}
    
	public static void assertContainsAll(String str, String[] substrArray) {
		assertTrue(containsAll(str, substrArray), "Not all substrings in " + str);
    }
	public static boolean containsAll(String str, String[] substrArray) {
		for (String substr : substrArray) {
			if (!contains(str, substr)) {
				return false;
			}
		}
		return true;
	}
	

	@Test
	@DisplayName("firstTernaryNumbers(31) gives incorrect result")
	void aaTernary31allTest() {
		assertArrayEquals(outFirst31ternaries, first31ternaries);
	}

	@Test
	@DisplayName("firstTernaryNumbers(31) gives incorrect result")
	void aTernary31sizeTest() {
		assertEquals(outFirst31ternaries.length, first31ternaries.length);
	}

	@Test
	@DisplayName("firstTernaryNumbers(31) gives incorrect result")
	void aTernary31sizeOffBy1Test() {
		assertTrue(outFirst31ternaries.length == first31ternaries.length || outFirst31ternaries.length == first31ternaries.length - 1 || outFirst31ternaries.length == first31ternaries.length + 1);
	}
	
	@Test
	void aTernary31lastElementTest() {
		assertContains(outFirst31ternariesString, first31ternaries[30]);
	}
	
	@Test
	void aTernary31lastElementOffBy1test() {
		assertContainsAny(outFirst31ternariesString, new String[] {first31ternaries[30], first31ternaries[29], first31ternariesOffBy1[30]});
	}
	
	@Test
	void aTernary31AllUnorderedTest() {
		assertContainsAll(outFirst31ternariesString, first31ternaries);
	}
	
	@Test
	void aTernary31AllUnorderedOffBy1Test() {
		boolean result = containsAll(outFirst31ternariesString, first31ternaries) || containsAll(outFirst31ternariesString, first31ternariesOffBy1);
		assertTrue(result, "Not all substrings in " + outFirst31ternariesString);
	}
	
	@Test
	void aTernary31ExceptionTest() {
		assertDoesNotThrow(() -> HW05.firstTernaryNumbers(10));
	}
	
	
	//Reference of how to test main method output
//	private boolean stringMatches(String test, String[] substrings) {
//        String pattern = ".*" + String.join(".*", substrings) + ".*";
//        return test.matches(pattern);
//    }
//
//    private void testMainOutput(String[] args, int numExpected) {
//        // Redirect standard output to capture the output of main
//        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outContent));
//
//        // Run the main method with the given arguments
//        edu.ics211.h09.HW09.main(args);
//
//        // Restore standard output
//        System.setOut(System.out);
//
//        // Construct the expectedSubstrings array with args.length copies of the regex expression
//        String[] expectedSubstrings = new String[args.length];
//        for (int i = 0; i < numExpected; i++) {
//            expectedSubstrings[i] = "(Syntax error|Valid syntax)";
//        }
//
//        // Check if the output contains args.length repetitions of the regex expression
//        assertTrue(stringMatches(outContent.toString(), expectedSubstrings),
//                   "The output does not contain the expected number of repetitions.");
//    }
}
