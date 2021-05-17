package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

/**
 * Represents a operator button.
 * 
 * @author Petra
 *
 */
public class OperatorButton extends JButton {

	private static final long serialVersionUID = 1L;

	public OperatorButton(String text, Color color) {
		this.setText(text);
		this.setBackground(color);
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		this.setFont(this.getFont().deriveFont(20f));
	}

}
