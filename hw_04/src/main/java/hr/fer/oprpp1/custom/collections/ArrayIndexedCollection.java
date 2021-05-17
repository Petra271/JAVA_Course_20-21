package hr.fer.oprpp1.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * he class represents a resizable array-backed collection used for storing
 * objects.
 * 
 * @author Petra
 *
 * @param <T> type of objects in the array indexed collection
 */
public class ArrayIndexedCollection<T> implements List<T> {

	/**
	 * Current size of the collection.
	 */
	private int size;

	/**
	 * An array of object references.
	 */
	private T[] elements;
	
	/**
	 * The number of collection modifications  
	 */
	private long modificationCount = 0;

	/**
	 * Default capacity of the collection
	 */
	private static final int DEFAULT = 16;
	

	/**
	 * This class represents implementation of {@link ElementsGetter} which is used
	 * for getting and returning objects from a collection in a user-friendly way.
	 * 
	 * @author Petra
	 *
	 */
	private static class IndexedArrayElementsGetter<T> implements ElementsGetter<T> {

		/**
		 * Collection with which the {@link IndexedArrayElementsGetter} will communicate
		 * with
		 */
		private ArrayIndexedCollection<? extends T> array;

		/**
		 * Position of the element that will be returned
		 */
		private int index = 0;
		
		/**
		 * Current number of modifications of a collection
		 */
		private long savedModificationCount;

		/**
		 * Creates an instance of this class with a reference to the collection it is
		 * reading from.
		 * 
		 * @param array the collection which the object is reading from
		 */
		public IndexedArrayElementsGetter(ArrayIndexedCollection<? extends T> array) {
			this.array = array;
			this.savedModificationCount = array.modificationCount;
		}

		/**
		 * Checks if a collection has any elements left to be read.
		 * 
		 * @return <code>true</code> if the collection has more elements and
		 *         <code>false</code> otherwise
		 */
		@Override
		public boolean hasNextElement() {
			if(array.modificationCount > this.savedModificationCount) {
				throw new ConcurrentModificationException("The collection has been modified during the iteration");
			}
		    return index < array.size();
		}

		/**
		 * Returns the next element in a collection.
		 * 
		 * @return the next element in the collection
		 * @throws NoSuchElementException if there is no more elements left in the
		 *                                collection
		 */
		@Override
		public T getNextElement() {
			if (this.hasNextElement()) {
				return array.get(index++);
			}
			throw new NoSuchElementException("There is no more elements left in the collection.");
		}

	}

	/**
	 * Creates an instance of the class with capacity set to default.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT);
	}

	/**
	 * Creates an instance of the class with the given capacity.
	 * 
	 * @param initialCapacity capacity of the newly created collection
	 * @throws IllegalArgumentException if the given capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1)
			throw new IllegalArgumentException("The inital capacity has to be greater than 1.");

		this.elements = (T[]) new Object[initialCapacity];
	}

	/**
	 * Creates a new collection and copies elements of another collection into it.
	 * 
	 * @param other collection whose elements are copied into this newly constructed
	 *              collection
	 */
	public ArrayIndexedCollection(Collection<? extends T> other) {
		this(other, DEFAULT);
	}

	/**
	 * Creates a new collection and copies elements of another collection into it.
	 * If the given capacity is smaller than the size of the given collection, the
	 * size of the given collection is used for elements array preallocation.
	 * 
	 * @param other           collection whose elements are copied into this newly
	 *                        constructed collection
	 * @param initialCapacity possible capacity of the newly created collection
	 * @throws NullPointerException     if the given collection is <code>null</code>
	 * @throws IllegalArgumentException if the given capacity is less than 1
	 */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection(Collection<? extends T> other, int initialCapacity) {
		Objects.requireNonNull(other, "The given collection mustn't be null.");
		if (initialCapacity < 1)
			throw new IllegalArgumentException("The inital capacity has to be greater than 1.");

		this.elements = (T[]) new Object[initialCapacity < other.size() ? other.size() : initialCapacity];
		this.addAll(other);
	}

