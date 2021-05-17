package hr.fer.oprpp1.custom.collections;


/**
 * The top-level interface used for collection manipulation. A collection, order
 * or unordered, is used for storing objects.
 * 
 * @author Petra
 *
 * @param <T> type of objects in the collection
 */
public interface Collection<T> {

	/**
	 * Checks if the collection is empty or not.
	 * 
	 * @return <code>true</code> if the collection contains no objects and
	 *         <code>false</code> otherwise
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns the number of elements stored in this collection.
	 * 
	 * @return the number of elements stored in the collection
	 */
	int size();

	/**
	 * Adds the given object to the collection.
	 * 
	 * @param value the object that will be added to the collection
	 */
	void add(T value);

	/**
	 * Checks if the collections contains the given value. It is OK to ask if the
	 * collection contains <code>null</code>.
	 * 
	 * @param value the object that will be checked to see whether it is a part of
	 *              the collection
	 * @return <code>true</code> if the collection contains the given value and
	 *         <code>false</code> otherwise
	 */
	boolean contains(Object value);

	/**
	 * Returns <code>true</code> only if the collection contains given value as
	 * determined by equals method and removes one occurrence of it.
	 * 
	 * @param value object that will be removed from the collection if the method
	 *              returns <code>true</code>
	 * @return <code>true</code> only if the collection contains the given value and
	 *         <code>false</code> otherwise
	 */
	boolean remove(Object value);

	/**
	 * Returns an array of all the elements in this collection.
	 * 
	 * @return an array of all the elements in this collection
	 * @throws UnsupportedOperationException if the array can not be created because
	 *                                       this method must never return
	 *                                       <code>null</code>
	 */
	Object[] toArray();

	/**
	 * Method calls {@link Processor#process(Object)} for each element.
	 * 
	 * @param processor object that will be called to perform an action on each
	 *                  element of the collection
	 */
	default void forEach(Processor<? super T> processor) {
		ElementsGetter<T> getter = this.createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * Adds to this collection all the elements from the given collection. The given
	 * collection remains unchanged.
	 * 
	 * @param other collection that will be added to the current collection.
	 */
	default void addAll(Collection<? extends T> other) {
		Processor<T> p = value -> add((T)value);
		other.forEach(p);
	}

	/**
	 * Removes all elements from this collection.
	 *
	 */
	void clear();

	/**
	 * Used for creating an object {@link ElementsGetter} which is capable of
	 * communicating with the user and the collection.
	 * 
	 * @return an object capable of communicating with the user and the
	 *         collection
	 */
	 ElementsGetter<T> createElementsGetter();

	/**
	 * Adds elements from the given collection to the current one if they are
	 * acceptable.
	 * 
	 * @param col    the collection whose elements are being tested and added to the
	 *               current collection if they pass the test
	 * @param tester an object used for testing the acceptability of the collection
	 *               elements
	 */
	@SuppressWarnings("unchecked")
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {

		ElementsGetter<T> getter =  (ElementsGetter<T>) col.createElementsGetter();

		while (getter.hasNextElement()) {
			T element = getter.getNextElement();
			if (tester.test(element)) {
				this.add((T)element);
			}
		}

	}
}
