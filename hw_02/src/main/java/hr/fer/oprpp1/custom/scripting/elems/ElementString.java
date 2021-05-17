package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents a string element. It inherits from {@link Element}.
 * 
 * @author Petra
 *
 */
public class ElementString extends Element {

	/**
	 * String element value
	 */
	private String value;

	/**
	 * Creates an instance of the class with the given value.
	 * 
	 * @param value value of a string element
	 */
	public ElementString(String value) {
		this.value = value;
	}

	/**
	 * Returns string representation of value property.
	 */
	@Override
	public String asText() {
		return this.value;
	}

}