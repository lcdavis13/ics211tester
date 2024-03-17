package ics211tester.tests;

import edu.ics211.h06.SortedArrayList;
import edu.ics211.h06.SortedArrayListInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

public class SortedArrayListTest {

    private SortedArrayListInterface list;

    @BeforeEach
    void setUp() {
        list = new SortedArrayList();
    }
    
    //================Definition tests================
    
    @Test
    public void aDefSizeTest() throws NoSuchMethodException {
        Method method = SortedArrayListInterface.class.getMethod("size");
        assertEquals("size", method.getName());
        assertEquals(int.class, method.getReturnType());
        assertEquals(0, method.getParameterTypes().length);
    }

    @Test
    public void aDefGetTest() throws NoSuchMethodException {
        Method method = SortedArrayListInterface.class.getMethod("get", int.class);
        assertEquals("get", method.getName());
        assertEquals(String.class, method.getReturnType());
        assertArrayEquals(new Class[]{int.class}, method.getParameterTypes());
    }

    @Test
    public void aDefAddTest() throws NoSuchMethodException {
        Method method = SortedArrayListInterface.class.getMethod("add", String.class);
        assertEquals("add", method.getName());
        assertEquals(boolean.class, method.getReturnType());
        assertArrayEquals(new Class[]{String.class}, method.getParameterTypes());
    }

    @Test
    public void aDefRemoveTest() throws NoSuchMethodException {
        Method method = SortedArrayListInterface.class.getMethod("remove", String.class);
        assertEquals("remove", method.getName());
        assertEquals(boolean.class, method.getReturnType());
        assertArrayEquals(new Class[]{String.class}, method.getParameterTypes());
    }

    @Test
    public void aDefIndexOfTest() throws NoSuchMethodException {
        Method method = SortedArrayListInterface.class.getMethod("indexOf", String.class);
        assertEquals("indexOf", method.getName());
        assertEquals(int.class, method.getReturnType());
        assertArrayEquals(new Class[]{String.class}, method.getParameterTypes());
    }

    @Test
    public void aDefToStringTest() throws NoSuchMethodException {
        Method method = SortedArrayListInterface.class.getMethod("toString");
        assertEquals("toString", method.getName());
        assertEquals(String.class, method.getReturnType());
        assertEquals(0, method.getParameterTypes().length);
    }
    
    //================End definition tests================
    
    @Test
    void bAdd()
    {
        assertTrue(list.add("chuck"));
        assertTrue(list.add("edward"));
        assertTrue(list.add("george"));
        assertTrue(list.add("derryl"));
    }
    void bAddDuplicate()
    {
        list.add("chuck");
        list.add("edward");
        assertFalse(list.add("edward"));
        list.add("george");
        assertFalse(list.add("edward"));
        list.add("derryl");
        assertFalse(list.add("chuck"));
    }

    @Test
    void cSize() {
        list.add("chuck");
        assertEquals(1, list.size());
        list.add("edward");
        assertEquals(2, list.size());
        list.add("george");
        assertEquals(3, list.size());
        list.add("derryl");
        assertEquals(4, list.size());
    }

    @Test
    void cSizeEmpty() {
        assertEquals(0, list.size());
    }

    @Test
    void cSizeDuplicate() {
        list.add("chuck");
        list.add("edward");
        list.add("edward");
        assertEquals(2, list.size());
        list.add("george");
        list.add("edward");
        assertEquals(3, list.size());
        list.add("derryl");
        list.add("chuck");
        assertEquals(4, list.size());
    }

    @Test
    void dGet() {
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");
        assertEquals("chuck", list.get(0));
        assertEquals("derryl", list.get(1));
        assertEquals("edward", list.get(2));
        assertEquals("george", list.get(3));
    }

    @Test
    void dGetDuplicate() {
        list.add("chuck");
        list.add("edward");
        list.add("edward");
        list.add("george");
        list.add("edward");
        list.add("derryl");
        list.add("chuck");
        assertEquals("chuck", list.get(0));
        assertEquals("derryl", list.get(1));
        assertEquals("edward", list.get(2));
        assertEquals("george", list.get(3));
    }

    @Test
    void dGetError() {
    	try {
	        assertNull(list.get(-1));
	        assertNull(list.get(0));
	        
	        list.add("edward");
	        list.add("chuck");
	        list.add("george");
	        list.add("derryl");
	
	        assertNull(list.get(4));
		} catch (IndexOutOfBoundsException ignored) {
		}
    }

    @Test
    void dGetErrorDuplicate() {
    	try {
	        assertNull(list.get(-1));
	        assertNull(list.get(0));
	        list.add("chuck");
	        list.add("edward");
	        list.add("edward");
	        list.add("george");
	        list.add("edward");
	        list.add("derryl");
	        list.add("chuck");
	
	        assertNull(list.get(4));
		} catch (IndexOutOfBoundsException ignored) {
		}
    }


