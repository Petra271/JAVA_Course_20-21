package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void testIsEmpty() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		assertTrue(dict.isEmpty());
	}

	@Test
	public void testSize() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(4, 5);
		dict.put(1, 2);
		assertEquals(2, dict.size());
	}

	@Test
	public void testClear() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(4, 5);
		dict.put(1, 2);
		assertEquals(2, dict.size());
		dict.clear();
		assertTrue(dict.isEmpty());
	}

	@Test
	public void testPutNullKey() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dict.put(null, 7));
	}

	@Test
	public void testPutExistingKey() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(8, 4);
		assertEquals(4, dict.put(8, 7));
		assertEquals(7, dict.get(8));
	}

	@Test
	public void testPutNewKey() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(8, 4);
		assertEquals(null, dict.put(9, 7));
	}

	@Test
	public void testGetValueKeyPresent() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(8, "dog");
		assertEquals("dog", dict.get(8));
	}

	@Test
	public void testGetValueKeyNotPresent() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(8, "dog");
		assertEquals(null, dict.get(2));
	}

	@Test
	public void testRemoveKeyNotPresent() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(8, "dog");
		assertEquals(null, dict.remove(6));
	}

	@Test
	public void testRemoveKeyPresent() {
		Dictionary<Integer, Double> dict = new Dictionary<>();
		dict.put(8, 0.5);
		assertEquals(0.5, dict.remove(8));
		assertEquals(0, dict.size());
	}

}
