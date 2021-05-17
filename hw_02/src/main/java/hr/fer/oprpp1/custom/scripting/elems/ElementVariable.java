package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents element variable. It inherits from {@link Element}.
 * 
 * @author Petra
 *
 */
public class ElementVariable extends Element {

	/**
	 * Name of the element variable
	 */
	private String name;

	/**
	 * Creates an instance of the class with the given name.
	 * 
	 * @param name name of the element variable
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the element variable.
	 */
	@Override
	public String asText() {
		return this.name;
	}

	/**
	 * Returns name in string form.
	 */
	public String toString() {
		return this.name;
	}

}
