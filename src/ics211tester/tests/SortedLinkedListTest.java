package ics211tester.tests;

import edu.ics211.h07.SortedLinkedList;
import edu.ics211.h07.SortedLinkedListInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SortedLinkedListTest {

    private SortedLinkedListInterface list;

    @BeforeEach
    void setUp() {
        list = new SortedLinkedList();
    }
    
    @Test
    void testAddReturnTrue()
    {
        assertTrue(list.add(3.0));
        assertTrue(list.add(5.0));
        assertTrue(list.add(7.0));
        assertTrue(list.add(4.0));
    }

    @Test
    void testSize() {
        list.add(3.0);
        assertEquals(1, list.size());
        list.add(5.0);
        assertEquals(2, list.size());
        list.add(7.0);
        assertEquals(3, list.size());
        list.add(4.0);
        assertEquals(4, list.size());
    }

    @Test
    void testGet() {
        list.add(5.0);
        list.add(3.0);
        list.add(7.0);
        list.add(4.0);
        assertEquals(3.0, list.get(0));
        assertEquals(4.0, list.get(1));
        assertEquals(5.0, list.get(2));
        assertEquals(7.0, list.get(3));
    }
    


    @Test
    void testGetError() {
        list.add(5.0);
        list.add(3.0);
        list.add(7.0);
        list.add(4.0);

        try {
            double result = list.get(4);
            assertEquals(Double.NaN, result, "Expected Double.NaN for invalid index, but got: " + result);
        } catch (Exception ignored) {
            
        }

    }


    @Test
    void testRemove() {
        list.add(5.0);
        list.add(3.0);
        list.add(7.0);
        list.add(4.0);

        // Remove middle element
        assertRemoveElement(5.0, true, new double[]{3.0, 4.0, 7.0});
        
        // Remove nonexistent elements
        assertRemoveElement(0.5, false, new double[]{3.0, 4.0, 7.0});
        assertRemoveElement(7.5, false, new double[]{3.0, 4.0, 7.0});
        assertRemoveElement(3.5, false, new double[]{3.0, 4.0, 7.0});

        // Remove last element
        assertRemoveElement(7.0, true, new double[]{3.0, 4.0});

        // Remove first element
        assertRemoveElement(3.0, true, new double[]{4.0});

        // Remove final element
        assertRemoveElement(4.0, true, new double[]{});
        
        // Remove nonexistent element from empty
        assertRemoveElement(4.5, false, new double[]{});
    }

    private void assertRemoveElement(double elementToRemove, boolean expectedSuccess, double[] expectedElements) {
    	int expectedSize = expectedElements.length;
    	
    	boolean removeSuccess = list.remove(elementToRemove);
        boolean validationSuccess = false;

        try {
            assertEquals(expectedSize, list.size());
            for (int i = 0; i < expectedElements.length; i++) {
                assertEquals(expectedElements[i], list.get(i));
            }
            validationSuccess = true;
        } catch (AssertionError ignored) {
        }

        try {
            assertEquals(-1, list.indexOf(elementToRemove));
            for (double expectedElement : expectedElements) {
                assertTrue(list.indexOf(expectedElement) != -1);
            }
            validationSuccess = true;
        } catch (AssertionError ignored) {
        }

        assertTrue((removeSuccess == expectedSuccess) && validationSuccess, "Removal of element " + elementToRemove + " failed or the remaining elements are incorrect.");
    }


    @Test
    void testIndexOf() {
        list.add(5.0);
        list.add(3.0);
        list.add(7.0);
        list.add(4.0);
        assertEquals(0, list.indexOf(3.0));
        assertEquals(1, list.indexOf(4.0));
        assertEquals(2, list.indexOf(5.0));
        assertEquals(3, list.indexOf(7.0));
        assertEquals(-1, list.indexOf(10.0));
    }

    @Test
    void testToString() {
        assertEquals("", list.toString());
        list.add(5.0);
        assertTrue(list.toString().matches(".*5\\.0.*"), "Expected: 5.0, but was: " + list.toString());
        list.add(3.0);
        assertTrue(list.toString().matches(".*3\\.0.*5\\.0.*"), "Expected: 3.0 5.0, but was: " + list.toString());
        list.add(7.0);
        assertTrue(list.toString().matches(".*3\\.0.*5\\.0.*7\\.0.*"), "Expected: 3.0 5.0 7.0, but was: " + list.toString());
        list.add(4.0);
        assertTrue(list.toString().matches(".*3\\.0.*4\\.0.*5\\.0.*7\\.0.*"), "Expected: 3.0 4.0 5.0 7.0, but was: " + list.toString());
    }


}
