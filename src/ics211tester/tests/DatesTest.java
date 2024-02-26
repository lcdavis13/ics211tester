package ics211tester.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import edu.ics211.h01.Dates;

import java.util.stream.Stream;

public class DatesTest {

    private static Dates dates;

    @BeforeEach
    public void setUp() {
        dates = new Dates();
    }

    private static class TestCase {
        long seconds;
        String expectedRegularYear;
        String expectedLeapYear;
        String description;

        public TestCase(long seconds, String expectedRegularYear, String expectedLeapYear, String description) {
            this.seconds = seconds;
            this.expectedRegularYear = expectedRegularYear;
            this.expectedLeapYear = expectedLeapYear;
            this.description = description;
        }
        
        @Override
        public String toString() {
            return "Dates: " + description;
        }

    }

    static Stream<TestCase> testCases() {
        return Stream.of(
        		new TestCase(690301, "Jan 8 23:45:01", "Jan 8 23:45:01", "instruction example"),
        	    new TestCase(0, "Jan 1 00:00:00", "Jan 1 00:00:00", "zero test"),
        	    new TestCase(3599, "Jan 1 00:59:59", "Jan 1 00:59:59", "pre-1am test"),
        	    new TestCase(32883, "Jan 1 09:08:03", "Jan 1 09:08:03", "time padding test"),
        	    new TestCase(47593, "Jan 1 13:13:13", "Jan 1 13:13:13", "pm test"),
        	    new TestCase(86399, "Jan 1 23:59:59", "Jan 1 23:59:59", "largest value in day"),
        	    new TestCase(86400, "Jan 2 00:00:00", "Jan 2 00:00:00", "day test"),
        	    new TestCase(3925800, "Feb 15 10:30:00", "Feb 15 10:30:00", "month test"),
        	    new TestCase(5135400, "Mar 1 10:30:00", "Feb 29 10:30:00", "leap day test (same for regular year)"),
        	    new TestCase(9023400, "Apr 15 10:30:00", "Apr 14 10:30:00", "post leap day test"),
        	    new TestCase(19564200, "Aug 15 10:30:00", "Aug 14 10:30:00", "month lengths test 1"),
        	    new TestCase(22242600, "Sep 15 10:30:00", "Sep 14 10:30:00", "month lengths test 2"),
        	    new TestCase(31535999, "Dec 31 23:59:59", "Dec 30 23:59:59", "largest value in regular year"),
        	    new TestCase(31622399, "illegal number of seconds", "Dec 31 23:59:59", "largest value in leap year"),
        	    new TestCase(31536000, "illegal number of seconds", "Dec 31 00:00:00", "too large in regular year"),
        	    new TestCase(31622400, "illegal number of seconds", "illegal number of seconds", "too large in leap year"),
        	    new TestCase(-1, "illegal number of seconds", "illegal number of seconds", "negative test")
        );
    }

    @ParameterizedTest(name = "{0}: {index}") // Use the TestCase.toString() for the display name
    @MethodSource("testCases")
    public void runTest(TestCase testCase) {
        // Test for regular year
        String resultRegularYear = dates.computeDateTime(testCase.seconds, false);
        assertEquals(testCase.expectedRegularYear.toLowerCase(), resultRegularYear.toLowerCase(), testCase.description + " - Regular Year");

        // Test for leap year
        String resultLeapYear = dates.computeDateTime(testCase.seconds, true);
        assertEquals(testCase.expectedLeapYear.toLowerCase(), resultLeapYear.toLowerCase(), testCase.description + " - Leap Year");
    }
}


