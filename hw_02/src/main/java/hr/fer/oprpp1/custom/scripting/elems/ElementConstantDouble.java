package hr.fer.oprpp1.custom.scripting.elems;


/**
 * Represents a constant double element. It inherits from {@link Element}.
 * 
 * @author Petra
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Double element value
	 */
	private double value;

	/**
	 * Creates an instance of the class with the given value.
	 * 
	 * @param value value of a constant double element
	 */
	public ElementConstantDouble(double value) {
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