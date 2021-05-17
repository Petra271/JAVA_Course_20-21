package hr.fer.oprpp1.hw04.db;

/**
 * Represents an object thrown when an exception occurs during lexical analysis.
 * 
 * @author Petra
 *
 */
public class QueryLexerException extends RuntimeException {

	private static final long serialVersionUID = 1802935421659156465L;

	/**
	 * Constructs a new query lexer exception.
	 */
	public QueryLexerException() {
		super();
	}

	/**
	 * Constructs a new query lexer exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public QueryLexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new query lexer exception with the specified detail message and
	 * cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public QueryLexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new query lexer exception with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public QueryLexerException(Throwable cause) {
		super(cause);
	}

}
