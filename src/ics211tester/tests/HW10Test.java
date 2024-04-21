package ics211tester.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import edu.ics211.h10.StackCalculator;

public class HW10Test {
	
	@BeforeEach
	void setUp() {
	}
	
	private void assertMatches(String input, String[] substrings) {
        int fromIndex = 0;

        for (String substring : substrings) {
            // Find the index of the current substring starting from 'fromIndex'
            int index = input.indexOf(substring, fromIndex);

            // If the substring is not found, or the order is incorrect, throw an assertion error
            assertTrue(index != -1, "Substring '" + substring + "' not found in the correct order in " + input);

            // Move the starting index forward to the end of the current found substring
            fromIndex = index + substring.length();
        }
    }



	
	
	@Test
	void CoreTest() {
		String s1 = "13 2 + 5 * 11 7 - / 15 % 3 ^";
		String s2 = "13 2 ^ 5 - 11 7 * % 15 / 3 +";
		
		String e1 = "15 75 4 18 3";
		String e2 = "169 164 77 10 0";
		
		String[] args1 = s1.split(" ");
		String[] args2 = s2.split(" ");
		String[] match1 = e1.split(" ");
		String[] match2 = e2.split(" ");
		StackCalculator stackCalculator1 = new StackCalculator();
		StackCalculator stackCalculator2 = new StackCalculator();
		String result1 = stackCalculator1.calculate(args1);
		String result2 = stackCalculator2.calculate(args2);
		assertMatches(result1, match1);
		assertMatches(result2, match2);
	}

	@Test
	@DisplayName("don't skip final input")
	void FullLengthTest() {
		String s1 = "13 2 + 5 * 11 7 - / 15 % 3 ^";
		String s2 = "13 2 ^ 5 - 11 7 * % 15 / 3 +";
		
		String e1 = "15 75 4 18 3 27";
		String e2 = "169 164 77 10 0 3";
		
		String[] args1 = s1.split(" ");
		String[] args2 = s2.split(" ");
		String[] match1 = e1.split(" ");
		String[] match2 = e2.split(" ");
		StackCalculator stackCalculator1 = new StackCalculator();
		StackCalculator stackCalculator2 = new StackCalculator();
		String result1 = stackCalculator1.calculate(args1);
		String result2 = stackCalculator2.calculate(args2);
		assertMatches(result1, match1);
		assertMatches(result2, match2);
	}
	
//	@Test
//	void HappyPathExhaustive1() {
//		String s = "13 2 + 5 * 11 7 - / 15 % -3 ^";
//		String[] args = s.split(" ");
//		
//		String expected = "15 75 4 18 3 0";
//		String[] e = expected.split(" ");
//		
//		String result = stackCalculator.calculate(args);
//		assertMatches(result, e);
//	}
//	
//	@Test
//	void HappyPathExhaustive2() {
//		String s = "13 2 ^ 5 - 11 7 * % 15 / -3 +";
//		String[] args = s.split(" ");
//		
//		String expected = "169 164 77 10 0 -3";
//		String[] e = expected.split(" ");
//		
//		String result = stackCalculator.calculate(args);
//		assertMatches(result, e);
//	}
//	
//	@Test
//	void testHW10_2() {
//		String s = "1 10 + 12 * 5 /";
//		String[] args = s.split(" ");
//		
//		String expected = "4 12 2 6 2 256";
//		String[] e = expected.split(" ");
//		
//		String result = StackCalculator.calculate(args);
//		assertMatches(result, e);
//	}
//	
//	@Test
//	void testHW10neg() {
//		String s = "2 2 + -3 * 22 20 - / 4 % 8 ^";
//		String[] args = s.split(" ");
//		
//		String expected = "4 -12 2 -6 -2 256";
//		String[] e = expected.split(" ");
//		
//		String result = StackCalculator.calculate(args);
//		assertMatches(result, e);
//	}
}
