package hr.fer.oprpp1.custom.collections;


/**
 * Used for testing acceptability of an object.
 * 
 * @author Petra
 *
 * @param <T> object that is being tested
 */
@FunctionalInterface
public interface Tester<T> {

	/**
	 * Tests if the given object is acceptable or not.
	 * 
	 * @param obj the object that will be tested for its acceptability
	 * @return <code>true</code> if the given object is acceptable and
	 *         <code>false</code> otherwise
	 */
	boolean test(T obj);

}
