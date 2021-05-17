package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void testInitialCapacityLessThanOneShouldThrow() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection<Integer>(-5));
		assertThrows(IllegalArgumentException.class,
				() -> new ArrayIndexedCollection<Object>(new ArrayIndexedCollection<Object>(), -3));
	}

	@Test
	public void testCollectionGivenToConstructorNullShouldThrow() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<Object>(null, 4));
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection<String>(null));
	}

	@Test
	public void testCopyCollectionConstructor() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(3);
		col.add(5);
		ArrayIndexedCollection<Number> newCol = new ArrayIndexedCollection<>(col);
		assertArrayEquals(new Object[] { 3, 5 }, newCol.toArray());
	}

	@Test
	public void testAddInheritedClassObject() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(3);
		col.add(0.5);
		ArrayIndexedCollection<Number> newCol = new ArrayIndexedCollection<>(col);
		assertArrayEquals(new Object[] { 3, 0.5 }, newCol.toArray());
	}

	@Test
	public void testSize() {
		ArrayIndexedCollection<String> col = new ArrayIndexedCollection<>();
		col.add("Hello");
		assertEquals(1, col.size());
	}

	@Test
	public void testIsEmpty() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		assertTrue(col.isEmpty());
		col.add(5);
		assertFalse(col.isEmpty());
	}

	@Test
	public void testAddNullShouldThrow() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
		assertThrows(NullPointerException.class, () -> col.add(null));
	}

	@Test
	public void testAdd() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		int number = 26;
		col.add(number);
	}

	@Test
	public void testContains() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(5);
		assertTrue(col.contains(5));
		assertFalse(col.contains(3));
		assertFalse(col.contains("string"));
	}

	@Test
	public void testRemoveObject() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(5);
		col.add(6);
		col.remove((Object) 5);
		assertArrayEquals(new Object[] { 6 }, col.toArray());
	}

	@Test
	public void testRemoveObjectNotPresentInTheCollection() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(5);
		col.add(6);
		assertFalse(col.remove("aaa"));
	}

	@Test
	public void testRemoveInvalidIndexShouldThrow() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(0));
	}

	@Test
	public void testRemoveIndex() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(2);
		col.add(5);
		col.add(8);
		col.remove(1);
		assertArrayEquals(new Object[] { 2, 8 }, col.toArray());
	}

	@Test
	public void testToArray() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(5);
		col.add(4.5);
		Object[] objects = col.toArray();
		assertArrayEquals(new Object[] { 5, 4.5 }, objects);
	}

	@Test
	public void testClear() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(1);
		col.clear();
		assertEquals(0, col.size());
	}

	@Test
	public void testGet() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(34);
		assertEquals(34, col.get(0));
	}

	@Test
	public void testGetObjectInvalidIndexShouldThrow() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-3));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(5));

	}

	@Test
	public void testInsertObjectInvalidIndexShouldThrow() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5, 3));
	}

	@Test
	public void testInsertNullShouldThrow() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
		assertThrows(NullPointerException.class, () -> col.insert(null, 0));
	}

	@Test
	public void testInsertFromSubclass() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();
		col.add(2);
		col.add(7);
		col.insert(3, 1);
		col.insert(0.5, 0);
		assertArrayEquals(new Object[] { 0.5, 2, 3, 7 }, col.toArray());
	}

	@Test
	public void testIndexOf() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(5);
		col.add(3);
		assertEquals(1, col.indexOf(3));
	}

	@Test
	public void testIndexOfNotFound() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
		assertEquals(-1, col.indexOf(null));
		assertEquals(-1, col.indexOf(1));
	}

	@Test
	public void testAddAll() {
		ArrayIndexedCollection<Integer> col = new ArrayIndexedCollection<>();
		col.add(5);

		ArrayIndexedCollection<Integer> other = new ArrayIndexedCollection<>();
		other.add(6);
		other.add(10);
		col.addAll(other);

		assertEquals(6, col.get(1));
		assertEquals(2, other.size());
		assertEquals(3, col.size());
	}

	@Test
	public void testAddAllFromSubclass() {
		ArrayIndexedCollection<Object> col = new ArrayIndexedCollection<>();
		col.add(5);

		ArrayIndexedCollection<Number> other = new ArrayIndexedCollection<>();
		other.add(6);
		other.add(1.2);
		col.addAll(other);

		assertEquals(6, col.get(1));
		assertEquals(2, other.size());
		assertEquals(3, col.size());
	}

	@Test
	public void testAddAllSatisfying() {
		ArrayIndexedCollection<Number> col = new ArrayIndexedCollection<>();

		ArrayIndexedCollection<Integer> other = new ArrayIndexedCollection<>();
		other.add(6);
		other.add(2);
		other.add(32);
		other.add(39);
		other.add(7);

		Tester<Number> tester = value -> (int) value % 3 == 0;
		col.addAllSatisfying(other, tester);
		assertArrayEquals(new Object[] { 6, 39 }, col.toArray());
	}

}
