// TO DO: test whether they properly ignore case for sorting & duplicates
// try to grade on sorting separately somehow for get and indexOf - like just test that they're all there if we get every element
//test all methods with various degrees of capacity remaining; I know some fail only when capacity is not reached

package ics211tester.tests;

import edu.ics211.h06.SortedArrayList;
import edu.ics211.h06.SortedArrayListInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

public class SortedArrayListTest {

    private SortedArrayList list;

    @BeforeEach
    void setUp() {
        list = new SortedArrayList();
    }
    
    //================Definition tests================
    
	@Test 
	@DisplayName("SortedArrayList must implement SortedArrayListInterface")
	public void aADefInterface() {
        assertTrue(SortedArrayListInterface.class.isInterface());
        assertTrue(SortedArrayList.class.getInterfaces().length > 0);
        assertTrue(SortedArrayList.class.getInterfaces()[0].equals(SortedArrayListInterface.class));
	}
	
	@Test
	@DisplayName("SortedArrayList method definitions not correct")
	public void aADefMethods() throws NoSuchMethodException {
		aDefSizeTest();
		aDefGetTest();
		aDefAddTest();
		aDefRemoveTest();
		aDefIndexOfTest();
		aDefToStringTest();
	}
    
    @Test
    public void aDefSizeTest() throws NoSuchMethodException {
        Method method = SortedArrayList.class.getMethod("size");
        assertEquals("size", method.getName());
        assertEquals(int.class, method.getReturnType());
        assertEquals(0, method.getParameterTypes().length);
    }

    @Test
    public void aDefGetTest() throws NoSuchMethodException {
        Method method = SortedArrayList.class.getMethod("get", int.class);
        assertEquals("get", method.getName());
        assertEquals(String.class, method.getReturnType());
        assertArrayEquals(new Class[]{int.class}, method.getParameterTypes());
    }

    @Test
    public void aDefAddTest() throws NoSuchMethodException {
        Method method = SortedArrayList.class.getMethod("add", String.class);
        assertEquals("add", method.getName());
        assertEquals(boolean.class, method.getReturnType());
        assertArrayEquals(new Class[]{String.class}, method.getParameterTypes());
    }

    @Test
    public void aDefRemoveTest() throws NoSuchMethodException {
        Method method = SortedArrayList.class.getMethod("remove", String.class);
        assertEquals("remove", method.getName());
        assertEquals(boolean.class, method.getReturnType());
        assertArrayEquals(new Class[]{String.class}, method.getParameterTypes());
    }

    @Test
    public void aDefIndexOfTest() throws NoSuchMethodException {
        Method method = SortedArrayList.class.getMethod("indexOf", String.class);
        assertEquals("indexOf", method.getName());
        assertEquals(int.class, method.getReturnType());
        assertArrayEquals(new Class[]{String.class}, method.getParameterTypes());
    }

    @Test
    public void aDefToStringTest() throws NoSuchMethodException {
        Method method = SortedArrayList.class.getMethod("toString");
        assertEquals("toString", method.getName());
        assertEquals(String.class, method.getReturnType());
        assertEquals(0, method.getParameterTypes().length);
    }
    
    //================End definition tests================
    
    @Test
    @DisplayName("Add returns true when not duplicate")
    void bAddReturn()
    {
        assertTrue(list.add("chuck"));
        assertTrue(list.add("edward"));
        assertTrue(list.add("george"));
        assertTrue(list.add("derryl"));
    }
    
