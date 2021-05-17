package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Represents a bar chart.
 * 
 * @author Petra
 *
 */
public class BarChart {

	private List<XYValue> values;
	private String desX;
	private String desY;
	private int minY;
	private int maxY;
	private int diff;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param values {@link XYValue} chart values
	 * @param desX   x axis description
	 * @param desY   y axis description
	 * @param minY   minimum y value
	 * @param maxY   maximum y value
	 * @param diff   difference between y values
	 */
	public BarChart(List<XYValue> values, String desX, String desY, int minY, int maxY, int diff) {
		if (minY < 0)
			throw new IllegalArgumentException("Minimum y must be positive.");

		if (maxY <= minY)
			throw new IllegalArgumentException("Maximum y must be greater that minimum y.");

		for (XYValue v : values)
			if (v.getY() < minY)
				throw new IllegalArgumentException("Values must be greater or equal to minimum y.");

		this.values = values;
		this.desX = desX;
		this.desY = desY;
		this.minY = minY;
		this.maxY = maxY;
		this.diff = diff;
	}

	/**
	 * Returns a list of {@link XYValue} chart values.
	 * 
	 * @return a list of {@link XYValue} values
	 */
	public List<XYValue> getValues() {
		return this.values;
	}

	/**
	 * Returns x axis description.
	 * 
	 * @return x axis description
	 */
	public String getDesX() {
		return this.desX;
	}

	/**
	 * Returns y axis description.
	 * 
	 * @return y axis description
	 */
	public String getDesY() {
		return this.desY;
	}

	/**
	 * Returns minimum y value.
	 * 
	 * @return minimum y value
	 */
	public int getMinY() {
		return this.minY;
	}

	/**
	 * Returns maximum y value.
	 * 
	 * @return maximum y value
	 */
	public int getMaxY() {
		return this.maxY;
	}

	/**
	 * Returns difference between y values.
	 * 
	 * @return difference between y values
	 */
	public int getDiff() {
		return this.diff;
	}

}
