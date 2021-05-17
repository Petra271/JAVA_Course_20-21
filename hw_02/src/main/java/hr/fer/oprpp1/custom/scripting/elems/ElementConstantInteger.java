package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents a constant integer element. It inherits from {@link Element}.
 * 
 * @author Petra
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Integer element value
	 */
	private int value;

	/**
	 * Creates an instance of the class with the given value.
	 * 
	 * @param value value of a constant integer element
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Returns string representation of value property.
	 */
	@Override
	public String asText() {
		return String.valueOf(this.value);
	}

}
