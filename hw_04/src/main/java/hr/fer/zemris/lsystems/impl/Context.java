package hr.fer.zemris.lsystems.impl;

import hr.fer.oprpp1.custom.collections.ObjectStack;

/**
 * Instances of this class enable the process of displaying fractals.
 * 
 * @author Petra
 *
 */
public class Context {

	/**
	 * Stack of turtle states
	 */
	private ObjectStack<TurtleState> stack;

	/**
	 * Creates an instance of this class.
	 */
	public Context() {
		this.stack = new ObjectStack<>();
	}

	/**
	 * Returns the last pushed element, but does not remove it from the stack
	 * 
	 * @return the last pushed element
	 */
	public TurtleState getCurrentState() {
		return this.stack.peek();
	}

	/**
	 * Adds a new object to the top of the stack.
	 * 
	 * @param state value which will be put on top of the stack
	 */
	public void pushState(TurtleState state) {
		this.stack.push(state);
	}

	/**
	 * Removes the last pushed element.
	 */
	public void popState() {
		this.stack.pop();
	}

}
