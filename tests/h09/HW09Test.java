package ics211tester.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.Deque;
import java.util.Vector;
import java.util.Queue;
import java.util.ArrayDeque;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import edu.ics211.h09.HW09;

public class HW09Test {

    @Test
    @DisplayName("Uses java standard library class or list")
    void AAtestNoForbiddenDependencies() {
        Field[] fields = HW09.class.getDeclaredFields();
        assertFalse(
                isForbiddenClass(HW09.class),
                "Class HW09 extends/implements a forbidden type"
            );
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            assertFalse(
                isForbiddenClass(fieldType),
                "Field " + field.getName() + " is of a forbidden type or extends/implements a forbidden type"
            );
            System.out.println(field.getName() + " passed test and is of type " + fieldType.getName());
        }
    }

    private boolean isForbiddenClass(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return ArrayList.class.isAssignableFrom(clazz) ||
               LinkedList.class.isAssignableFrom(clazz) ||
               List.class.isAssignableFrom(clazz) ||
               Stack.class.isAssignableFrom(clazz) ||
               Deque.class.isAssignableFrom(clazz) ||
               Vector.class.isAssignableFrom(clazz) ||
               Queue.class.isAssignableFrom(clazz) ||
               ArrayDeque.class.isAssignableFrom(clazz) ||
               BlockingDeque.class.isAssignableFrom(clazz) ||
               BlockingQueue.class.isAssignableFrom(clazz) ||
               isForbiddenClass(clazz.getSuperclass());
    }
}
