package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Represents a digit button.
 * 
 * @author Petra
 *
 */
public class DigitButton extends JButton {

	private static final long serialVersionUID = 1L;
	private String text;

	public DigitButton(int num, CalcModel calcModel, Color color) {
		this.text = String.valueOf(num);
		this.setFont(this.getFont().deriveFont(30f));
		this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		this.setText(text);
		this.setBackground(color);
		this.addActionListener(e -> calcModel.insertDigit(num));
	}

}
