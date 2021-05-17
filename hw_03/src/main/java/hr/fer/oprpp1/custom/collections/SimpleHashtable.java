package hr.fer.oprpp1.custom.collections;

import java.lang.Math;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class represents a simple hashtable which maps keys to values. A
 * dictionary can not contain duplicate keys; each key can map to at most one
 * value.
 * 
 * @author Petra
 *
 * @param <K> the type of keys
 * @param <V> the type of mapped values
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Simple hashable table
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Number of all table entries
	 */
	private int size;

	/**
	 * Number of modifications
	 */
	private long modificationCount;

	/**
	 * Default table capacity
	 */
	static final int DEFAULT_INITIAL_CAPACITY = 16;

	/**
	 * This class represents a table slot which has a key, a value mapped to that
	 * key and a variable next which points to the next entry in the same slot.
	 * 
	 * @author Petra
	 *
	 * @param <K> type of the entry key
	 * @param <V> type of the value mapped to the key
	 */
	public static class TableEntry<K, V> {

		/**
		 * Entry key
		 */
		private K key;

		/**
		 * Entry value
		 */

		private V value;

		/**
		 * Reference to the next entry in the same table slot.
		 */
		private TableEntry<K, V> next;

		/**
		 * Creates an instance of this class.
		 * 
		 * @param key   table entry key
		 * @param value table entry value mapped to the key
		 * @param next  reference to the next entry in the same table slot
		 * @throws {@link NullPointerException} if the given key is <code>null</code>
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			Objects.requireNonNull(key, "The entry key can not be null.");
			this.key = key;
			this.value = value;
			this.next = next;
		}

		/**
		 * Returns the table entry key.
		 * 
		 * @return the table entry key
		 */
		public K getKey() {
			return this.key;
		}

		/**
		 * Returns the table entry value.
		 * 
		 * @return the table entry value
		 */
		public V getValue() {
			return this.value;
		}

		/**
		 * Sets the table entry value.
		 * 
		 * @param value the value which the table entry value is set to
		 */
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.key + "=" + this.value;
		}
	}

	/**
	 * Creates an instance of the class and sets the table capacity to the default
	 * capacity.
	 */
	public SimpleHashtable() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Creates an instance of the class and sets the table capacity to the given
	 * value.
	 * 
	 * @param initialCapacity the method finds the nearest power of 2 to this value
	 *                        and sets the table capacity to the found value
	 * @throws {@link IllegalArgumentException} if the given value is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Initial capacity must be greater that 1.");
		}

		initialCapacity = (int) Math.pow(2, Math.ceil(Math.log(initialCapacity) / Math.log(2)));
		this.table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];
	}

	/**
	 * Returns the number of all entries.
	 * 
	 * @return the number of entries
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Checks if this map is empty.
	 * 
	 * @return <code>true</code> if the map is empty and <code>false</code>
	 *         otherwise
	 */
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * If the given key is already present in the map, the method overwrites the
	 * value mapped to that key. If the given key is not in the map, the method adds
	 * a new entry.
	 * 
	 * @param key   the entry key
	 * @param value the value mapped to the key
	 * @return value previously mapped to the given key if the key is already
	 *         present in the map and <code>null</code> otherwise
	 * @throws {@link NullPointerException} if the given key is <code>null</code>
	 */
	public V put(K key, V value) {
		Objects.requireNonNull(key, "The key can not be null.");

		this.checkCapacity();

		int index = Math.abs(key.hashCode()) % this.table.length;
		TableEntry<K, V> e = this.table[index];

		if (e == null) {
			this.table[index] = new TableEntry<>(key, value, null);
			this.size++;
			this.modificationCount++;
			return null;
		}

		if (this.getEntry(e, key) != null) {
			this.getEntry(e, key).value = value;
			return null;
		}

		while (e.next != null)
			e = e.next;

		e.next = new TableEntry<>(key, value, null);
		this.size++;
		this.modificationCount++;
		return null;
	}

	/**
	 * Checks the table occupancy and if it is greater than 75%, the method doubles
	 * the capacity.
	 */
	@SuppressWarnings("unchecked")
	private void checkCapacity() {
		if (this.size / this.table.length > 0.75) {
			TableEntry<K, V>[] array = this.toArray();
			this.clear();
			this.table = (TableEntry<K, V>[]) new TableEntry[this.table.length * 2];
			for (TableEntry<K, V> e : array) {
				this.put(e.key, e.value);
			}
		}
	}

	/**
	 * Returns the mapped value if the given key is present in this map.
	 * 
	 * @param key entry key
	 * @return the mapped value if the given key is present in the map and
	 *         <code>null</code> otherwise
	 */
	public V get(Object key) {
		if (key == null)
			return null;
		int index = Math.abs(key.hashCode()) % this.table.length;
		TableEntry<K, V> e = this.table[index];
		if (this.getEntry(e, key) != null) {
			return this.getEntry(e, key).getValue();
		}
		return null;
	}

	/**
	 * Checks if this map contains the given key.
	 * 
	 * @param key value for which it is checked to see if it one of the keys in this
	 *            map
	 * @return <code>true</code> if the map contains the given key and
	 *         <code>false</code> otherwise
	 */
	public boolean containsKey(Object key) {
		if (this.get(key) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if this map contains the given value.
	 * 
	 * @param value value for which it is checked to see if it one of the mapped
	 *              values in this map
	 * @return <code>true</code> if the map contains the given value and
	 *         <code>false</code> otherwise
	 */
	public boolean containsValue(Object value) {
		for (TableEntry<K, V> e : this.table) {
			if (e == null)
				continue;
			if (e.getValue() == value) {
				return true;
			}
			if (e.getValue().equals(value))
				return true;
			while (e.next != null) {
				if (e.getValue().equals(value)) {
					return true;
				}
				e = e.next;
				if (e.getValue().equals(value))
					return true;
			}
		}
		return false;
	}

	/**
	 * Removes the entry which contains the given key if the key is present in this
	 * map.
	 * 
	 * @param key key of the entry which will be removed if it is present in the map
	 * @return the value of the removed entry or <code>null</code> if no entry was
	 *         removed
	 */
	public V remove(Object key) {
		if (key == null)
			return null;

		int index = Math.abs(key.hashCode()) % this.table.length;
		TableEntry<K, V> e = this.table[index];

		TableEntry<K, V> previous = null;
		while (e != null && !e.getKey().equals(key)) {
			previous = e;
			e = e.next;
		}

		if (e == null)
			return null;
		if (previous == null) {
			this.table[index] = e.next;
		} else {
			previous.next = e.next;
		}
		this.size--;
		this.modificationCount++;
		return e.getValue();
	}

	/**
	 * Returns string representation of this map.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (TableEntry<K, V> e : this.toArray()) {
			sb.append(e.toString()).append(", ");
		}
		String s = sb.toString().replaceAll(", $", "");
		s += "]";
		return s;
	}

	/**
	 * Returns a reference array of all of the map entries.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public TableEntry<K, V>[] toArray() {
		TableEntry<K, V>[] array = (TableEntry<K, V>[]) new TableEntry[this.size()];
		int i = 0;
		for (TableEntry<K, V> e : this.table) {
			while (e != null) {
				array[i] = e;
				e = e.next;
				i++;
			}
		}
		return array;
	}

	/**
	 * Removes all entries from this map.
	 */
	@SuppressWarnings("unused")
	public void clear() {
		for (TableEntry<K, V> e : this.table) {
			e = null;
		}
		this.size = 0;
	}

	/**
	 * Returns the entry of the given key if it is present in this map.
	 * 
	 * @param key entry key
	 * @return entry of the given key if it is present in the map and
	 *         <code>null</code> otherwise
	 */
	private TableEntry<K, V> getEntry(TableEntry<K, V> e, Object key) {
		while (e != null) {
			if (e.getKey().equals(key))
				return e;
			e = e.next;
		}
		// if(e.getKey().equals(key)) return e;
		return null;
	}

	private class IteratorImpl implements Iterator<SimpleHashtable.TableEntry<K, V>> {

		/**
		 * Saved number of iterations before the iteration
		 */
		private long savedModificationCount = modificationCount;

		/**
		 * Entry used for iteration
		 */
		private TableEntry<K, V> current;

		/**
		 * Counter of table entries
		 */
		private int entryCounter = 0;

		/**
		 * Current slot index tracker
		 */
		private int index = 0;

		/**
		 * Flag used for checking if an entry has already been removed
		 */
		private boolean alreadyRemoved = false;

		/**
		 * Checks if the map has any entries left to be read.
		 * 
		 * @throws {@link ConcurrentModificationException} if the map has been modified
		 *                during the iteration
		 * @return <code>true</code> if the map has more entries and <code>false</code>
		 *         otherwise
		 */
		@Override
		public boolean hasNext() {
			if (modificationCount > this.savedModificationCount) {
				throw new ConcurrentModificationException("The map has been modified during the iteration");
			}
			return this.entryCounter < size;
		}

		/**
		 * Returns the next entry in the map.
		 * 
		 * @return the next entry in the map
		 * @throws NoSuchElementException if there is no more entries left in the map
		 */
		@Override
		public TableEntry<K, V> next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException("There is no more elements left in the collection.");
			}

			alreadyRemoved = false;
			if (current == null || current.next == null) {
				if (current != null)
					this.index++;
				current = table[this.index];
				while (current == null) {
					this.index++;
					current = table[this.index];
				}
				this.entryCounter++;
				return current;
			}

			if (current.next != null) {
				current = current.next;
				this.entryCounter++;
			}
			return current;
		}

		/**
		 * Removes the current entry from the map.
		 * 
		 * @throws ConcurrentModificationException if the map has been modified during
		 *                                         the iteration
		 * @throws IllegalArgumentException        if the
		 */
		public void remove() {
			if (modificationCount > this.savedModificationCount) {
				throw new ConcurrentModificationException("The map has been modified during the iteration");
			}

			if (alreadyRemoved) {
				throw new IllegalStateException("This element has already been removed");
			}

			SimpleHashtable.this.remove(this.current.key);
			this.current = null;
			this.alreadyRemoved = true;
			this.savedModificationCount++;
			this.entryCounter--;
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	public static void main(String[] args) {

		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
			iter.remove();
		}
		System.out.printf("Veličina: %d%n", examMarks.size());
	}

}
