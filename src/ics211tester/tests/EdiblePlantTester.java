package ics211tester.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import edu.ics211.h03.EdiblePlant;
import edu.ics211.h03.NegativeOrZeroAmountException;
import edu.ics211.h03.PlantType;
import static org.junit.jupiter.api.Assertions.*;

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
    public void testName() {
        assertEquals("TestPlant", plant.name());
    }

    @Test
    public void testType() {
        assertEquals(PlantType.GRAIN, plant.type()); // Assuming TestPlant is a type of GRAIN
    }

    @Test
    public void testAvailable() {
        assertEquals(0, plant.available());
    }

    @Test
    public void testAdd() {
        plant.add(100);
        assertEquals(100, plant.available());
    }

    @Test
    public void testConsume() {
        plant.add(100);
        plant.consume(50);
        assertEquals(50, plant.available());
    }

    @Test
    public void testEquals() {
        EdiblePlant otherPlant = new TestPlant();
        otherPlant.add(100);
        plant.add(100);
        assertTrue(plant.equals(otherPlant));
    }

    @Test
    public void testIsGreenAbstractMethod() {
        assertTrue(EdiblePlant.class.getDeclaredMethods()[0].getName().equals("isGreen"));
    }

    @Test
    public void testCaloriesAbstractMethod() {
        assertTrue(EdiblePlant.class.getDeclaredMethods()[1].getName().equals("calories"));
    }
}
