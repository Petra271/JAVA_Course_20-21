package hr.fer.oprpp1.hw04.db;

/**
 * Represents a query token.
 * 
 * @author Petra
 *
 */
public class QueryToken {

	/**
	 * Query token type
	 */
	private QueryTokenType type;

	/**
	 * Query token value
	 */
	private String value;

	/**
	 * Creates an instance of this class using the given type and value.
	 * 
	 * @param type  query token type
	 * @param value query token value
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Return the query token value.
	 * 
	 * @return the value of the query token
	 */
	public String getValue() {
		return this.value;
	}

	/**
	 * Returns the query token type.
	 * 
	 * @return the query token type
	 */
	public QueryTokenType getType() {
		return this.type;
	}

}
