package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Represents a token which can be text, an integer, a double, an operator, begin tag, end tag or EOF.
 * 
 * @author Petra
 *
 */
public class SmartScriptToken {

	/**
	 * Token type
	 */
	private SmartScriptTokenType type;

	/**
	 * Token value
	 */
	private Object value;

	/**
	 * Creates an instance of this class using the given type and value.
	 * 
	 * @param type  token type
	 * @param value token value
	 */
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Return the token value.
	 * 
	 * @return the value of the token
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Returns the token type.
	 * 
	 * @return the token type
	 */
	public SmartScriptTokenType getType() {
		return this.type;
	}
}