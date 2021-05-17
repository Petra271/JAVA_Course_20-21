package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * This class represents an object which maps keys to values. A dictionary can
 * not contain duplicate keys; each key can map to at most one value.
 * 
 * @author Petra
 *
 * @param <K> the type of keys
 * @param <V> the type of mapped values
 * 
 */
public class Dictionary<K, V> {

	/**
	 * ArrayIndexedCollection used for storing the dictionary objects.
	 */
	private ArrayIndexedCollection<Entry<K, V>> col = new ArrayIndexedCollection<>();

	/**
	 * The class represents key and value pair.
	 * 
	 * @author Petra
	 *
	 * @param <K> type of the entry key
	 * @param <V> type of the value mapped to the key
	 */
	private static class Entry<K, V> {

		/**
		 * The entry key
		 */
		private K key;

		/**
		 * The entry value
		 */
		private V value;

		/**
		 * Creates an instance of this class.
		 * 
		 * @param key   entry key
		 * @param value entry value
		 * @throws {@link NullPointerException} if the given key is <code>null</code>
		 */
		public Entry(K key, V value) {
			Objects.requireNonNull(key, "The key mustn't be null.");
			this.key = key;
			this.value = value;
		}
	}

	/**
	 * Checks if the dictionary is empty.
	 * 
	 * @return <code>true</code> if the dictionary is empty and <code>false</code>
	 *         otherwise
	 */
	public boolean isEmpty() {
		return this.col.isEmpty();
	}

	/**
	 * Returns the size of this dictionary.
	 * 
	 * @return the size of a dictionary
	 */
	public int size() {
		return this.col.size();
	}

	/**
	 * Removes all of the elements from this dictionary.
	 *
	 */
	public void clear() {
		this.col.clear();
	}

	/**
	 * If the given key is already present in the dictionary, the method overwrites
	 * the value mapped to that key. If the given key is not in the dictionary, the
	 * method adds a new entry.
	 * 
	 * @param key   entry key
	 * @param value the value mapped to the key
	 * @return value previously mapped to the given key if the key is already
	 *         present in the dictionary and <code>null</code> otherwise
	 */
	public V put(K key, V value) {
		if (this.getEntry(key) != null) {
			V oldValue = this.getEntry(key).value;
			this.getEntry(key).value = value;
			return oldValue;
		}
		this.col.add(new Entry<>(key, value));
		return null;
	}

	/**
	 * Returns the mapped value if the given key is present in this dictionary.
	 * 
	 * @param key entry key
	 * @return the mapped value if the given key is present in the dictionary and
	 *         <code>null</code> otherwise
	 */
	public V get(Object key) {
		if (this.getEntry(key) != null) {
			return this.getEntry(key).value;
		}
		return null;
	}

	/**
	 * Removes the entry of the given key if the key is present in this dictionary.
	 * 
	 * @param key entry key
	 * @return value mapped to the given key if the key is present in this
	 *         dictionary and <code>null</code> otherwise
	 */
	public V remove(K key) {
		if (this.getEntry(key) != null) {
			V value = this.getEntry(key).value;
			this.col.remove(this.getEntry(key));
			return value;
		}
		return null;
	}

	/**
	 * Returns the entry of the given key if it is present in this dictionary.
	 * 
	 * @param key entry key
	 * @return entry of the given key if it is present in the dictionary and
	 *         <code>null</code> otherwise
	 */
	private Entry<K, V> getEntry(Object key) {
		for (int i = 0; i < this.col.size(); i++) {
			Entry<K, V> e = this.col.get(i);
			if (e.key.equals(key)) {
				return e;
			}
		}
		return null;
	}

}
