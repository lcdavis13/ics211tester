package ics211tester.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import edu.ics211.h08.HW8Iterator;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class HW8IteratorTest {

	//Test cases:
	//x hasNext empty
	//x hasNext not empty
	//x next empty
	//x next not empty
	//x next end of list
	//x next and hasNext end of list
	//x changing list
	//x arraylist
	//x linkedlist
	//x nested list
	//x string
	
	@Test
	@DisplayName("cannot use anything except get(index) and size() methods")
	public void AAtestNotAllowedMethods() {
		List<Integer> list = List.of(0, 1);
		AssertingList<Integer> assertingList = new AssertingList<Integer>(list);
		HW8Iterator iterator = new HW8Iterator(assertingList);
		int i = 0;
		while (iterator.hasNext() && i < 10000) {
			iterator.next();
			i++;
		}
	}
	
	
    @Test
	@DisplayName("should implement Iterator interface")
    public void BAtestImplementsIterator() {
        // Check if HW8Iterator implements Iterator interface
        assertTrue(Iterator.class.isAssignableFrom(HW8Iterator.class));
    }

    @Test
	@DisplayName("must use generic type")
    public void BBtestIsGeneric() {
        // Check if HW8Iterator has type parameters
        TypeVariable<?>[] typeParameters = HW8Iterator.class.getTypeParameters();
        assertTrue(typeParameters.length > 0);
    }
	
	@Test
	@DisplayName("hasNext method is incorrect")
	void DAhasNextTest() {
		List<Integer> list = List.of(0, 1);
		HW8Iterator iterator = new HW8Iterator(list);
		assertTrue(iterator.hasNext());

		List<Integer> list2 = List.of(0);
		HW8Iterator iterator2 = new HW8Iterator(list2);
		assertTrue(iterator2.hasNext());
		
		List<Integer> list3 = List.of();
		HW8Iterator iterator3 = new HW8Iterator(list3);
		assertFalse(iterator3.hasNext());
	}
	
	@Test
	//@DisplayName("hasNext method is incorrect")
	void DBhasNextBasicTest() {
		List<Integer> list = List.of(0, 1);
		HW8Iterator iterator = new HW8Iterator(list);
		assertTrue(iterator.hasNext());

		List<Integer> list2 = List.of(0);
		HW8Iterator iterator2 = new HW8Iterator(list2);
		assertTrue(iterator2.hasNext());
	}
	
	@Test
	//@DisplayName("hasNext method empty")
	void DChasNextEmptyTest() {
		List<Integer> list = List.of();
		HW8Iterator iterator = new HW8Iterator(list);
		assertFalse(iterator.hasNext());
	}
	
	@Test
	@DisplayName("next and hasNext fail to work together")
	void DDhasNextAfterIteratingTest() {
		List<Integer> list = List.of(0, 12, 30, 3);
		HW8Iterator iterator = new HW8Iterator(list);
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertFalse(iterator.hasNext());
	}
	
	@Test
	@DisplayName("next method returns incorrect results")
	void CAnextTest() {
		List<Integer> list = List.of(0, 12, 30, 3);
		HW8Iterator iterator = new HW8Iterator(list);
		assertEquals(iterator.next(), 0);
		assertEquals(iterator.next(), 12);
		assertEquals(iterator.next(), 30);
		assertEquals(iterator.next(), 3);
	}
	
	@Test
	@DisplayName("next method should throw exception when no more elements")
	void CBnextExceptionTest() {
		List<Integer> list = List.of(0, 12, 30, 3);
		HW8Iterator iterator = new HW8Iterator(list);
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertThrows(Exception.class, () -> iterator.next());
		
		List<Integer> list2 = List.of();
		HW8Iterator iterator2 = new HW8Iterator(list2);
		assertThrows(Exception.class, () -> iterator2.next());
	}
	
	@Test
	//@DisplayName("next method should throw exception")
	void CCnextExceptionEndTest() {
		List<Integer> list = List.of(0, 12, 30, 3);
		HW8Iterator iterator = new HW8Iterator(list);
		iterator.next();
		iterator.next();
		iterator.next();
		iterator.next();
		assertThrows(Exception.class, () -> iterator.next());
	}
	
	@Test
	//@DisplayName("next method should throw exception when empty")
	void CDnextExceptionEmptyTest() {
		List<Integer> list = List.of();
		HW8Iterator iterator = new HW8Iterator(list);
		assertThrows(Exception.class, () -> iterator.next());
	}
	
	@Test
	@DisplayName("must throw NoSuchElementException specifically")
	void CEnextSpecificExceptionTest() {
		List<Integer> list = List.of();
		HW8Iterator iterator = new HW8Iterator(list);
		assertThrows(NoSuchElementException.class, () -> iterator.next());
	}

	@Test
	@DisplayName("must handle a list changing after iterator is created")
	void listChangedTest() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(0);
		list.add(12);
		list.add(30);
		HW8Iterator iterator = new HW8Iterator(list);
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
	//@DisplayName("fails on list of Strings")
	void BCtypeStringTest() {
		List<String> list = List.of("aa", "bbb", "cccc");
		HW8Iterator<String> iterator = new HW8Iterator<String>(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), "aa");
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), "bbb");
	}


	@Test
	@DisplayName("fails on list of lists")
	void EAtypeNestedListTest() {
		List<List<String>> list = List.of(List.of("aa", "bbb"), List.of("ccc", "dddd"), List.of("eee", "fffff"));
		HW8Iterator<List<String>> iterator = new HW8Iterator<List<String>>(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next().get(0), "aa");
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next().get(1), "dddd");
	}


	@Test
	@DisplayName("fails on ArrayList")
	void BDinitArrayListTest() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		list.add(12);
		list.add(30);
		HW8Iterator iterator = new HW8Iterator(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 0);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 12);
	}

	@Test
	@DisplayName("fails on LinkedList")
	void BEinitLinkedListTest() {
		LinkedList<Integer> list = new LinkedList<Integer>();
		list.add(0);
		list.add(12);
		list.add(30);
		HW8Iterator iterator = new HW8Iterator(list);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 0);
		assertTrue(iterator.hasNext());
		assertEquals(iterator.next(), 12);
	}	
}