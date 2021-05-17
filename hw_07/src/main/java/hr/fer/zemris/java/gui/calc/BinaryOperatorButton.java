package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.util.function.DoubleBinaryOperator;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Represents a binary operation button.
 * 
 * @author Petra
 *
 */
public class BinaryOperatorButton extends OperatorButton {

	private static final long serialVersionUID = 1L;

	public BinaryOperatorButton(String operator, CalcModel calcModel, DoubleBinaryOperator op, Color color) {
		super(operator, color);

		this.addActionListener(e -> {
			if (calcModel.getPendingBinaryOperation() == null)
				calcModel.setActiveOperand(calcModel.getValue());
			else if (calcModel.isActiveOperandSet()) {
				calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
						calcModel.getValue()));
			}
			calcModel.setActiveOperand(calcModel.getValue());
			calcModel.setPendingBinaryOperation(op);
			calcModel.clear();
		});
	}

}
