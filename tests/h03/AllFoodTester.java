package ics211tester.tests;

import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("PlantType enum should be defined with GRAIN, FRUIT, and VEGETABLE")
    public void DEFplantTypeEnumTest() {
        assertNotNull(PlantType.GRAIN);
        assertNotNull(PlantType.FRUIT);
        assertNotNull(PlantType.VEGETABLE);
    }

    @Test
    @DisplayName("EdiblePlant should be an abstract class")
    public void DEFediblePlantAbstractClassTest() {
        assertTrue(Modifier.isAbstract(EdiblePlant.class.getModifiers()));
    }

    @Test
    @DisplayName("Edible should be an interface implemented by EdiblePlant")
    public void DEFedibleInterfaceTest() {
        assertTrue(Edible.class.isInterface());
        assertTrue(EdiblePlant.class.getInterfaces().length > 0);
        assertTrue(EdiblePlant.class.getInterfaces()[0].equals(Edible.class));
    }

    @Test
    @DisplayName("Grain should be an abstract class extending EdiblePlant")
    public void DEFgrainClassTest() {
        assertTrue(Grain.class.getSuperclass().equals(EdiblePlant.class));
        assertTrue(Modifier.isAbstract(Grain.class.getModifiers()));
    }

    @Test
    @DisplayName("Fruit should be an abstract class extending EdiblePlant")
    public void DEFfruitClassTest() {
        assertTrue(Fruit.class.getSuperclass().equals(EdiblePlant.class));
        assertTrue(Modifier.isAbstract(Fruit.class.getModifiers()));
    }

    @Test
    @DisplayName("Vegetable should be a concrete class extending EdiblePlant")
    public void DEFvegetableClassTest() {
        assertTrue(Vegetable.class.getSuperclass().equals(EdiblePlant.class));
        assertFalse(Modifier.isAbstract(Vegetable.class.getModifiers()));
    }

    @Test
    @DisplayName("Rice and Barley should extend Grain")
    public void DEFriceAndBarleyTest() {
        assertTrue(Rice.class.getSuperclass().equals(Grain.class));
        assertTrue(Barley.class.getSuperclass().equals(Grain.class));
    }

    @Test
    @DisplayName("Lettuce and Carrot should extend Vegetable")
    public void DEFlettuceAndCarrotTest() {
        assertTrue(Lettuce.class.getSuperclass().equals(Vegetable.class));
        assertTrue(Carrot.class.getSuperclass().equals(Vegetable.class));
    }

    @Test
    @DisplayName("NegativeOrZeroAmountException should extend RuntimeException")
    public void DEFnegativeOrZeroAmountExceptionTest() {
        assertTrue(NegativeOrZeroAmountException.class.getSuperclass().equals(RuntimeException.class));
    }

    @Test
    @DisplayName("Available amount should be zero initially")
    public void FUNavailableZeroTest() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        assertEquals(0, plant.available());
    }

    @Test
    @DisplayName("Add should increase available amount")
    public void FUNaddTest() {
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
    @DisplayName("Consuming should decrease available amount")
    public void FUNconsumeAvailableTest() {
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
    @DisplayName("Consuming too much should return the current available amount")
    public void FUNconsumeUnvailableTest() {
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
    @DisplayName("Adding negative or zero amount should throw NegativeOrZeroAmountException")
    public void FUNaddThrowsTest() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.add(-1));
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.add(0));
    }

    @Test
    @DisplayName("Consuming negative or zero amount should throw NegativeOrZeroAmountException")
    public void FUNconsumeThrowsTest() {
        EdiblePlant plant = new Lettuce(); // Using Lettuce as a concrete class for testing
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.consume(-1));
        assertThrows(NegativeOrZeroAmountException.class, () -> plant.consume(0));
    }

    @Test
    @DisplayName("Two plants with the same properties should be equal")
    public void FUNequalsTest() {
        Lettuce plant1 = new Lettuce();
        Lettuce plant2 = new Lettuce();
        try {
            plant1.add(100);
            plant2.add(100);
            assertTrue(plant1.equals(plant2));
        } catch (NegativeOrZeroAmountException e) {
            fail("NegativeOrZeroAmountException should not be thrown");
        }
    }

    @Test
    @DisplayName("Two plants with similar amounts should be equal")
    public void FUNequalsApproxTest() {
        Lettuce plant1 = new Lettuce();
        Lettuce plant2 = new Lettuce();
        try {
            plant1.add(100);
            plant2.add(108); // Within 10% of each other
            assertTrue(plant1.equals(plant2));
        } catch (NegativeOrZeroAmountException e) {
            fail("NegativeOrZeroAmountException should not be thrown");
        }
    }

    // Test methods for Rice class
    @Test
    @DisplayName("Rice incorrect")
    public void PLANTriceMethodsTest() {
        EdiblePlant rice = new Rice();
        assertEquals("rice", rice.name().toLowerCase());
        assertFalse(rice.isGreen());
        assertEquals(129, rice.calories(100.0), 0.01); // Assuming 1.29 calories/gram
        assertNotNull(rice.toString());
        assertTrue(rice.toString().toLowerCase().contains("rice"));
    }

    // Test methods for Barley class
    @Test
    @DisplayName("Barley incorrect")
    public void PLANTbarleyMethodsTest() {
        EdiblePlant barley = new Barley();
        assertEquals("barley", barley.name().toLowerCase());
        assertFalse(barley.isGreen());
        assertEquals(123, barley.calories(100.0), 0.01); // Assuming 1.23 calories/gram
        assertNotNull(barley.toString());
        assertTrue(barley.toString().toLowerCase().contains("barley"));
    }

    // Test methods for Lettuce class
    @Test
    @DisplayName("Lettuce incorrect")
    public void PLANTlettuceMethodsTest() {
        EdiblePlant lettuce = new Lettuce();
        assertEquals("lettuce", lettuce.name().toLowerCase());
        assertTrue(lettuce.isGreen());
        assertEquals(15, lettuce.calories(100.0), 0.01); // Assuming 0.15 calories/gram
        assertNotNull(lettuce.toString());
        assertTrue(lettuce.toString().toLowerCase().contains("lettuce"));
    }

    // Test methods for Carrot class
    @Test
    @DisplayName("Carrot incorrect")
    public void PLANTcarrotMethodsTest() {
        EdiblePlant carrot = new Carrot();
        assertEquals("carrot", carrot.name().toLowerCase());
        assertTrue(!carrot.isGreen());
        assertEquals(41, carrot.calories(100.0), 0.01); // Assuming 0.41 calories/gram
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
    @DisplayName("There should be 3 non-abstract fruit classes")
    public void PLANT3FruitClassesExistTest() {
        Set<Class> classes = findAllClassesUsingClassLoader("edu.ics211.h03");
        long nonAbstractFruitCount = classes.stream()
                .filter(Fruit.class::isAssignableFrom)
                .filter(cls -> !java.lang.reflect.Modifier.isAbstract(cls.getModifiers()))
                .count();

        assertTrue(3 <= nonAbstractFruitCount, "There should be exactly 3 non-abstract fruit classes.");
    }
}
