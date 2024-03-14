package ics211tester.tests;

import edu.ics211.h04.hw04;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class hw04Tests {
    private String simpleInput;
    private String simpleExpected;
    
    private String complexInput;
    private String complexExpected;
    
    private String duplicateCharInput;
    private String expectedDuplicateChar;
    
    private String whitespaceInput;
    private String expectedWhitespace;

	private String uniqueSortInput;
	private String uniqueSortExpected;
	private String uniqueDupesInput;
	private String uniqueDupesExpected;
	private String uniqueSortDupesInput;
	private String uniqueSortDupesExpected;
	private String uniqueSortComplexInput;
	private String uniqueSortComplexExpected;
    
    private hw04.SortType uniqueSortType;

    @BeforeEach
    void setUp() throws Exception {
        uniqueSortType = getUniqueSortTypeEnum();

        simpleInput = "gnhvpodrusyiemztcajqklbwfx";
        simpleExpected = "abcdefghijklmnopqrstuvwxyz";
        
        complexInput = "the quick brown fox jumps over the lazy dog";
        complexExpected =  "        abcdeeefghhijklmnoooopqrrsttuuvwxyz";
        //complexExpectedUnique = " abcdefghijklmnopqrstuvwxyz";
        
        duplicateCharInput = "daadacccddbbbd";
        expectedDuplicateChar = "aaabbbcccddddd";
        //expectedDuplicateUnique = "abcd";
        
        whitespaceInput = "ca db";
        expectedWhitespace = " abcd";
        //expectedWhitespaceUnique = " abcd";


        //simpleUniqueInput = "thequickbrownfoxjumpsoverthelazydog";
        //simpleUniqueExpected = "abcdefghijklmnopqrstuvwxyz";
        
        uniqueSortInput = simpleInput;
        uniqueSortExpected = simpleExpected;
        
        uniqueDupesInput = "aaabbbcccddddd";
        uniqueDupesExpected = "abcd";
        
        uniqueSortDupesInput = "thequickbrownfoxjumpsoverthelazydog";
        uniqueSortDupesExpected = "abcdefghijklmnopqrstuvwxyz";
        
        uniqueSortComplexInput = "the quick brown fox jumps over the lazy dog";
        uniqueSortComplexExpected = " abcdefghijklmnopqrstuvwxyz";
    }

    private hw04.SortType getUniqueSortTypeEnum() {
        try {
            return hw04.SortType.valueOf("SelectUnique");
        } catch (IllegalArgumentException e) {
            try {
                return hw04.SortType.valueOf("InsertUnique");
            } catch (IllegalArgumentException ex) {
                throw new RuntimeException("Enum value for unique sorting not found.");
            }
        }
    }
    
    

	
	 @Test
	 @DisplayName("Unique sort doesn't remove duplicates correctly")
	 void bSortUniqueTest() {
	     assertEquals(uniqueDupesExpected, hw04.sortChars(uniqueDupesInput, uniqueSortType));
	 }

	 @Test
	 @DisplayName("Unique sort doesn't sort correctly")
	 void cSortUniqueWithoutDupesTest() {
	     assertEquals(uniqueSortExpected, hw04.sortChars(uniqueSortInput, uniqueSortType));
	 }

	 @Test
	 @DisplayName("Unique sort doesn't sort and remove duplicates correctly")
	 void cSortUniqueTestComplex() {
	     assertEquals(uniqueSortDupesExpected, hw04.sortChars(uniqueSortDupesInput, uniqueSortType));
	 }

    @Test
    @DisplayName("Handling whitespace incorrectly with UniqueSort")
    void cWhitespaceUniqueTest() {
        assertEquals(uniqueSortComplexExpected, hw04.sortChars(uniqueSortComplexInput, uniqueSortType));
    }

    @Test
    //@DisplayName("Handling duplicate characters incorrectly with UniqueSort")
    void cDuplicateCharactersUniqueTest() {
        //assertEquals(expectedDuplicateUnique, hw04.sortChars(duplicateCharInput, uniqueSortType));
    }
    
    


	 @Test
	 void eSortSelectionTestComplex() {
	     assertEquals(complexExpected, hw04.sortChars(complexInput, hw04.SortType.SelectionSort));
	 }

	 @Test
	 @DisplayName("Selection sort incorrect")
	 void bSortSelectionTest() {
	     assertEquals(simpleExpected, hw04.sortChars(simpleInput, hw04.SortType.SelectionSort));
	 }
	
	 @Test
	 void eSortBubbleTestComplex() {
	     assertEquals(complexExpected, hw04.sortChars(complexInput, hw04.SortType.BubbleSort));
	 }

	 @Test
	 @DisplayName("Bubble sort incorrect")
	 void bSortBubbleTest() {
	     assertEquals(simpleExpected, hw04.sortChars(simpleInput, hw04.SortType.BubbleSort));
	 }
	
	 @Test
	 void eSortInsertionTestComplex() {
	     assertEquals(complexExpected, hw04.sortChars(complexInput, hw04.SortType.InsertionSort));
	 }

	 @Test
	 @DisplayName("Insertion sort incorrect")
	 void bSortInsertionTest() {
	     assertEquals(simpleExpected, hw04.sortChars(simpleInput, hw04.SortType.InsertionSort));
	 }
	
	 @Test
	 @DisplayName("Handling empty string incorrectly")
	 void eEmptyStringTest() {
	     String input = "";
	     String expected = "";
	     assertEquals(expected, hw04.sortChars(input, hw04.SortType.SelectionSort));
	     assertEquals(expected, hw04.sortChars(input, hw04.SortType.BubbleSort));
	     assertEquals(expected, hw04.sortChars(input, hw04.SortType.InsertionSort));
	     assertEquals(expected, hw04.sortChars(input, uniqueSortType));
	 }
	
	 @Test
	 @DisplayName("Handling single character strings incorrectly")
	 void eSingleCharacterTest() {
	     String input = "a";
	     String expected = "a";
	     assertEquals(expected, hw04.sortChars(input, hw04.SortType.SelectionSort));
	     assertEquals(expected, hw04.sortChars(input, hw04.SortType.BubbleSort));
	     assertEquals(expected, hw04.sortChars(input, hw04.SortType.InsertionSort));
	     assertEquals(expected, hw04.sortChars(input, uniqueSortType));
	 }
	 
    // Test cases for handling duplicate characters
    @Test
    @DisplayName("Handling duplicate characters incorrectly with SelectionSort")
    void cDuplicateCharactersSelectionTest() {
        assertEquals(expectedDuplicateChar, hw04.sortChars(duplicateCharInput, hw04.SortType.SelectionSort));
    }

    @Test
    @DisplayName("Handling duplicate characters incorrectly with BubbleSort")
    void cDuplicateCharactersBubbleTest() {
        assertEquals(expectedDuplicateChar, hw04.sortChars(duplicateCharInput, hw04.SortType.BubbleSort));
    }

    @Test
    @DisplayName("Handling duplicate characters incorrectly with InsertionSort")
    void cDuplicateCharactersInsertionTest() {
        assertEquals(expectedDuplicateChar, hw04.sortChars(duplicateCharInput, hw04.SortType.InsertionSort));
    }

    // Test cases for handling whitespace
    @Test
    @DisplayName("Handling whitespace incorrectly with SelectionSort")
    void cWhitespaceSelectionTest() {
        assertEquals(expectedWhitespace, hw04.sortChars(whitespaceInput, hw04.SortType.SelectionSort));
    }

    @Test
    @DisplayName("Handling whitespace incorrectly with BubbleSort")
    void cWhitespaceBubbleTest() {
        assertEquals(expectedWhitespace, hw04.sortChars(whitespaceInput, hw04.SortType.BubbleSort));
    }

    @Test
    @DisplayName("Handling whitespace incorrectly with InsertionSort")
    void cWhitespaceInsertionTest() {
        assertEquals(expectedWhitespace, hw04.sortChars(whitespaceInput, hw04.SortType.InsertionSort));
    }

	 @Test
	 @DisplayName("swap doesn't exchange values correctly")
	 void aSwapTest() {
	     char[] array = {'a', 'b', 'c', 'd'};
	     hw04.swap(array, 1, 3);
	     assertArrayEquals(new char[]{'a', 'd', 'c', 'b'}, array);
	 }

	 @Test
	 @DisplayName("swap with same indices shouldn't change array")
	 void aSwapzSameIndexTest() {
	     char[] array = {'x', 'y', 'z'};
	     hw04.swap(array, 2, 2);
	     assertArrayEquals(new char[]{'x', 'y', 'z'}, array);
	 }

}
