package hr.fer.oprpp1.custom.collections;

/**
 * Class Collection represents a general collection of objects.
 * 
 * @author Petra
 *
 */

public class Collection {

	/**
	 * Creates a new Collection object which represents some general collection of
	 * objects.
	 * 
	 */
	protected Collection() {
	}

	/**
	 * Checks if the collection is empty or not.
	 * 
	 * @return <code>true</code> if the collection contains no objects and
	 *         <code>false</code> otherwise
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns the number of elements stored in this collection.
	 * 
	 * @return number of elements stored in the collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object to the collection.
	 * 
	 * @param value the object that will be added to the collection
	 */
	public void add(Object value) {
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
	public boolean contains(Object value) {
		return false;
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
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Returns an array of all the elements in this collection.
	 * 
	 * @return an array of all the elements in this collection
	 * @throws UnsupportedOperationException if the array can not be created because
	 *                                       this method must never return <code>null</code>
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method calls {@link Processor#process(Object)} for each element.
	 * 
	 * @param processor object that will be called to perform an action on each
	 *                  element of the collection
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Adds to this collection all the elements from the given collection. The given
	 * collection remains unchanged.
	 * 
	 * @param other collection that will be added to the current collection.
	 */
	public void addAll(Collection other) {

		class addProcessor extends Processor {

			@Override
			public void process(Object value) {
				add(value);
			}
		}
		other.forEach(new addProcessor());
	}

	/**
	 * Removes all elements from this collection.
	 *
	 */
	public void clear() {
	}

}
