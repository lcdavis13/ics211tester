package ics211tester.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.ics211.h08.HW8Iterator;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class HW8IteratorTest {
    @Test
	@DisplayName("implement Iterator interface")
    public void testImplementsIterator() {
        // Check if HW8Iterator implements Iterator interface
        assertTrue(Iterator.class.isAssignableFrom(HW8Iterator.class));
    }

    @Test
	@DisplayName("uses generic type")
    public void testIsGeneric() {
        // Check if HW8Iterator has a generic type
        Type genericSuperclass = HW8Iterator.class.getGenericInterfaces()[0];
        assertTrue(genericSuperclass instanceof ParameterizedType);
    }
	
	@Test
	@DisplayName("hasNext method")
	void hasNextTest() {
		List<Integer> list = List.of(0, 1);
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		assertTrue(iterator.hasNext());

		List<Integer> list2 = List.of(0);
		HW8Iterator<Integer> iterator2 = new HW8Iterator<Integer>(list2);
		assertTrue(iterator2.hasNext());
	}
	
	@Test
	@DisplayName("hasNext method empty")
	void hasNextEmptyTest() {
		List<Integer> list = List.of();
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		assertFalse(iterator.hasNext());
	}
	
	@Test
	@DisplayName("next method")
	void nextTest() {
		List<Integer> list = List.of(0, 12, 30, 3);
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		assertEquals(iterator.next(), 0);
		assertEquals(iterator.next(), 12);
		assertEquals(iterator.next(), 30);
		assertEquals(iterator.next(), 3);
	}
	
	@Test
	@DisplayName("next method end of list")
	void nextEndTest() {
		List<Integer> list = List.of(0, 12, 30, 3);
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertThrows(Exception.class, () -> iterator.next());
	}
	
	@Test
	@DisplayName("next method empty")
	void nextEmptyTest() {
		List<Integer> list = List.of();
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		assertThrows(Exception.class, () -> iterator.next());
	}
	
	@Test
	@DisplayName("throw NoSuchElementException specifically")
	void nextExceptionTest() {
		List<Integer> list = List.of();
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		assertThrows(NoSuchElementException.class, () -> iterator.next());
	}
	
	@Test
	@DisplayName("hasNext method end of list (after using next)")
	void hasNextAndNextEndTest() {
		List<Integer> list = List.of(0, 12, 30, 3);
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertFalse(iterator.hasNext());
	}

	@Test
	@DisplayName("changing list after iterator is created")
	void listChangedTest() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(0);
		list.add(12);
		list.add(30);
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		iterator.next();
		iterator.next();
		iterator.next();
		list.add(40);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 40);
		assertFalse(iterator.hasNext());
		assertThrows(RuntimeException.class, () -> iterator.next());
	}


	@Test
	@DisplayName("list of Strings")
	void typeStringTest() {
		List<String> list = List.of("aa", "bbb", "cccc");
		HW8Iterator<String> iterator = new HW8Iterator<String>(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), "aa");
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), "bbb");
	}


	@Test
	@DisplayName("list of lists")
	void typeNestedListTest() {
		List<List<String>> list = List.of(List.of("aa", "bbb"), List.of("ccc", "dddd"), List.of("eee", "fffff"));
		HW8Iterator<List<String>> iterator = new HW8Iterator<List<String>>(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next().get(0), "aa");
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next().get(1), "dddd");
	}


	@Test
	@DisplayName("initializing from ArrayList")
	void initArrayListTest() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(12);
		list.add(30);
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 0);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 12);
	}

	@Test
	@DisplayName("initializing from LinkedList")
	void initLinkedListTest() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(0);
		list.add(12);
		list.add(30);
		HW8Iterator<Integer> iterator = new HW8Iterator<Integer>(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 0);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 12);
	}	
}