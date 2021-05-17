package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import javax.swing.JComponent;

/**
 * Represents a {@link BarChart}.
 * 
 * @author Petra
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;
	private BarChart barChart;
	private int offset = 20;
	private int spacingX = 50;
	private int spacingY;
	private int spacingYAfter = 10;
	private int strokeThickness = 2;
	private int numOfBars;
	private int numOfRows;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param barChart
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		this.numOfBars = barChart.getValues().stream().map(v -> v.getX()).max(Integer::compare).get();
	}

	@Override
	public void paintComponent(Graphics graphics) {

		Graphics2D g = (Graphics2D) graphics;
		Dimension dim = getSize();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		FontMetrics fm = g.getFontMetrics();
		numOfRows = (int) Math.ceil((double) (barChart.getMaxY() - barChart.getMinY()) / barChart.getDiff());
		spacingY = 30 + fm.stringWidth(String.valueOf(numOfRows * 2)) + spacingYAfter;

		int chartW = dim.width - 2 * offset - spacingY + 5 * 2;
		int chartH = dim.height - 2 * offset - spacingX;

		int originX = offset + spacingY;
		int originY = dim.height - offset - spacingX;

		int endX = originX + chartW + strokeThickness * numOfBars - offset;
		int endY = (int) Math.ceil((double) (originY - chartH) - strokeThickness * 3);

		drawBars(g, chartW, chartH, originY, originX, endX, endY);
		drawAxes(g, chartH, chartW, originX, originY, endX, endY);

	}

	/**
	 * Draws chart axes.
	 * 
	 * @param g2      graphics object
	 * @param chartH  chart height
	 * @param chartW  chart width
	 * @param originX x value of origin
	 * @param originY y value of origin
	 * @param endX    x value of end point of x axis
	 * @param endY    y value of end point of y axis
	 */
	private void drawAxes(Graphics2D g2, int chartH, int chartW, int originX, int originY, int endX, int endY) {
		Color color = g2.getColor();
		String desX = this.barChart.getDesX();
		String desY = this.barChart.getDesY();
		Stroke stroke = g2.getStroke();
		AffineTransform t = new AffineTransform();
		Polygon polygon = new Polygon();

		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(strokeThickness));
		g2.drawLine(originX - 5, originY, endX, originY);
		g2.drawLine(originX, originY + 5, originX, endY);

		g2.setStroke(new BasicStroke(1));
		double base = 5;
		double triangleHeight = Math.sqrt(3) * base;
		polygon = new Polygon(new int[] { endX, endX, (int) (endX + triangleHeight) },
				new int[] { (int) (originY + base), (int) (originY - base), originY }, 3);
		g2.fill(polygon);
		g2.drawPolygon(polygon);
		polygon = new Polygon(new int[] { (int) (originX - base), (int) (originX + base), originX },
				new int[] { endY, endY, (int) (endY - triangleHeight) }, 3);
		g2.fill(polygon);
		g2.drawPolygon(polygon);

		g2.setColor(color);

		FontMetrics fm = g2.getFontMetrics();

		int stringWidth = fm.stringWidth(desX);
		int xCoordinate = spacingY + chartW / 2 - stringWidth / 2;
		int yCoordinate = originY + spacingX;

		Font prev = g2.getFont();
		Font font = new Font(prev.getName(), Font.PLAIN, prev.getSize() + 3);
		g2.setFont(font);
		g2.drawString(desX, xCoordinate, yCoordinate);

		stringWidth = fm.stringWidth(desY);
		xCoordinate = offset + fm.getAscent() / 2;
		yCoordinate = originY - chartH / 2 + stringWidth / 2;

		t.rotate(Math.toRadians(-90), 0, 0);
		Font rotated = font.deriveFont(t);
		g2.setFont(rotated);
		g2.drawString(desY, xCoordinate, yCoordinate);

		g2.setFont(prev);
		g2.setStroke(stroke);
	}

	/**
	 * Draws chart bars.
	 * 
	 * @param g2      graphics object
	 * @param chartW  chart width
	 * @param chartH  chart height
	 * @param originY y value of origin
	 * @param originX x value of origin
	 * @param endX    x value of end point of x axis
	 * @param endY    y value of end point of x axis
	 */
	public void drawBars(Graphics2D g2, int chartW, int chartH, int originY, int originX, int endX, int endY) {
		Color color = g2.getColor();
		Stroke stroke = g2.getStroke();
		FontMetrics fm = g2.getFontMetrics();

		int barWidth = (int) Math.floor(
				((double) ((double) chartW - (double) strokeThickness - numOfBars * strokeThickness) / numOfBars));

		Font prev = g2.getFont();
		Font font = new Font(prev.getName(), Font.BOLD, prev.getSize());
		g2.setFont(font);
		g2.setColor(Color.LIGHT_GRAY);
		g2.setStroke(new BasicStroke(strokeThickness));
		int yLines;
		String sY;
		for (double i = 0, ct = barChart.getMinY(); i <= numOfRows; i++, ct = ct + barChart.getDiff()) {
			g2.setColor(Color.LIGHT_GRAY);
			yLines = (int) Math.floor((double) originY - i * (double) chartH / numOfRows);
			g2.drawLine(originX - 5, yLines, endX, yLines);

			g2.setColor(color);
			sY = String.valueOf((int) ct);
			g2.drawString(sY, offset + spacingY - fm.stringWidth(sY) - spacingYAfter, yLines + strokeThickness * 3 / 2);
		}

		int stringWidth;
		int x = offset + spacingY;
		int y = 0;
		String sX;
		for (XYValue v : barChart.getValues()) {
			sX = String.valueOf(v.getX());
			stringWidth = fm.stringWidth(String.valueOf(v.getX()));

			y = (int) ((double) originY - (double) chartH / numOfRows * v.getY() / barChart.getDiff()
					- (double) strokeThickness / 2.0);

			g2.setColor(Color.LIGHT_GRAY);
			if (v.getX() != 1) {
				x += (int) Math.ceil((double) barWidth);
				g2.drawLine(x, y, x, endY);
			}

			Rectangle rec = new Rectangle(x, y, barWidth, originY - y);
			g2.setStroke(new BasicStroke(strokeThickness));
			g2.draw(rec);
			g2.setColor(new Color(0, 0, 83));
			g2.fill(rec);
			g2.setColor(color);

			g2.drawString(sX, x + barWidth / 2 - stringWidth / 2, chartH + spacingY / 2 + offset);
		}

		g2.setColor(Color.LIGHT_GRAY);
		g2.drawLine(x + barWidth, y, x + barWidth, endY);
		g2.setColor(color);
		g2.setStroke(stroke);
	}

}
