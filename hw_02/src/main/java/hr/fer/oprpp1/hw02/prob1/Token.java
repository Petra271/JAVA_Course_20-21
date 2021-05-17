package hr.fer.oprpp1.hw02.prob1;

/**
 * Represents a token which can be a word, a number, a symbol or an EOF.
 * 
 * @author Petra
 *
 */
public class Token {

	/**
	 * Token type
	 */
	private TokenType type;

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
	public Token(TokenType type, Object value) {
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
	public TokenType getType() {
		return this.type;
	}
}
