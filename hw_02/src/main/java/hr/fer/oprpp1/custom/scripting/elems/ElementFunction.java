package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents function of an element. It inherits from {@link Element}.
 * 
 * @author Petra
 *
 */
public class ElementFunction extends Element {

	/**
	 * Name of the function
	 */
	private String name;

	/**
	 * Creates an instance of this class with the given name.
	 */
	public ElementFunction(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the function.
	 */
	@Override
	public String asText() {
		return this.name;
	}

}
