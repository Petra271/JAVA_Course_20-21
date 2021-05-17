package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {
	
	
	@Test
	public void testInitialCapacityLessThanOneShouldThrow() {
		assertThrows(IllegalArgumentException.class, () 
				-> new ArrayIndexedCollection(-5));
		assertThrows(IllegalArgumentException.class, () 
				-> new ArrayIndexedCollection(new ArrayIndexedCollection(), -3));
	}
	
	@Test
	public void testCollectionGivenToConstructorNullShouldThrow() {
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 4));
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
	}
	
	@Test
	public void testCopyCollectionConstructor() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(3);
		col.add(4);
		ArrayIndexedCollection newCol = new ArrayIndexedCollection(col);
		assertEquals(4, newCol.get(1));
		assertEquals(2, newCol.size());
	}
	
	@Test
	public void testSize() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Hello");
		col.add(2);
		assertEquals(2, col.size());
	}
	
	@Test
	public void testIsEmpty() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertTrue(col.isEmpty());
		col.add(5);
		assertFalse(col.isEmpty());
	}
	
	@Test
	public void testAddNullShouldThrow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> col.add(null));
	}
	
	@Test
	public void testAdd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		int number = 26;
		col.add(number);
	}
	
	@Test
	public void testContains() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		assertTrue(col.contains(5));
		assertFalse(col.contains(3));	
		assertFalse(col.contains(null));
	}
	
	@Test
	public void testRemoveObject() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		col.add(6);
		col.remove((Object)5);
		assertArrayEquals(new Object[] {6}, col.toArray());
	}
	
	@Test
	public void testRemoveInvalidIndexShouldThrow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(0));
	}
	
	@Test
	public void testRemoveIndex() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(2);
		col.add(5);
		col.add(8);
		col.remove(1);
		assertArrayEquals(new Object[] {2, 8}, col.toArray());
	}
	
	@Test
	public void testToArray() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		col.add(10);
		Object[] objects = col.toArray();
		assertArrayEquals(new Object[] {5, 10}, objects);
	}
	
	@Test
	public void testClear() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(1);
		col.clear();
		assertEquals(0, col.size());
	}
	
	@Test
	public void testGet() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(34);
		assertEquals(34, col.get(0));	
	}
	
	
	@Test
	public void testGetObjectInvalidIndexShouldThrow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-3));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(5));
		
	}
	
	@Test
	public void testInsertObjectInvalidIndexShouldThrow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert(5, 3));
	}
	
	@Test
	public void testInsertNullShouldThrow() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class,() -> col.insert(null, 0));
	}
	
	@Test
	public void testInsert() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(2);
		col.add(7);
		col.insert(3, 1);
		assertArrayEquals(new Object[] {2, 3, 7}, col.toArray());
	}
	
	@Test
	public void testIndexOf() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		col.add(3);
		assertEquals(1, col.indexOf(3));
	}
	
	@Test
	public void testIndexOfNotFound() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertEquals(-1, col.indexOf(null));
		assertEquals(-1, col.indexOf(1));
	}
	
	@Test
	public void testAddAll() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add(5);
		
		ArrayIndexedCollection other = new ArrayIndexedCollection();
		other.add(6);
		other.add(10);
		col.addAll(other);
		
		assertEquals(6, col.get(1));
		assertEquals(2, other.size());
		assertEquals(3, col.size());
	}

}
