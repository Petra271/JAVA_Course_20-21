package hr.fer.oprpp1.custom.collections;

/**
 * The class Processor represents a model of an object capable of performing
 * some operation on the passed object.
 * 
 * @author Petra
 *
 * @param <T> object that is being processed
 */
@FunctionalInterface
public interface Processor<T> {

	/**
	 * Used for performing an action on the given object.
	 * 
	 * @param object object on which the action will be done
	 */
	void process(T t);

}

