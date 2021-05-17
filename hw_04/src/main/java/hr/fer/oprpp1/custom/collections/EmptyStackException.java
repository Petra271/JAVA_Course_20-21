package hr.fer.oprpp1.custom.collections;

/**
 * Exception which is thrown when trying to access an element from an empty
 * stack.
 * 
 * @author Petra
 *
 */
public class EmptyStackException extends RuntimeException {

	@java.io.Serial
	private static final long serialVersionUID = 735807516317944609L;

	/**
	 * Constructs a new empty stack exception.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructs a new empty stack exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * Constructs a new empty stack exception with the specified detail message and
	 * cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new empty stack exception with the specified cause
	 *
	 * @param cause the cause of the exception
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

}
