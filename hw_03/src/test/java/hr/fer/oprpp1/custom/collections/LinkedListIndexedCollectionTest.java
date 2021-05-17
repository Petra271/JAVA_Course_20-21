package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	
	@Test
	public void testCollectionGivenToConstructorNullShouldThrow() {
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection<>(null));
	}
	
	@Test
	public void testCopyCollectionConstructor() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
		col.add(3);
		col.add(4);
		LinkedListIndexedCollection<Integer> newCol = new LinkedListIndexedCollection<>(col);
		assertArrayEquals(new Object[] {3, 4}, newCol.toArray());
	}
	
	@Test
	public void testSize() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.add(6);
		assertEquals(2, col.size());
	}
	
	@Test
	public void testIsEmpty() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
		assertTrue(col.isEmpty());
		col.add(5);
		assertFalse(col.isEmpty());
	}
	
	@Test
	public void testAddNullShouldThrow() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void testAdd() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.add(10);
		assertArrayEquals(new Object[] {5, 10}, col.toArray());
	}
	
	@Test
	public void testContains() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(2);
		assertTrue(col.contains(2));
		assertFalse(col.contains(null));
		assertFalse(col.contains(-3));
	}
	
	@Test
	public void testRemoveObject() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
		col.add(8);
		col.add(6);
		assertTrue(col.remove((Object)8));
		assertArrayEquals(new Object[] {6}, col.toArray());
	}
	
	@Test
	public void testRemoveObjectNotPresentInTheCollection() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.add(6);
		assertFalse(col.remove("aaa"));
	}
	
	@Test 
	public void testRemoveInvalidIndexShouldThrow() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(3));
	}
	
	@Test
	public void testRemoveFirst() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.add(12);
		col.remove(0);
		assertArrayEquals(new Object[] {12}, col.toArray());
	}
	
	@Test
	public void testRemoveLast() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.add(3);
		col.add(4);
		col.add(5);
		col.remove(2);
		assertArrayEquals(new Object[] {3, 4}, col.toArray());
	}
	
	@Test
	public void testRemoveFromFirstHalf() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(4);
		col.add(5);
		col.add(8);
		col.add(6);
		col.remove(1);
		assertArrayEquals(new Object[] {4, 8, 6}, col.toArray());	
	}
	
	@Test
	public void testRemoveFromSecondHalf() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(4);
		col.add(5);
		col.add(8);
		col.add(6);
		col.remove(2);
		assertArrayEquals(new Object[] {4, 5, 6}, col.toArray());	
	}
	
	@Test
	public void testToArray() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.add(10);
		Object[] objects = col.toArray();
		assertArrayEquals(new Object[] {5, 10}, objects);
	}
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection<Integer> col = new LinkedListIndexedCollection<>();
		col.add(1);
		col.add(2);
		col.clear();
		assertEquals(0, col.size());
	}
	
	@Test
	public void testGet() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(34);
		assertEquals(34, col.get(0));
	}
	
	@Test
	public void testGetObjectInvalidIndexShouldThrow() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-3));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(5));
	}
	
	@Test
	public void testInsertObjectInvalidIndexShouldThrow() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5, 3));
	}
	
	@Test
	public void testInsertNullShouldThrow() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		assertThrows(NullPointerException.class,() -> col.insert(null, 0));
	}
	
	@Test
	public void testInsertFromSubclass() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();
		col.add(2);
		col.add(7);
		col.insert(3, 1);
		col.insert(0.5, 0);
		assertArrayEquals(new Object[] { 0.5, 2, 3, 7 }, col.toArray());
	}
	
	@Test
	public void testInsertEmptyCollection() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.insert(4, 0);
		assertEquals(4, col.get(0));
	}
	
	@Test
	public void testInsertFirstIndex() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.insert(3, 0);
		assertArrayEquals(new Object[] {3, 5}, col.toArray());
	}
	
	@Test
	public void testInsertLast() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.add(6);
		col.insert(3, 1);
		assertArrayEquals(new Object[] {5, 3, 6}, col.toArray());
	}
	
	@Test
	public void testInsert() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.add(1);
		col.add(2);
		col.add(7);
		col.insert(3, 1);
		assertArrayEquals(new Object[] {1, 3, 2, 7}, col.toArray());
	}

	
	@Test
	public void testIndexOf() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.add(5);
		col.add(3);
		assertEquals(1, col.indexOf(3));
	}
	
	@Test
	public void testIndexOfNotFound() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		assertEquals(-1, col.indexOf(null));
		assertEquals(-1, col.indexOf(1));
	}
	
	@Test
	public void testAddAll() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.add(5);
		
		LinkedListIndexedCollection<Number> other = new LinkedListIndexedCollection<>();
		other.add(6);
		other.add(10);
		col.addAll(other);
		
		assertArrayEquals(new Object[] {5, 6, 10}, col.toArray());
		assertArrayEquals(new Object[] {6, 10}, other.toArray());
	}
	
	@Test
	public void testAddAllFromSubclass() {
		LinkedListIndexedCollection<Object> col = new LinkedListIndexedCollection<>();
		col.add(5);

		LinkedListIndexedCollection<Number> other = new LinkedListIndexedCollection<>();
		other.add(6);
		other.add(1.2);
		col.addAll(other);

		assertEquals(6, col.get(1));
		assertEquals(2, other.size());
		assertEquals(3, col.size());
	}
	
	@Test
	public void testAddAllSatisfying() {
		LinkedListIndexedCollection<Number> col = new LinkedListIndexedCollection<>();

		LinkedListIndexedCollection<Integer> other = new LinkedListIndexedCollection<>();
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
