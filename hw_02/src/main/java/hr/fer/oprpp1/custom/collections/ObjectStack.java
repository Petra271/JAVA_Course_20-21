package hr.fer.oprpp1.custom.collections;


/**
 * The class represents a stack of objects. It uses
 * {@link ArrayIndexedCollection} for the storage, but it provides methods which
 * are natural for a stack.
 * 
 * @author Petra
 *
 */
public class ObjectStack {

	/**
	 * ArrayIndexedCollection used for storing the stack objects.
	 */
	private ArrayIndexedCollection col = new ArrayIndexedCollection();

	/**
	 * Checks if the stack is empty.
	 * 
	 * @return <code>true</code> is the stack is empty and <code>false</code>
	 *         otherwise
	 */
	public boolean isEmpty() {
		return col.isEmpty();
	}

	/**
	 * Returns the number of elements in the stack.
	 * 
	 * @return number of elements in the stack.
	 */
	public int size() {
		return col.size();
	}

	/**
	 * Pushes the given value on the stack.
	 * 
	 * @param value the objects that will be pushed on the stack
	 */
	public void push(Object value) {
		col.add(value);
	}

	/**
	 * Removes last value pushed on the stack and returns it.
	 * 
	 * @return last value pushed on the stack
	 * @throws EmptyStackException if the stack is empty when the method is called
	 */
	public Object pop() {
		if (col.isEmpty())
			throw new EmptyStackException("The stack is empty.");

		Object popped = peek();
		col.remove(col.size() - 1);
		return popped;
	}

	/**
	 * Returns the last element placed on the stack.
	 * 
	 * @return the last element placed on the stack
	 * @throws EmptyStackException if the stack is empty when the method is called
	 */
	public Object peek() {
		if (col.isEmpty())
			throw new EmptyStackException("The stack is empty.");

		return col.get(col.size() - 1);
	}

	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		col.clear();
	}

}
