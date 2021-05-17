package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Implementation of {@link CalcModel}. Specifies a model of a simple
 * calculator.
 * 
 * @author Petra
 *
 */
public class CalcModelImpl implements CalcModel {

	private boolean editable = true;
	private boolean positive = true;
	private String digitsString = "";
	private double digits = 0.0;
	private String frozen = null;
	private List<CalcValueListener> listeners = new ArrayList<>();
	private double activeOperand = 0.0;
	private DoubleBinaryOperator pendingOperation;
	private boolean activeOperandSet;

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public String toString() {
		if (frozen != null)
			return frozen;

		String res = "";
		if (digitsString != null)
			res = digitsString.replaceFirst("^0+(?!\\.)", "");
		return res.isEmpty() ? (positive ? "0" : "-0") : (positive ? res : "-" + res);
	}

	@Override
	public double getValue() {
		if (!positive)
			return this.digits * -1.0;
		return this.digits;
	}

	@Override
	public void setValue(double value) {
		if (Double.compare(value, 0.0) < 0 && value != Double.NEGATIVE_INFINITY) {
			this.digits = Math.abs(value);
			this.positive = false;
		} else {
			this.digits = value;
			this.positive = true;
		}
		this.digitsString = String.valueOf(this.digits);
		this.editable = false;
		this.notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		this.digitsString = "";
		this.editable = true;
		this.positive = true;
		this.notifyListeners();
	}

	@Override
	public void clearAll() {
		this.clear();
		this.activeOperand = 0.0;
		this.activeOperandSet = false;
		this.pendingOperation = null;
		this.positive = true;
		this.digits = 0.0;
		this.notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model is not editable.");

		if (digitsString != null) {
			if (positive)
				this.positive = false;
			else
				this.positive = true;

			this.notifyListeners();
		}
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException("Model is not editable");

		if (digitsString.isEmpty())
			throw new CalculatorInputException("Number is empty.");

		if (digitsString.contains("."))
			throw new CalculatorInputException("The number already contains a decimal point.");
		else {
			digitsString += ".";
			this.notifyListeners();
		}

	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {

		if (digit < 0 || digit > 9)
			throw new IllegalArgumentException("Digit must be greater than 0 and equal to 9 at most.");

		if (!editable)
			throw new CalculatorInputException("Model is not editable.");

		try {
			digits = Double.parseDouble(digitsString + digit);
			if (Double.isInfinite(digits))
				throw new CalculatorInputException("Number is to large.");
		} catch (CalculatorInputException e) {
			throw new CalculatorInputException("Input can not be parsed to double.");
		}
		digitsString += digit;
		this.notifyListeners();

	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperandSet;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!activeOperandSet)
			throw new IllegalStateException("Active operator is not set");
		return this.activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
		this.activeOperandSet = true;
	}

	@Override
	public void clearActiveOperand() {
		this.activeOperand = 0.0;
		this.activeOperandSet = false;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return this.pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
		this.notifyListeners();
	}

	@Override
	public void freezeValue(String value) {
		this.frozen = value;
	}

	@Override
	public boolean hasFrozenValue() {
		if (frozen != null)
			return false;
		return true;
	}

	@Override
	public void emptyStack(String text) {
		this.digitsString = text;
		notifyListeners();
	}

	/**
	 * Notifies all listeners that a change has happened.
	 */
	private void notifyListeners() {
		for (CalcValueListener l : listeners)
			l.valueChanged(this);
	}

}
