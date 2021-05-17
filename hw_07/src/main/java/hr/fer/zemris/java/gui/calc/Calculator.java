package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * The class represents a simple calculator.
 * 
 * @author Petra
 *
 */
public class Calculator extends JFrame {

	private final Color backgroundColor = Color.LIGHT_GRAY;
	private final Color digitButtonsColor = Color.WHITE;
	private final Color operatorbuttonsColor1 = new Color(255, 127, 127);
	private final Color operatorbuttonsColor2 = new Color(146, 223, 243);
	private static CalcModel calcModel = new CalcModelImpl();
	private Stack<Double> calcStack = new Stack<>();
	private List<InverseOperationButton> inverseOperations = new ArrayList<>();

	private static final long serialVersionUID = 1L;

	/**
	 * Creates an instance of this class.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("My Calculator");
		setLocation(50, 50);
		setSize(700, 350);
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 12));
		setContentPane(panel);
		initGUI();
	}

	/**
	 * Used for initializing calculator GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(10));
		cp.setBackground(backgroundColor);

		JLabel screen = new JLabel();
		screen.setBackground(digitButtonsColor);
		screen.setHorizontalAlignment(SwingConstants.RIGHT);
		screen.setOpaque(true);
		screen.setFont(new Font("Dialog", Font.BOLD, 25));
		calcModel.addCalcValueListener(m -> screen.setText(m.toString()));
		cp.add(screen, "1,1");

		this.addNumberButtons(cp);

		cp.add(new UnaryOperatorButton("+/-", e -> calcModel.swapSign(), operatorbuttonsColor2), "5,4");
		cp.add(new UnaryOperatorButton(".", e -> calcModel.insertDecimalPoint(), operatorbuttonsColor2), "5,5");
		cp.add(new BinaryOperatorButton("-", calcModel, (x, y) -> x - y, operatorbuttonsColor2), "4,6");
		cp.add(new BinaryOperatorButton("+", calcModel, (x, y) -> x + y, operatorbuttonsColor2), "5,6");
		cp.add(new BinaryOperatorButton("*", calcModel, (x, y) -> x * y, operatorbuttonsColor2), "3,6");
		cp.add(new BinaryOperatorButton("/", calcModel, (x, y) -> x / y, operatorbuttonsColor2), "2,6");

		cp.add(new UnaryOperatorButton("=", e -> {
			if (calcModel.isActiveOperandSet() && calcModel.getPendingBinaryOperation() != null) {
				calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
						calcModel.getValue()));
				calcModel.setPendingBinaryOperation(null);
			}
		}, operatorbuttonsColor2), "1,6");

		cp.add(new UnaryOperatorButton("clr", e -> calcModel.clear(), operatorbuttonsColor1), "1,7");
		cp.add(new UnaryOperatorButton("reset", e -> calcModel.clearAll(), operatorbuttonsColor1), "2,7");

		cp.add(new UnaryOperatorButton("1/x", e -> calcModel.setValue(1.0 / (calcModel.getValue())),
				operatorbuttonsColor2), "2,1");

		cp.add(new UnaryOperatorButton("push", e -> {
			calcStack.push(calcModel.getValue());
			calcModel.clear();
		}, operatorbuttonsColor2), "3,7");
		
		cp.add(new UnaryOperatorButton("pop", e -> {
			if (calcStack.isEmpty()) {
				calcModel.emptyStack("Stack is empty");
			} else {
				calcModel.setValue(calcStack.pop());
			}
		}, operatorbuttonsColor2), "4,7");

		InverseOperationButton log = new InverseOperationButton("log", "10^x",
				e -> calcModel.setValue(Math.log10(calcModel.getValue())),
				e -> calcModel.setValue(Math.pow(10, calcModel.getValue())), operatorbuttonsColor2);
		inverseOperations.add(log);
		cp.add(log, "3,1");

		InverseOperationButton ln = new InverseOperationButton("ln", "e^x",
				e -> calcModel.setValue(Math.log(calcModel.getValue())),
				e -> calcModel.setValue(Math.pow(Math.E, calcModel.getValue())), operatorbuttonsColor2);
		inverseOperations.add(ln);
		cp.add(ln, "4,1");

		InverseOperationButton pow = new InverseOperationButton("x^n", "x^(1/n)", e -> {
			operationState();
			calcModel.setPendingBinaryOperation(Math::pow);
			calcModel.clear();
		}, e -> {
			operationState();
			calcModel.setPendingBinaryOperation((x, y) -> Math.pow(x, 1 / y));
			calcModel.clear();
		}, operatorbuttonsColor2);
		inverseOperations.add(pow);
		cp.add(pow, "5,1");

		InverseOperationButton sin = new InverseOperationButton("sin", "arcsin",
				e -> calcModel.setValue(Math.sin(calcModel.getValue())),
				e -> calcModel.setValue(Math.asin(calcModel.getValue())), operatorbuttonsColor2);
		inverseOperations.add(sin);
		cp.add(sin, "2,2");

		InverseOperationButton cos = new InverseOperationButton("cos", "arccos",
				e -> calcModel.setValue(Math.cos(calcModel.getValue())),
				e -> calcModel.setValue(Math.acos(calcModel.getValue())), operatorbuttonsColor2);
		inverseOperations.add(cos);
		cp.add(cos, "3,2");

		InverseOperationButton tan = new InverseOperationButton("tan", "arctan",
				e -> calcModel.setValue(Math.tan(calcModel.getValue())),
				e -> calcModel.setValue(Math.atan(calcModel.getValue())), operatorbuttonsColor2);
		inverseOperations.add(tan);
		cp.add(tan, "4,2");

		InverseOperationButton ctg = new InverseOperationButton("ctg", "arcctg",
				e -> calcModel.setValue(1.0 / Math.tan(calcModel.getValue())),
				e -> calcModel.setValue(Math.PI / 2 - Math.atan(calcModel.getValue())), operatorbuttonsColor2);
		inverseOperations.add(ctg);
		cp.add(ctg, "5,2");

		JCheckBox inverse = new JCheckBox("Inv");
		inverse.addItemListener(e -> {
			for (InverseOperationButton operator : inverseOperations) {
				operator.inverseOperation(inverse.isSelected());
			}
		});
		inverse.setBackground(operatorbuttonsColor1);
		inverse.setFont(new Font("Dialog", Font.BOLD, 20));
		inverse.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(inverse, "5,7");

	}

	/**
	 * Adds number buttons.
	 * 
	 * @param p container in which the buttons will be added
	 */
	private void addNumberButtons(Container p) {
		int counter = 7;
		int i = 0;
		int j = 0;
		for (i = 2; i < 5; i++) {
			for (j = 3; j < 6; j++) {
				DigitButton b = new DigitButton(counter++, calcModel, digitButtonsColor);
				p.add(b, new RCPosition(i, j));
			}
			counter -= 6;
		}
		p.add(new DigitButton(0, calcModel, digitButtonsColor), new RCPosition(i, j - 3));
	}

	/**
	 * Checks binary operation state.
	 */
	private static void operationState() {
		if (calcModel.getPendingBinaryOperation() == null)
			calcModel.setActiveOperand(calcModel.getValue());
		else if (calcModel.isActiveOperandSet()) {
			calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
					calcModel.getValue()));
		}

		calcModel.setActiveOperand(calcModel.getValue());
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {
			JFrame calc = new Calculator();
			calc.setVisible(true);
		});
	}

}
