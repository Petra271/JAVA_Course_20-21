package hr.fer.oprpp1.custom.collections;

import java.util.Objects;

/**
 * The class represents a linked list-backed collection of objects.
 * 
 * @author Petra
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Current size of collection. Number of nodes in the list.
	 */
	private int size;

	/**
	 * Reference to the first node of the linked list.
	 */
	private ListNode first;

	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode last;

	/**
	 * The class represents a list node.
	 * 
	 * @author Petra
	 *
	 */
	private static class ListNode {

		/**
		 * Value of the node.
		 */
		Object value;

		/**
		 * Pointer to the next list node.
		 */
		ListNode next;

		/**
		 * Pointer to the previous list node.
		 */
		ListNode previous;
	}

	/**
	 * Creates an instance of the class.
	 */
	public LinkedListIndexedCollection() {
		this.first = this.last = null;
	}

	/**
	 * Creates a new collection and copies elements of another collection into the
	 * newly created one.
	 * 
	 * @param other collection whose elements are copied into this newly constructed
	 *              collection
	 * @throws NullPointerException if the given collection is <code>null</code>
	 */
	public LinkedListIndexedCollection(Collection other) {
		Objects.requireNonNull(other, "The given collection mustn't be null.");
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
	 * Adds the given object to the collection.
	 * 
	 * @param value the object that will be added to the collection
	 * @throws NullPointerException if the given value is <code>null</code>
	 */
	@Override
	public void add(Object value) {
		Objects.requireNonNull(value, "The given object mustn't be null.");

		ListNode newElement = new ListNode();

		if (this.first == null) {
			this.first = this.last = newElement;
			this.first.value = this.last.value = value;
		} else {
			newElement.value = value;
			this.last.next = newElement;
			newElement.previous = this.last;
			this.last = newElement;
		}
		this.size++;
	}

	/**
	 * Checks if the collections contains the given value. It is OK to ask if the
	 * collection contains <code>null</code>.
	 * 
	 * @param value the object that will be checked to see whether it is a part of
	 *              the collection
	 * @return <code>true</code> if the collection contains the given value and
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean contains(Object value) {
		return this.indexOf(value) != -1;

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
		if (this.indexOf(value) != -1) {
			this.remove(this.indexOf(value));
			return true;
		}
		return false;
	}

	/**
	 * Returns an array of all the elements in this collection.
	 * 
	 * @return an array of all the elements in this collection
	 */
	@Override
	public Object[] toArray() {
		return new ArrayIndexedCollection(this).toArray();
	}

	/**
	 * Method calls {@link Processor#process(Object)} for each element.
	 * 
	 * @param processor object that will be called to perform an action on each
	 *                  element of the collection
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode node = this.first; node != null; node = node.next) {
			processor.process(node.value);
		}
	}

	/**
	 * Removes all elements from this collection.
	 *
	 */
	@Override
	public void clear() {
		for (ListNode node = this.first; node != null; node = node.next) {
			node.value = null;
			node.next = null;
			node.previous = null;
		}
		this.first = this.last = null;
		this.size = 0;
	}

	/**
	 * Returns the object that is stored in linked list at position index
	 * 
	 * @param index index of the Object that will be returned
	 * @return Object that is stored at position <code>index</code>
	 * @throws IndexOutOfBoundsException if index is less than 0 or greater than the
	 *                                   largest index in the collection
	 */
	public Object get(int index) {
		if (index < 0 || index > (this.size - 1))
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size-1");

		if (index <= this.size / 2) {
			ListNode node = this.first;
			for (int i = 0; i < index; i++)
				node = node.next;
			return node.value;
		} else {
			ListNode node = this.last;
			for (int i = this.size - 1; i > index; i--)
				node = node.previous;
			return node.value;
		}
	}

	/**
	 * Inserts the given value at the given position in the linked list.
	 * 
	 * @param value    object that will be inserted
	 * @param position position at which the object will be inserted
	 * @throws NullPointerException      if the given object is <code>null</code>
	 * @throws IndexOutOfBoundsException if the given position is less than 0 or
	 *                                   greater than the size of the collection
	 */
	public void insert(Object value, int position) {
		Objects.requireNonNull(value, "The given object mustn't be null.");
		if (position < 0 || position > this.size)
			throw new IndexOutOfBoundsException("Valid indexes are 0 to size");

		ListNode newElement = new ListNode();
		newElement.value = value;

		if (this.first == null) {
			this.first = this.last = newElement;
			this.first.value = this.last.value = value;
		} else {
			if (position == 0) {
				newElement.next = this.first;
				this.first.previous = newElement;
				this.first = newElement;
			} else if (position == this.size) {
				newElement.previous = this.last;
				this.last.next = newElement;
				this.last = newElement;

			} else {
				if (position <= this.size / 2) {
					ListNode node = this.first;
					for (int i = 0; i < position; i++) {
						node = node.next;
					}
					newElement.next = node;
					newElement.previous = node.previous;
					node.previous.next = newElement;
					node.previous = newElement;
				}
			}
		}
		this.size++;
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

		int index = 0;
		for (ListNode node = this.first; node != null; node = node.next) {
			if (value.equals(node.value))
				return index;
			index++;
		}
		return -1;
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

		if (index == 0) {
			this.first = this.first.next;
			this.first.previous = null;
		} else if (index == this.size - 1) {
			this.last = this.last.previous;
			this.last.next = null;
		} else {
			if (index <= this.size / 2) {
				ListNode node = this.first;
				for (int i = 0; i < index; i++) {
					node = node.next;
				}
				node.previous.next = node.next;
				node.next.previous = node.previous;
			} else {
				ListNode node = this.last;
				for (int i = this.size - 1; i > index; i--) {
					node = node.previous;
				}
				node.previous.next = node.next;
				node.next.previous = node.previous;
			}
		}

		this.size--;
	}

}
