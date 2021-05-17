package hr.fer.oprpp1.hw02.prob1;

/**
 * Represents an object thrown when an exception occurs during lexical analysis.
 * 
 * @author Petra
 *
 */
public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1802935421659156465L;

	/**
	 * Constructs a new lexer exception.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructs a new lexer exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new lexer exception with the specified detail message and cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new lexer exception with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

}