    @Test
    @DisplayName("Add returns false when duplicate")
    void bAddReturnDuplicate()
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
    @DisplayName("Size incorrect")
    void cCSizeAll() {
        assertEquals(0, list.size());
        list.add("chuck");
        assertEquals(1, list.size());
        list.add("edward");
        assertEquals(2, list.size());
        list.add("edward");
        assertEquals(2, list.size());
        list.add("george");
        assertEquals(3, list.size());
        list.add("edward");
        assertEquals(3, list.size());
        list.add("derryl");
        assertEquals(4, list.size());
        list.add("chuck");
        assertEquals(4, list.size());
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
    @DisplayName("Get is incorrect")
    void dDGetAll() {
    	try {
	        assertNull(list.get(-1));
	        assertNull(list.get(0));
		} catch (IndexOutOfBoundsException ignored) {
		}
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
        try {
	        assertNull(list.get(4));  
		} catch (IndexOutOfBoundsException ignored) {
		}
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
    @DisplayName("IndexOf is incorrect")
    void eEIndexOfAll() {
        assertEquals(-1, list.indexOf("jason"));
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
        assertTrue(list.indexOf("jason") < 0);
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
        assertTrue(list.indexOf("jason") < 0);
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");
        assertTrue(list.indexOf("henry") < 0);
    }

    @Test
    @DisplayName("ToString is incorrect")
    void gGToStringAll() {
        assertEquals("", list.toString());
        list.add("edward");
        assertTrue(list.toString().toLowerCase().matches(".*edward.*"), "Expected: edward, but was: " + list.toString());
        list.add("chuck");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*"), "Expected: chuck edward, but was: " + list.toString());
        list.add("edward");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*"), "Expected: chuck edward, but was: " + list.toString());
        list.add("george");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*george.*"), "Expected: chuck edward george, but was: " + list.toString());
        list.add("edward");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*george.*"), "Expected: chuck edward george, but was: " + list.toString());
        list.add("derryl");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*derryl.*edward.*george.*"), "Expected: chuck derryl edward george, but was: " + list.toString());
        list.add("chuck");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*derryl.*edward.*george.*"), "Expected: chuck derryl edward george, but was: " + list.toString());
    }

    @Test
    void gToString() {
        list.add("edward");
        assertTrue(list.toString().toLowerCase().matches(".*edward.*"), "Expected: edward, but was: " + list.toString());
        list.add("chuck");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*"), "Expected: chuck edward, but was: " + list.toString());
        list.add("george");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*george.*"), "Expected: chuck edward george, but was: " + list.toString());
        list.add("derryl");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*derryl.*edward.*george.*"), "Expected: chuck derryl edward george, but was: " + list.toString());
    }

    @Test
    void gToStringDuplicate() {
        list.add("edward");
        list.add("chuck");
        list.add("edward");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*"), "Expected: chuck edward, but was: " + list.toString());
        list.add("george");
        list.add("edward");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*edward.*george.*"), "Expected: chuck edward george, but was: " + list.toString());
        list.add("derryl");
        list.add("chuck");
        assertTrue(list.toString().toLowerCase().matches(".*chuck.*derryl.*edward.*george.*"), "Expected: chuck derryl edward george, but was: " + list.toString());
    }

    @Test
    void gToStringEmpty() {
        assertEquals("", list.toString());
    }


    @Test
    @DisplayName("Remove should return true when element is present")
    void fRemoveReturnTrue() {
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");

        assertTrue(list.remove("edward"));
        assertTrue(list.remove("george"));
        assertTrue(list.remove("chuck"));
        assertTrue(list.remove("derryl"));
    }


    @Test
    @DisplayName("Remove should return false when element is not present")
    void fRemoveReturnFalse() {
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");

        assertFalse(list.remove("test"));
        assertFalse(list.remove("frank"));
        assertFalse(list.remove("jason"));
    }


    @Test
    @DisplayName("Remove should return false when list is empty")
    void fRemoveRetrunFalseEmpty() {
        assertFalse(list.remove("edward"));
        assertFalse(list.remove("george"));
        assertFalse(list.remove("chuck"));
        assertFalse(list.remove("derryl"));
    }


    @Test
    @DisplayName("Remove not correctly altering the list, or no other methods are working to allow inspection of removed results")
    void fFRemoveAll() {
        // Remove nonexistent element from empty
        assertRemoveElement("drake", new String[]{});
        
        list.add("chuck");
        list.add("edward");
        list.add("edward");
        list.add("george");
        list.add("edward");
        list.add("derryl");
        list.add("chuck");

        // Remove middle element
        assertRemoveElement("edward", new String[]{"chuck", "derryl", "george"});

        // Remove nonexistent elements
        assertRemoveElement("aaron", new String[]{"chuck", "derryl", "george"});
        assertRemoveElement("gregor", new String[]{"chuck", "derryl", "george"});
        assertRemoveElement("craig", new String[]{"chuck", "derryl", "george"});

        // Remove last element
        assertRemoveElement("george", new String[]{"chuck", "derryl"});

        // Remove element again should fail
        assertRemoveElement("george", new String[]{"chuck", "derryl"});

        // Remove first element
        assertRemoveElement("chuck", new String[]{"derryl"});

        // Remove final element
        assertRemoveElement("derryl", new String[]{});
        
    }
    
    @Test
    void fRemove() {
        list.add("edward");
        list.add("chuck");
        list.add("george");
        list.add("derryl");

        // Remove middle element
        assertRemoveElement("edward", new String[]{"chuck", "derryl", "george"});

        // Remove last element
        assertRemoveElement("george", new String[]{"chuck", "derryl"});

        // Remove first element
        assertRemoveElement("chuck", new String[]{"derryl"});

        // Remove final element
        assertRemoveElement("derryl", new String[]{});
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
        assertRemoveElement("edward", new String[]{"chuck", "derryl", "george"});

        // Remove last element
        assertRemoveElement("george", new String[]{"chuck", "derryl"});

        // Remove first element
        assertRemoveElement("chuck", new String[]{"derryl"});

        // Remove final element
        assertRemoveElement("derryl", new String[]{});
    }
    
    @Test
    void fRemoveNonexistent() {
        // Remove nonexistent element from empty
        assertRemoveElement("drake", new String[]{});
        
        list.add("chuck");
        list.add("george");
        list.add("derryl");
        
        // Remove nonexistent elements
        assertRemoveElement("aaron", new String[]{"chuck", "derryl", "george"});
        assertRemoveElement("gregor", new String[]{"chuck", "derryl", "george"});
        assertRemoveElement("craig", new String[]{"chuck", "derryl", "george"});
    }
    
    //test if we can confirm that remove works through any 1 of the 3 getter methods (in case some are unimplemented)
    private void assertRemoveElement(String elementToRemove, String[] expectedElements) {
    	
    	list.remove(elementToRemove);
    	
        boolean getSuccess = false;
        boolean indexOfSuccess = false;
        boolean sizeSuccess = false;
        boolean toStringSuccess = false;

        
        //size
    	int expectedSize = expectedElements.length;
        try {
			if (expectedSize == list.size()) {
				sizeSuccess = true;
			}
		} catch (Exception ignored) {sizeSuccess = false;}

        
        //get
        try {
            getSuccess = true;
            for (int i = 0; i < expectedElements.length; i++) {
				if (expectedElements[i] != list.get(i)) {
					getSuccess = false;
					break;
				}
            }
		} catch (Exception ignored) {getSuccess = false;}

        
        //indexOf
        try {
			if (list.indexOf(elementToRemove) < 0) {
				indexOfSuccess = true;
			}
            for (int i = 0; i < expectedElements.length; i++) {
                assertEquals(i, list.indexOf(expectedElements[i]));
            }
            indexOfSuccess = true;
		} catch (Exception ignored) {indexOfSuccess = false;}

        
        //toString
    	String expectedPattern = String.join(".*", expectedElements);
    	String badPattern = ".*" + elementToRemove + ".*";
        try {
        	String result = list.toString().toLowerCase();
			toStringSuccess = result.matches(expectedPattern);
			if (result.matches(badPattern)) {
				toStringSuccess = false;
			}
		} catch (Exception ignored) {toStringSuccess = false;}
        
        

        assertTrue(sizeSuccess || getSuccess || indexOfSuccess || toStringSuccess);
    }


}
