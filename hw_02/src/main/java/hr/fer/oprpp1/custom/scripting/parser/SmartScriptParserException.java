package hr.fer.oprpp1.custom.scripting.parser;

/**
 * Represents an object thrown when an exception occurs during lexical analysis.
 * 
 * @author Petra
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 7192769174408290284L;

	/**
	 * Constructs a new parser exception.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructs a new parser exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a new parser exception with the specified detail message and cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new parser exception with the specified cause.
	 *
	 * @param cause the cause of the exception
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
}