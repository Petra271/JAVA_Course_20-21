package hr.fer.oprpp1.custom.scripting.elems;

/**
 * Represents an operator. It inherits from {@link Element}.
 * 
 * @author Petra
 *
 */
public class ElementOperator extends Element {

	/**
	 * Operator symbol
	 */
	private String symbol;

	/**
	 * Creates an instance of this class with the given symbol.
	 * 
	 * @param symbol operator symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * Returns the operator symbol.
	 */
	@Override
	public String asText() {
		return this.symbol;
	}

}
