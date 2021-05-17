package hr.fer.oprpp1.custom.collections;

/**
 * The class Processor represents a model of an object capable of performing
 * some operation on the passed object.
 * 
 * @author Petra
 *
 */
public interface Processor {

	/**
	 * Used for performing an action on the given object.
	 * 
	 * @param value object on which the action will be done
	 */
	void process(Object value);

}
