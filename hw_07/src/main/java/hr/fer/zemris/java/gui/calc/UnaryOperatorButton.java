package hr.fer.zemris.java.gui.calc;

/**
 * Represents a unary operator button.
 */
import java.awt.Color;
import java.awt.event.ActionListener;

public class UnaryOperatorButton extends OperatorButton {

	private static final long serialVersionUID = 1L;

	public UnaryOperatorButton(String text, ActionListener listener, Color color) {
		super(text, color);
		this.addActionListener(listener);
	}

}
