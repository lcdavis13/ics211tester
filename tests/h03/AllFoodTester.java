package ics211tester.tests;

import org.junit.jupiter.api.Test;

import edu.ics211.h03.Edible;
import edu.ics211.h03.EdiblePlant;
import edu.ics211.h03.Fruit;
import edu.ics211.h03.Grain;
import edu.ics211.h03.NegativeOrZeroAmountException;
import edu.ics211.h03.PlantType;
import edu.ics211.h03.Vegetable;
import edu.ics211.h03.barley;
import edu.ics211.h03.carrot;
import edu.ics211.h03.lettuce;
import edu.ics211.h03.rice;



import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;


public class AllFoodTester {

    @Test
    public void testPlantTypeEnum() {
        assertNotNull(PlantType.Grain);
        assertNotNull(PlantType.Fruit);
        assertNotNull(PlantType.Vegetable);
    }

    @Test
    public void testEdiblePlantAbstractClass() {
        assertTrue(Modifier.isAbstract(EdiblePlant.class.getModifiers()));
    }

    @Test
    public void testEdibleInterface() {
        assertTrue(Edible.class.isInterface());
        assertTrue(EdiblePlant.class.getInterfaces().length > 0);
        assertTrue(EdiblePlant.class.getInterfaces()[0].equals(Edible.class));
    }

    @Test
    public void testGrainClass() {
        assertTrue(Grain.class.getSuperclass().equals(EdiblePlant.class));
        assertTrue(Modifier.isAbstract(Grain.class.getModifiers()));
    }

    @Test
    public void testFruitClass() {
        assertTrue(Fruit.class.getSuperclass().equals(EdiblePlant.class));
        assertTrue(Modifier.isAbstract(Fruit.class.getModifiers()));
    }

    @Test
    public void testVegetableClass() {
        assertTrue(Vegetable.class.getSuperclass().equals(EdiblePlant.class));
        assertFalse(Modifier.isAbstract(Vegetable.class.getModifiers()));
    }

    @Test
    public void testRiceAndBarley() {
        assertTrue(rice.class.getSuperclass().equals(Grain.class));
        assertTrue(barley.class.getSuperclass().equals(Grain.class));
    }

    @Test
    public void testLettuceAndCarrot() {
        assertTrue(lettuce.class.getSuperclass().equals(Vegetable.class));
        assertTrue(carrot.class.getSuperclass().equals(Vegetable.class));
    }

    @Test
    public void testNegativeOrZeroAmountException() {
        assertTrue(NegativeOrZeroAmountException.class.getSuperclass().equals(RuntimeException.class));
    }

    @Test
    public void testAddMethod() {
        EdiblePlant plant = new lettuce(); // Using Lettuce as a concrete class for testing
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.add(-1));
        try {
            plant.add(100);
            assertEquals(100, plant.available());
        } catch (NegativeOrZeroAmountException e) {
            fail("Should not throw NegativeOrZeroAmountException for positive amounts");
        }
    }

    @Test
    public void testConsumeMethodWithinAvailableAmount() {
        EdiblePlant plant = new lettuce(); // Using Lettuce as a concrete class for testing
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.consume(-1));
        try {
            plant.add(100);
            plant.consume(50);
            assertEquals(50, plant.available());
            plant.consume(25);
            assertEquals(25, plant.available());
        } catch (NegativeOrZeroAmountException e) {
            fail("Should not throw NegativeOrZeroAmountException for positive amounts");
        }
    }
    
    @Test
    public void testConsumeMethodExceedingAvailableAmount() {
        EdiblePlant plant = new lettuce(); // Using Lettuce as a concrete class for testing
        try {
            plant.add(50);
            assertEquals(50, plant.consume(100)); // Consuming more than available
            plant.add(20);
            assertEquals(70, plant.consume(100)); // Consuming more than available
        } catch (NegativeOrZeroAmountException e) {
            fail("Should not throw NegativeOrZeroAmountException for positive amounts");
        }
    }

    @Test
    public void testEqualsMethod() {
        EdiblePlant plant1 = new lettuce();
        EdiblePlant plant2 = new lettuce();
	    try {
	        plant1.add(100);
	        plant2.add(110); // Within 10% of each other
	        assertTrue(plant1.equals(plant2));
		} catch (NegativeOrZeroAmountException e) {
			fail("NegativeOrZeroAmountException should not be thrown");
		}
    }
    
 // Test methods for Rice class
    @Test
    public void testRiceMethods() {
        EdiblePlant rice = new rice();
        assertEquals("rice", rice.name());
        assertFalse(rice.isGreen());
        assertEquals(129, rice.calories(100), 0.01); // Assuming 1.29 calories/gram
        assertNotNull(rice.toString());
        assertTrue(rice.toString().contains("rice"));
    }

    // Test methods for Barley class
    @Test
    public void testBarleyMethods() {
        EdiblePlant barley = new barley();
        assertEquals("barley", barley.name());
        assertFalse(barley.isGreen());
        assertEquals(123, barley.calories(100), 0.01); // Assuming 1.23 calories/gram
        assertNotNull(barley.toString());
        assertTrue(barley.toString().contains("barley"));
    }

    // Test methods for Lettuce class
    @Test
    public void testLettuceMethods() {
        EdiblePlant lettuce = new lettuce();
        assertEquals("lettuce", lettuce.name());
        assertTrue(lettuce.isGreen());
        assertEquals(15, lettuce.calories(100), 0.01); // Assuming 0.15 calories/gram
        assertNotNull(lettuce.toString());
        assertTrue(lettuce.toString().contains("lettuce"));
    }

    // Test methods for Carrot class
    @Test
    public void testCarrotMethods() {
        EdiblePlant carrot = new carrot();
        assertEquals("carrot", carrot.name());
        assertTrue(!carrot.isGreen());
        assertEquals(41, carrot.calories(100), 0.01); // Assuming 0.41 calories/gram
        assertNotNull(carrot.toString());
        assertTrue(carrot.toString().contains("carrot"));
    }

    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
          .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
          .filter(line -> line.endsWith(".class"))
          .map(line -> getClass(line, packageName))
          .collect(Collectors.toSet());
    }
 
    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
              + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }
    
    @Test
    public void testThreeNonAbstractFruitClassesExist() {
        Set<Class> classes = findAllClassesUsingClassLoader("edu.ics211.h03");
        long nonAbstractFruitCount = classes.stream()
                .filter(Fruit.class::isAssignableFrom)
                .filter(cls -> !java.lang.reflect.Modifier.isAbstract(cls.getModifiers()))
                .count();

        assertTrue(3 <= nonAbstractFruitCount, "There should be exactly 3 non-abstract fruit classes.");
    }

    
    

}
