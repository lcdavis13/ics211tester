//package ics211tester.tests;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.MethodSource;
//import java.io.ByteArrayOutputStream;
//import java.io.PrintStream;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import edu.ics211.h01.HW1;
//
//import java.util.stream.Stream;
//
//public class HW1Test {
//
//    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//    private final PrintStream originalOut = System.out;
//
//    @BeforeEach
//    public void setUpStreams() {
//        System.setOut(new PrintStream(outContent));
//    }
//
//    @AfterEach
//    public void restoreStreams() {
//        System.setOut(originalOut);
//    }
//
//    static Stream<String> computeDateTimeOutputScenarios() {
//        return Stream.of(
//            "Jan 1 00:00:00", // For 0 seconds
//            "illegal number of seconds" // For -1, 31536000, and 31622400 seconds
//            // Add more expected strings based on your specific scenarios
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("computeDateTimeOutputScenarios")
//    public void testComputeDateTimeOutput(String expectedOutput) {
//        String[] args = {}; // If your main method doesn't use args, keep this empty
//        HW1.main(args);
//
//        assertTrue(outContent.toString().contains(expectedOutput),
//            "Expected to find: {" + expectedOutput + "} as a substring of {" + outContent + "}");
//    }
//
//    static Stream<String> fileReadingOutputScenarios() {
//        return Stream.of(
//            "Ghello.txt", // Assuming the presence of the file
//            "Gworld.txt", // Same assumption as above
//            "Gf1.txt not found",
//            "Gf2.txt not found"
//            // Add more expected strings if necessary
//        );
//    }
//
//    @ParameterizedTest
//    @MethodSource("fileReadingOutputScenarios")
//    public void testFileReadingOutput(String expectedOutput) {
//        String[] args = {"hello.txt", "world.txt", "f1.txt", "f2.txt"};
//        HW1.main(args);
//
//        assertTrue(outContent.toString().toLowerCase().contains(expectedOutput.toLowerCase()),
//            "Expected to find: " + expectedOutput);
//    }
//}