    @Test
    void eIndexOf() {
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");
        assertEquals(0, list.indexOf("chuck"));
        assertEquals(1, list.indexOf("derryl"));
        assertEquals(2, list.indexOf("edward"));
        assertEquals(3, list.indexOf("george"));
    }


    @Test
    void eIndexOfDuplicate() {
        list.add("chuck");
        list.add("edward");
        list.add("edward");
        list.add("george");
        list.add("edward");
        list.add("derryl");
        list.add("chuck");
        assertEquals(0, list.indexOf("chuck"));
        assertEquals(1, list.indexOf("derryl"));
        assertEquals(2, list.indexOf("edward"));
        assertEquals(3, list.indexOf("george"));
    }


    @Test
    void eIndexOfError() {
        assertEquals(-1, list.indexOf("jason"));
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");
        assertEquals(-1, list.indexOf("jason"));
    }

    @Test
    void gToString() {
        list.add("edward");
        assertTrue(list.toString().matches(".*edward.*"), "Expected: edward, but was: " + list.toString());
        list.add("chuck");
        assertTrue(list.toString().matches(".*chuck.*edward.*"), "Expected: chuck edward, but was: " + list.toString());
        list.add("george");
        assertTrue(list.toString().matches(".*chuck.*edward.*george.*"), "Expected: chuck edward george, but was: " + list.toString());
        list.add("derryl");
        assertTrue(list.toString().matches(".*chuck.*derryl.*edward.*george.*"), "Expected: chuck derryl edward george, but was: " + list.toString());
    }

    @Test
    void gToStringDuplicate() {
        list.add("edward");
        list.add("chuck");
        list.add("edward");
        assertTrue(list.toString().matches(".*chuck.*edward.*"), "Expected: chuck edward, but was: " + list.toString());
        list.add("george");
        list.add("edward");
        assertTrue(list.toString().matches(".*chuck.*edward.*george.*"), "Expected: chuck edward george, but was: " + list.toString());
        list.add("derryl");
        list.add("chuck");
        assertTrue(list.toString().matches(".*chuck.*derryl.*edward.*george.*"), "Expected: chuck derryl edward george, but was: " + list.toString());
    }

    @Test
    void gToStringEmpty() {
        assertEquals("", list.toString());
    }


    @Test
    void fRemove() {
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");

        // Remove middle element
        assertRemoveElement("edward", true, new String[]{"chuck", "derryl", "george"});

        // Remove last element
        assertRemoveElement("george", true, new String[]{"chuck", "derryl"});

        // Remove first element
        assertRemoveElement("chuck", true, new String[]{"derryl"});

        // Remove final element
        assertRemoveElement("derryl", true, new String[]{});
    }


    @Test
    void fRemoveDuplicate() {
        list.add("chuck");
        list.add("edward");
        list.add("edward");
        list.add("george");
        list.add("edward");
        list.add("derryl");
        list.add("chuck");

        // Remove middle element
        assertRemoveElement("edward", true, new String[]{"chuck", "derryl", "george"});

        // Remove last element
        assertRemoveElement("george", true, new String[]{"chuck", "derryl"});

        // Remove first element
        assertRemoveElement("chuck", true, new String[]{"derryl"});

        // Remove final element
        assertRemoveElement("derryl", true, new String[]{});
    }


    @Test
    void fRemoveNonexistent() {
        // Remove nonexistent element from empty
        assertRemoveElement("drake", false, new String[]{});
        
        list.add("chuck");
        list.add("george");
        list.add("derryl");
        
        // Remove nonexistent elements
        assertRemoveElement("aaron", false, new String[]{"chuck", "derryl", "george"});
        assertRemoveElement("gregor", false, new String[]{"chuck", "derryl", "george"});
        assertRemoveElement("craig", false, new String[]{"chuck", "derryl", "george"});
    }


    @Test
    void fRemoveEmpty() {
        // Remove nonexistent element from empty
        assertRemoveElement("drake", false, new String[]{});
    }

    private void assertRemoveElement(String elementToRemove, boolean expectedSuccess, String[] expectedElements) {
    	int expectedSize = expectedElements.length;
    	
    	boolean removeSuccess = list.remove(elementToRemove);
    	
    	assertEquals(removeSuccess, expectedSuccess, " returned incorrect ");
    	
        boolean validationSuccess = false;

        assertEquals(expectedSize, list.size(), " size incorrect ");

        try {
        	try {
            for (int i = 0; i < expectedElements.length; i++) {
                assertEquals(expectedElements[i], list.get(i));
            }
            validationSuccess = true;
        	}
        	catch (Exception ignored2) {}
        } catch (AssertionError ignored) {
        }

        try {
        	try {
            assertEquals(-1, list.indexOf(elementToRemove));
            for (int i = 0; i < expectedElements.length; i++) {
                assertEquals(i, list.indexOf(expectedElements[i]));
            }
            validationSuccess = true;
    	}
    	catch (Exception ignored2) {}
        } catch (AssertionError ignored) {
        }

        assertTrue(validationSuccess, "get && indexOf incorrect");
    }


}
