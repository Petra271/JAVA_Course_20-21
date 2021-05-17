package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.event.ActionListener;

/**
 * Represents a button which has two listeners for two different operation.
 * 
 * @author Petra
 *
 */
public class InverseOperationButton extends OperatorButton {

	private static final long serialVersionUID = 1L;
	private ActionListener actionListener;
	private ActionListener inverseActionListener;
	private String text;
	private String inverseText;

	public InverseOperationButton(String text, String inverseText, ActionListener listener, ActionListener inverse,
			Color color) {
		super(text, color);

		this.actionListener = listener;
		this.inverseActionListener = inverse;
		this.text = text;
		this.inverseText = inverseText;

		this.addActionListener(listener);
	}

	/**
	 * Inverses operations.
	 * 
	 * @param inverse inverse operation
	 */
	public void inverseOperation(boolean inverse) {

		if (inverse) {
			removeActionListener(this.actionListener);
			addActionListener(this.inverseActionListener);
			this.setText(inverseText);
		} else {
			removeActionListener(this.inverseActionListener);
			addActionListener(this.actionListener);
			this.setText(this.text);
		}

	}

}
