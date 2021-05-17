package hr.fer.zemris.java.gui.charts;

/**
 * Represents a point on {@link BarChart} graph.
 * 
 * @author Petra
 *
 */
public class XYValue {

	private int x;
	private int y;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param x x value
	 * @param y y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns x value.
	 * 
	 * @return x value
	 */
	public int getX() {
		return x;
	}

	/**
	 * Returns y value.
	 * 
	 * @return y value
	 */
	public int getY() {
		return y;
	}

}
