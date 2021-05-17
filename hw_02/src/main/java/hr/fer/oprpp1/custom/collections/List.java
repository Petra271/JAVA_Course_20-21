package hr.fer.oprpp1.custom.collections;

/**
 * This interface is used for collection manipulation. It extends
 * {@link Collection}
 * 
 * @author Petra
 *
 */
public interface List extends Collection {

	/**
	 * Return an object at the given index.
	 * 
	 * @param index position of the object that will be returned
	 * @return object at the given position in the collection
	 */
	Object get(int index);

	/**
	 * Insert an object into the collection.
	 * 
	 * @param value    object that will be inserted into the collection
	 * @param position position in the collection at which the object will be
	 *                 inserted
	 */
	void insert(Object value, int position);

	/**
	 * Returns the position of the given object in the collection.
	 * 
	 * @param value objects whose index will be returned
	 * @return index of the given object in the collection
	 */
	int indexOf(Object value);

	/**
	 * Removes object at the given index in the collection.
	 * 
	 * @param index position of the object that will be removed from the collection.
	 */
	void remove(int index);

}
