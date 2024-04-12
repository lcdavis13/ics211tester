package ics211tester.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.ics211.h10.HW10;

public class HW10Test {
	private void assertMatches(String input, String[] substrings) {
        StringBuilder regex = new StringBuilder();
        for (String substring : substrings) {
            // Escape special regex characters in the substring
            String escapedSubstring = substring.replaceAll("([\\Q.^$*+?()[{\\|]\\E])", "\\\\$1");
            // Append the escaped substring followed by arbitrary whitespace
            regex.append(escapedSubstring).append("\\s*");
        }
        assertTrue(input.matches(regex.toString()), "Input does not contain the expected substrings.");
    }
	
	@Test
	void testHW10() {
		String s = "2 2 + 3 * 22 20 - / 4 % 8 ^";
		String[] args = s.split(" ");
		
		String expected = "4 12 2 6 2 256";
		String[] e = expected.split(" ");
		
		String result = HW10.calculate(args);
		assertMatches(result, e);
	}
	
	@Test
	void testHW10neg() {
		String s = "2 2 + -3 * 22 20 - / 4 % 8 ^";
		String[] args = s.split(" ");
		
		String expected = "4 -12 2 -6 -2 256";
		String[] e = expected.split(" ");
		
		String result = HW10.calculate(args);
		assertMatches(result, e);
	}
}
