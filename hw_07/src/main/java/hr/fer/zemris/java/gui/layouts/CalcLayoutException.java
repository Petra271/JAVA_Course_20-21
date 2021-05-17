package hr.fer.zemris.java.gui.layouts;

/**
 * Calculator layout exception.
 * 
 * @author Petra
 *
 */
public class CalcLayoutException extends RuntimeException {

	@java.io.Serial
	private static final long serialVersionUID = -7571988897781474581L;

	/**
	 * Constructs a new calculator layout exception.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructs a new calculator layout exception with the specified detail
	 * message.
	 * 
	 * @param message the detail message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

	/**
	 * Constructs a new calculator layout exception with the specified detail
	 * message and cause.
	 *
	 * @param message the detail message
	 * @param cause   the cause of the exception
	 */
	public CalcLayoutException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new calculator layout exception with the specified cause
	 *
	 * @param cause the cause of the exception
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}

}
