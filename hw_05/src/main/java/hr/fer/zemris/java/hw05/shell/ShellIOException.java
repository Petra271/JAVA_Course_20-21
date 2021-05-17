package hr.fer.zemris.java.hw05.shell;

/**
 * Represents {@linkplain MyShell} IO exception.
 * 
 * @author Petra
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = -4546821855818939876L;

	/**
	 * Constructs a new shell IO exception.
	 */
	public ShellIOException() {
		super();
	}

	/**
	 * Constructs a new shell IO exception with the specified detail message.
	 * 
	 * @param message the detail message
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Constructs a new shell IO exception with the specified detail message and
	 * cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new shell IO exception with the specified cause
	 *
	 * @param cause the cause of the exception
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

}
