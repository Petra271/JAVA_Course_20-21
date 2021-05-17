package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Represents an object thrown when an exception occurs during lexical analysis.
 * 
 * @author Petra
 *
 */
public class SmartScriptLexerException extends RuntimeException {
	
	private static final long serialVersionUID = 2631336637335401221L;

	/**
	 * Constructs a new lexer exception.
	 */
	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Constructs a new lexer exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a new lexer exception with the specified detail message and cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new lexer exception with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}
}