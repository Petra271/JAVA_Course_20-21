package hr.fer.oprpp1.hw04.db;

/**
 * Represents an object thrown when an exception occurs during lexical analysis.
 * 
 * @author Petra
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 7192769174408290284L;

	/**
	 * Constructs a new query parser exception.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Constructs a new query parser exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a new query parser exception with the specified detail message and cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new query parser exception with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}
}