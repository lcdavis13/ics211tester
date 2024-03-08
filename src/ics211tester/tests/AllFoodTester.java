package ics211tester.tests;

import org.junit.jupiter.api.Test;

import edu.ics211.h03.Edible;
import edu.ics211.h03.EdiblePlant;
import edu.ics211.h03.Fruit;
import edu.ics211.h03.Grain;
import edu.ics211.h03.NegativeOrZeroAmountException;
import edu.ics211.h03.PlantType;
import edu.ics211.h03.Vegetable;
import edu.ics211.h03.Barley;
import edu.ics211.h03.Carrot;
import edu.ics211.h03.Lettuce;
import edu.ics211.h03.Rice;

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
        assertNotNull(PlantType.GRAIN);
        assertNotNull(PlantType.FRUIT);
        assertNotNull(PlantType.VEGETABLE);
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
        assertTrue(Rice.class.getSuperclass().equals(Grain.class));
        assertTrue(Barley.class.getSuperclass().equals(Grain.class));
    }

    @Test
    public void testLettuceAndCarrot() {
        assertTrue(Lettuce.class.getSuperclass().equals(Vegetable.class));
        assertTrue(Carrot.class.getSuperclass().equals(Vegetable.class));
    }

    @Test
    public void testNegativeOrZeroAmountException() {
        assertTrue(NegativeOrZeroAmountException.class.getSuperclass().equals(RuntimeException.class));
    }

    @Test
    public void testZeroAvailable() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        assertEquals(0, plant.available());
    }

    @Test
    public void testAddMethod() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        double initialAmount = plant.available();
        try {
            plant.add(100);
            assertEquals(initialAmount + 100, plant.available());
        } catch (NegativeOrZeroAmountException e) {
            fail("Should not throw NegativeOrZeroAmountException for positive amounts");
        }
    }

    @Test
    public void testConsumeMethodWithinAvailableAmount() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        try {
            double initialAmount = plant.available();
            plant.add(100);
            plant.consume(50);
            assertEquals(initialAmount + 50, plant.available());
            plant.consume(25);
            assertEquals(initialAmount + 25, plant.available());
        } catch (NegativeOrZeroAmountException e) {
            fail("Should not throw NegativeOrZeroAmountException for positive amounts");
        }
    }

    @Test
    public void testConsumeMethodExceedingAvailableAmount() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        try {
            double initialAmount = plant.available();
            plant.add(50);
            assertEquals(initialAmount + 50, plant.consume(100 + initialAmount)); // Consuming more than available
            plant.add(20);
            assertEquals(initialAmount + 70, plant.consume(100 + initialAmount)); // Consuming more than available
        } catch (NegativeOrZeroAmountException e) {
            fail("Should not throw NegativeOrZeroAmountException for positive amounts");
        }
    }


	@Test
	public void testAddThrows() {
	    EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
	    assertThrows(NegativeOrZeroAmountException.class, () -> plant.add(-1));
	    assertThrows(NegativeOrZeroAmountException.class, () -> plant.add(0));
	}


    @Test
    public void testConsumeThrows() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.consume(-1));
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.consume(0));
    }

    @Test
    public void testEqualsMethod() {
        EdiblePlant plant1 = new Lettuce();
        EdiblePlant plant2 = new Lettuce();
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
        EdiblePlant rice = new Rice();
        assertEquals("rice", rice.name().toLowerCase());
        assertFalse(rice.isGreen());
        assertEquals(129, rice.calories(100), 0.01); // Assuming 1.29 calories/gram
        assertNotNull(rice.toString());
        assertTrue(rice.toString().toLowerCase().contains("rice"));
    }

    // Test methods for Barley class
    @Test
    public void testBarleyMethods() {
        EdiblePlant barley = new Barley();
        assertEquals("barley", barley.name().toLowerCase());
        assertFalse(barley.isGreen());
        assertEquals(123, barley.calories(100), 0.01); // Assuming 1.23 calories/gram
        assertNotNull(barley.toString());
        assertTrue(barley.toString().toLowerCase().contains("barley"));
    }

    // Test methods for Lettuce class
    @Test
    public void testLettuceMethods() {
        EdiblePlant lettuce = new Lettuce();
        assertEquals("lettuce", lettuce.name().toLowerCase());
        assertTrue(lettuce.isGreen());
        assertEquals(15, lettuce.calories(100), 0.01); // Assuming 0.15 calories/gram
        assertNotNull(lettuce.toString());
        assertTrue(lettuce.toString().toLowerCase().contains("lettuce"));
    }

    // Test methods for Carrot class
    @Test
    public void testCarrotMethods() {
        EdiblePlant carrot = new Carrot();
        assertEquals("carrot", carrot.name().toLowerCase());
        assertTrue(!carrot.isGreen());
        assertEquals(41, carrot.calories(100), 0.01); // Assuming 0.41 calories/gram
        assertNotNull(carrot.toString());
        assertTrue(carrot.toString().toLowerCase().contains("carrot"));
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
    
 // Additional tests for EdiblePlant

 // Test class that inherits from EdiblePlant for testing purposes
 class TestPlant extends EdiblePlant {
     TestPlant() {
         super("TestPlant", PlantType.GRAIN, 0);
     }

     @Override
     public boolean isGreen() {
         return false;
     }

     @Override
     public double calories(double grams) {
         return grams; // Simple calories implementation for testing
     }
 }

 @Test
 public void testNameMethod() {
     EdiblePlant plant = new TestPlant();
     assertEquals("TestPlant", plant.name());
 }

 @Test
 public void testTypeMethod() {
     EdiblePlant plant = new TestPlant();
     assertEquals(PlantType.GRAIN, plant.type());
 }

 @Test
 public void testAvailableMethod() {
     EdiblePlant plant = new TestPlant();
     assertEquals(0, plant.available(), 0.01);
 }

 @Test
 public void testAddMethodForTestPlant() {
     EdiblePlant plant = new TestPlant();
     plant.add(50);
     assertEquals(50, plant.available(), 0.01);
 }

 @Test
 public void testConsumeMethodForTestPlant() {
     EdiblePlant plant = new TestPlant();
     plant.add(50);
     assertEquals(25, plant.consume(25), 0.01);
     assertEquals(25, plant.available(), 0.01);
 }

 @Test
 public void testEqualsMethodForTestPlant() {
     EdiblePlant plant1 = new TestPlant();
     EdiblePlant plant2 = new TestPlant();
     plant1.add(100);
     plant2.add(90); // Within 10% of each other
     assertTrue(plant1.equals(plant2));
 }

 @Test
 public void testAbstractMethodsInEdiblePlant() {
     assertTrue(Modifier.isAbstract(EdiblePlant.class.getDeclaredMethod("isGreen").getModifiers()));
     assertTrue(Modifier.isAbstract(EdiblePlant.class.getDeclaredMethod("calories", double.class).getModifiers()));
 }

}
