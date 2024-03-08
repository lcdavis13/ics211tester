package ics211tester.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.ics211.h03.EdiblePlant;
import edu.ics211.h03.NegativeOrZeroAmountException;
import edu.ics211.h03.PlantType;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class EdiblePlantTester {

    private EdiblePlant plant;
    
    // Test class that inherits from EdiblePlant for testing purposes
    class TestPlant extends EdiblePlant {
        TestPlant() {
        	plantName = "TestPlant";
        	plantType = PlantType.GRAIN; 
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

    
    @BeforeEach
    public void setUp() {
        // Assuming you have a concrete class named TestPlant for testing
        plant = new TestPlant();
    }

    @Test
    public void zBASEnameTest() {
        assertEquals("TestPlant", plant.name());
    }

    @Test
    public void zBASEtypeTest() {
        assertEquals(PlantType.GRAIN, plant.type()); // Assuming TestPlant is a type of GRAIN
    }

    @Test
    public void zBASEavailableTest() {
        assertEquals(0, plant.available());
    }

    @Test
    public void zBASEaddTest() {
        plant.add(100);
        assertEquals(100, plant.available());
    }

    @Test
    public void zBASEconsumeTest() {
        plant.add(100);
        plant.consume(50);
        assertEquals(50, plant.available());
    }

    @Test
    public void zBASEequalsTest() {
        EdiblePlant otherPlant = new TestPlant();
        otherPlant.add(100);
        plant.add(100);
        assertTrue(plant.equals(otherPlant));
    }

    @Test
    public void zBASEisGreenAbstractMethodTest() {
        boolean isGreenFound = false;
        for (Method method : EdiblePlant.class.getDeclaredMethods()) {
            if (method.getName().equals("isGreen") && Modifier.isAbstract(method.getModifiers())) {
                isGreenFound = true;
                break;
            }
        }
        assertTrue(isGreenFound, "EdiblePlant should have an abstract method named isGreen.");
    }

    @Test
    public void zBASEcaloriesAbstractMethodTest() {
        boolean caloriesFound = false;
        for (Method method : EdiblePlant.class.getDeclaredMethods()) {
            if (method.getName().equals("calories") && Modifier.isAbstract(method.getModifiers())) {
                caloriesFound = true;
                break;
            }
        }
        assertTrue(caloriesFound, "EdiblePlant should have an abstract method named calories.");
    }
}