	/**
	 * Returns the number of elements stored in this collection.
	 * 
	 * @return number of elements stored in the collection
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Adds the given object to the collection. If the elements array is full, it
	 * should be reallocated by doubling its size.
	 * 
	 * @param value the object that will be added to the collection
	 * @throws NullPointerException if the given value is <code>null</code>
	 */
	@Override
	public void add(T value) {
		Objects.requireNonNull(value, "The given object mustn't be null.");

		if (this.size == elements.length) {
			elements = Arrays.copyOf(elements, elements.length * 2);
		}
		this.elements[this.size] = value;
		this.size++;
		this.modificationCount++;
	}

	/**
	 * Checks if the collection contains the given value. It is OK to ask if the
	 * collection contains <code>null</code>.
	 * 
	 * @param value the object that will be checked to see whether it is a part of
	 *              the collection
	 * @return <code>true</code> if the collection contains the given value and
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (this.indexOf(value) == -1)
			return false;
		return true;
	}

	/**
	 * Returns <code>true</code> only if the collection contains given value as
	 * determined by equals method and removes one occurrence of it.
	 * 
	 * @param value object that will be removed from the collection if the method
	 *              returns <code>true</code>
	 * @return <code>true</code> only if the collection contains given value and
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean remove(Object value) {
		if (this.indexOf(value) == -1)
			return false;

		this.remove(this.indexOf(value));
		this.modificationCount++;
		return true;
	}

	/**
	 * Returns an array of all the elements in this collection.
	 * 
	 * @return an array of all the elements in this collection
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(this.elements, this.size);
	}

	/**
	 * Removes all elements from this collection.
	 *
	 */
	@Override
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		this.size = 0;
		this.modificationCount++;
	}

	/**
	 * Returns the object that is stored in backing array at position
	 * <code>index</code>
	 * 
	 * @param index index of the Object that will be returned
	 * @return Object that is stored at position <code>index</code>
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than the
	 *                                   largest index in the collection
	 */
	public T get(int index) {
		if (index < 0 || index > (this.size - 1))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1");

		return this.elements[index];
	}

	/**
	 * Inserts the given value at the given position in array.
	 * 
	 * @param value    object that will be inserted
	 * @param position position at which the object will be inserted
	 * @throws NullPointerException      if the given object is <code>null</code>
	 * @throws IndexOutOfBoundsException if the given position is less than 0 or
	 *                                   greater than the size of the collection
	 */
	public void insert(T value, int position) {
		Objects.requireNonNull(value, "The given object mustn't be null.");
		if (position < 0 || position > this.size)
			throw new IndexOutOfBoundsException("Valid positions are 0 to size.");

		if (this.size == this.elements.length) {
			elements = Arrays.copyOf(elements, this.elements.length * 2);
		}

		for (int i = this.size; i > position; i--) {
			this.elements[i] = this.elements[i - 1];
		}
		this.elements[position] = value;
		this.size++;
		this.modificationCount++;
	}

	/**
	 * Removes element at specified index from collection.
	 * 
	 * @param index position of the element that will be removed
	 * @throws IndexOutOfBoundsException if the given index is less than 0 or
	 *                                   greater than the largest index in the
	 *                                   collection
	 */
	public void remove(int index) {
		if (index < 0 || index > (this.size - 1))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1");

		for (int i = index; i < this.size; i++) {
			this.elements[i] = this.elements[i + 1];
		}
		this.elements[this.size - 1] = null;
		this.size--;
		this.modificationCount++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found.
	 * 
	 * @param value object whose index will be returned
	 * @return position of the given object in the collection or -1 if the value is
	 *         not found
	 */
	public int indexOf(Object value) {
		if (value == null)
			return -1;
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Used for creating an object {@link IndexedArrayElementsGetter} which is
	 * capable of communicating with the user and the collection.
	 * 
	 * @return an object capable of capable communicating with the user and an
	 *         array-backed collection
	 */
	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new IndexedArrayElementsGetter<T>(this);
	}


}
