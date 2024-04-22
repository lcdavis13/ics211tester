package ics211tester.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	void aSimpleTest() {
		String s1 = "13 2 + 5 * 2 4 + + 5 +";
		String s2 = "13 2 * 5 + 2 4 * + 5 *";
		
		String e1 = "15 75 6 81";
		String e2 = "26 31 8 39";
		
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
	void bFullLengthTest() {
		String s1 = "13 2 + 5 * 2 4 + + 5 +";
		String s2 = "13 2 * 5 + 2 4 * + 5 *";
		
		String e1 = "15 75 6 81 86";
		String e2 = "26 31 8 39 195";
		
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
	@DisplayName("handle negative numbers")
	void bNegativeTest() {
		String s1 = "13 2 + -5 * -2 4 + + 5 +";
		String s2 = "13 -2 * 5 + 2 -4 * + 5 *";
		
		String e1 = "15 -75 2 -73";
		String e2 = "-26 -21 -8 -29";
		
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
	@DisplayName("use correct order of arguments for subtraction etc")
	void bNonTransitiveTest() {
		String s1 = "13 2 + 5 * 11 7 - / 15 % 3 ^ 3 + 3 +";
		String s2 = "13 2 ^ 5 - 11 7 * % 15 / 3 + 3 * 3 +";
		
		String e1 = "15 75 4 18 3 27 30";
		String e2 = "169 164 77 10 0 3 9";
		
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
	@DisplayName("shouldn't crash on empty string")
	void bEmptyStringTest() {
		String s1 = "";
		
		String e1 = "";
		
		String[] args1 = s1.split(" ");
		String[] match1 = e1.split(" ");
		StackCalculator stackCalculator1 = new StackCalculator();
		String result1 = stackCalculator1.calculate(args1);
		assertMatches(result1, match1);
	}



	@Test
	@DisplayName("should ignore operators without 2 arguments and continue calculating")
	void cIgnoreOperatorsWithOneArgument() {
		String s1 = "13 + 2 + * 5 * 2 4 + + 5";
		String s2 = "13 * 2 * + 5 + 2 4 * + 5";
		
		String e1 = "15 75 6 81";
		String e2 = "26 31 8 39";
		
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
	@DisplayName("ignore operators with zero arguments")
	void cIgnoreOperatorsWithZeroArguments() {
		String s1 = "+ 13 + 2 + * 5 * 2 4 + + 5";
		String s2 = "* 13 * 2 * + 5 + 2 4 * + 5";
		
		String e1 = "15 75 6 81";
		String e2 = "26 31 8 39";
		
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
	@DisplayName("handle are extra numbers at end of input")
	void dHangingNumbersTest() {
		String s1 = "13 2 + 5 * 2 4 + + 5";
		String s2 = "13 2 * 5 + 2 4 * + 5";
		
		String e1 = "15 75 6 81";
		String e2 = "26 31 8 39";
		
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
	@DisplayName("don't allow stack to persist between calculations")
	void dHangingNumbersPersistentStackTest() {
		String s1 = "13 2 + 5 * 2 4 + + 5";
		String s2 = "13 2 * 5 + 2 4 * + 5";
		
		String e1 = "15 75 6 81";
		String e2 = "26 31 8 39";
		
		String[] args1 = s1.split(" ");
		String[] args2 = s2.split(" ");
		String[] match1 = e1.split(" ");
		String[] match2 = e2.split(" ");
		
		StackCalculator stackCalculator = new StackCalculator();
		
		String result1 = stackCalculator.calculate(args1);
		String result2 = stackCalculator.calculate(args2);
		assertMatches(result1, match1);
		assertMatches(result2, match2);
	}
	
	
	

	
	private void testMainOutput(String[] args, String[] substrings) {
	    // Redirect standard output to capture the output of main
	    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    PrintStream originalOut = System.out;  // Save the original System.out
	    System.setOut(new PrintStream(outContent));

	    try {
	        // Run the main method with the given arguments
			StackCalculator stackCalculator = new StackCalculator();
			stackCalculator.main(args);

	        assertMatches(outContent.toString(), substrings);
	    } catch (Exception e) {
	    	fail("Exception occurred: " + e.getMessage());
	    } 
	    finally {
	        // Restore standard output to ensure it's reset even if an exception occurs
	        System.setOut(originalOut);
	    }
	}
	
	
	@Test
	@DisplayName("main should use args and print the result")
	void aMainOutputTest() {
		String s1 = "13 2 + 5 * 2 4 + + 5 +";
		String s2 = "13 2 * 5 + 2 4 * + 5 *";
		
		String e1 = "15 75 6 81";
		String e2 = "26 31 8 39";
		
		String[] args1 = s1.split(" ");
		String[] args2 = s2.split(" ");
		String[] match1 = e1.split(" ");
		String[] match2 = e2.split(" ");

		testMainOutput(args1, match1);
		testMainOutput(args2, match2);
	}
}
