package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	@Test
	public void testSize() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(3, 4);
		assertEquals(1, map.size());
	}

	@Test
	public void testIsEmpty() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(8, 9);
		map.remove(8);
		assertTrue(map.isEmpty());
	}

	@Test
	public void testPutNullKeyShouldThrow() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		assertThrows(NullPointerException.class, () -> map.put(null, 1));
	}

	@Test
	public void testPutNullValue() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(1, null);
		assertTrue(map.containsValue(null));
	}

	@Test
	public void testPut() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(1, null);
		map.put(2, 45);
		map.put(3, 67);
		map.put(4, 23);
		assertEquals(4, map.size());
	}

	@Test
	public void testGetNullKey() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		assertEquals(null, map.get(null));
	}

	@Test
	public void testGetNonexistentKey() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		assertEquals(null, map.get(5));
	}

	@Test
	public void testGet() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(5, 7);
		assertEquals(7, map.get(5));
	}

	@Test
	public void testContainsNullKey() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		assertEquals(false, map.containsKey(null));
	}

	@Test
	public void testContainsKey() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(45, 32);
		assertEquals(true, map.containsKey(45));
		assertEquals(false, map.containsKey(4));
	}

	@Test
	public void testContainsNullValue() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(4, null);
		assertEquals(true, map.containsValue(null));
	}

	@Test
	public void testContainsValue() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(4, 5);
		assertEquals(true, map.containsValue(5));
		assertEquals(false, map.containsValue(7));
	}

	@Test
	public void testRemoveNullKey() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		assertEquals(null, map.remove(null));
	}

	@Test
	public void testRemove() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(4, 5);
		map.put(5, 6);
		map.put(89, 2);
		assertEquals(6, map.remove(5));
		assertEquals(2, map.size());
	}

	@Test
	public void testClear() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(4, 5);
		map.put(5, 6);
		map.put(89, 2);
		map.clear();
		assertTrue(map.isEmpty());
	}

	@Test
	public void testModificationDuringIterationShouldThrow() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		examMarks.put("Ana", 2);
		assertThrows(ConcurrentModificationException.class, () -> iter.hasNext());
	}

	@Test
	public void testModificationRemoveAlreadyRemovedShouldThrow() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				assertThrows(IllegalStateException.class, () -> iter.remove());
			}
		}
	}

	@Test
	public void testModificationRemove() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>();
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}
		assertEquals(1, examMarks.size());
	}

}
