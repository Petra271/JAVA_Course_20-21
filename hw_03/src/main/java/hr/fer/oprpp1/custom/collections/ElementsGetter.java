package hr.fer.oprpp1.custom.collections;


/**
 * The interface is used for giving elements of a collection one by one to the
 * user.
 * 
 * @author Petra
 *
 * @param <T> type of elements in the collection
 */
public interface ElementsGetter<T> {

	/**
	 * Checks if a collection has any elements left to be read.
	 * 
	 * @return <code>true</code> if the collection has more elements to return and
	 *         <code>false</code> otherwise
	 * 
	 */
	boolean hasNextElement();

	/**
	 * Returns the next element in the collection.
	 * 
	 * @return the next element
	 */
	T getNextElement();

	/**
	 * Calls the given processor on all of the remaining elements in the collection.
	 * 
	 * @param p processor which processes elements that were not already returned,
	 *          that is the elements left in the collection
	 */
	default void processRemaining(Processor<? super T> p) {
		while (this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}

}
