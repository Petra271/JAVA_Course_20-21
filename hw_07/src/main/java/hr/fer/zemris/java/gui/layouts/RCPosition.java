package hr.fer.zemris.java.gui.layouts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Used as a constraint in {@link CalcLayout}.
 * 
 * @author Petra
 *
 */
public class RCPosition {

	private int row;
	private int column;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param row    calculator layout row
	 * @param column calculator layout column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Row getter.
	 * 
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Column getter.
	 * 
	 * @return column
	 */
	public int getColumn() {
		return this.column;
	}

	/**
	 * Parsed string input into an object of this class.
	 * 
	 * @param text string input
	 * @return a new RCPosition
	 */
	public static RCPosition parse(String text) {
		Pattern pattern = Pattern.compile("[1-9]+[' ']*,[' ']*[1-9]+");
		Matcher matcher = pattern.matcher(text.strip());
		if (!matcher.matches())
			throw new IllegalArgumentException("Input is invalid.");

		String[] parts = text.split(",");

		int x = 0;
		int y = 0;
		try {
			x = Integer.parseInt(parts[0].strip());
			y = Integer.parseInt(parts[1].strip());
		} catch (NumberFormatException e) {
			System.err.println("Input can not be parsed to an integer.");
			System.exit(1);
		}

		return new RCPosition(x, y);
	}

	public static void main(String[] args) {
		RCPosition rc = parse("   3    ,   4   ");
		System.out.println(rc.getRow());
		System.out.println(rc.getColumn());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

}
