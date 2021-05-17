package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Represents layout of a simple calculator.
 * 
 * @author Petra
 *
 */
public class CalcLayout implements LayoutManager2 {

	private final int maxRow = 5;
	private final int maxCol = 7;
	private final int minRow = 1;
	private final int minCol = 1;

	private int gap;
	private Map<RCPosition, Component> components = new HashMap<>();

	/**
	 * Creates an instance of this class using the given gap.
	 * 
	 * @param gap
	 */
	public CalcLayout(int gap) {
		this.gap = gap;
	}

	/**
	 * Creates an instance of this class.
	 */
	public CalcLayout() {
		this(0);
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		components.values().remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		Function<Component, Dimension> preferredFun = c -> c.getPreferredSize();
		return layoutSize(parent, preferredFun);
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		Function<Component, Dimension> minFun = c -> c.getMinimumSize();
		return layoutSize(parent, minFun);
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		Function<Component, Dimension> maxFun = c -> c.getMaximumSize();
		return layoutSize(target, maxFun);
	}

	/**
	 * Calculates calculator dimension based on type (maximum, minimum or
	 * preferred).
	 * 
	 * @param parent parent container
	 * @param fun    contains information about dimension type
	 * @return calculated dimension
	 */
	private Dimension layoutSize(Container parent, Function<Component, Dimension> fun) {
		Insets insets = parent.getInsets();

		double w = 0;
		double h = 0;

		for (Map.Entry<RCPosition, Component> c : components.entrySet()) {
			Dimension componentDimension = fun.apply(c.getValue());
			double cHeight = componentDimension.height;
			double cWidth = componentDimension.width;

			if (c.getKey().getColumn() == 1 && c.getKey().getRow() == 1) {
				cWidth -= 4 * gap;
				cWidth /= 5;
			}

			if (w < cWidth)
				w = cWidth;
			if (h < cHeight)
				h = cHeight;
		}

		int width = (int) Math.round(w);
		int height = (int) Math.round(h);
		return new Dimension(insets.left + insets.right + maxCol * width + (maxCol - 1) * gap,
				insets.top + insets.bottom + maxRow * height + (maxRow - 1) * gap);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets i = parent.getInsets();
		int ncomponents = components.size();

		if (ncomponents == 0)
			return;

		double width = components.values().stream().map(c -> c.getMaximumSize().width).max(Integer::compareTo).get();
		double height = components.values().stream().map(c -> c.getMaximumSize().height).max(Integer::compareTo).get();

		width = ((double) parent.getWidth() - i.left - i.right - (maxCol - 1) * gap) / maxCol;
		height = ((double) parent.getHeight() - i.top - i.bottom - (maxRow - 1) * gap) / maxRow;
		boolean flag = false;
		boolean flagH = false;

		double h = 0;
		double w = 0;
		int x = i.left;
		int y = i.top;
		for (Map.Entry<RCPosition, Component> c : components.entrySet()) {
			RCPosition pos = c.getKey();

			if (pos.getColumn() == 1 && pos.getRow() == 1) {
				if (!flag) {
					h = (int) Math.ceil(height);
					w = (int) Math.ceil(5 * width + 4 * gap);
				} else {
					h = (int) Math.floor(height);
					w = (int) Math.floor(5 * width + 4 * gap);
				}
				c.getValue().setBounds(x, y, (int) w, (int) h);
				continue;
			}

			if (!flagH)
				h = (int) Math.ceil(height);
			else
				h = (int) Math.floor(height);

			if (!flag) {
				w = (int) Math.ceil(width);
				flag = true;
			} else {
				w = (int) Math.floor(width);
				flag = false;
			}

			c.getValue().setBounds((int) ((pos.getColumn() - 1) * (width + gap) + x),
					(int) ((pos.getRow() - 1) * (height + gap)) + y, (int) w, (int) h);

			if (pos.getColumn() == maxCol)
				flagH = true;
			else if (pos.getColumn() == minCol)
				flagH = false;
		}

	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp, "Component can not be null.");
		Objects.requireNonNull(constraints, "Constraints can not be null.");

		if (!(constraints instanceof RCPosition || constraints instanceof String))
			throw new IllegalArgumentException("Constraint must be RCPosition object or a string."); // exception!!

		RCPosition cons;
		if (constraints instanceof RCPosition)
			cons = (RCPosition) constraints;
		else
			cons = RCPosition.parse((String) constraints);

		checkPosition(cons.getRow(), cons.getColumn());
		if (components.get(cons) != null)
			throw new CalcLayoutException("There is already a component in the given position.");
		components.put(cons, comp);

	}

	/**
	 * Checks if the given position is valid.
	 * 
	 * @param row    layout row
	 * @param column layout column
	 */
	private void checkPosition(int row, int column) {
		List<Integer> invalidColumns = IntStream.rangeClosed(2, 5).boxed().collect(Collectors.toList());
		if (row == minRow && invalidColumns.contains(column))
			throw new CalcLayoutException("Position (" + row + ", " + column + ") is unavailable.");
		else if (row < minRow || row > maxRow || column < minCol || row > maxRow)
			throw new CalcLayoutException(
					"Maximum number of rows is " + maxRow + ". Maximum level of columns is " + maxCol + ".");
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Sets gap to the given value.
	 * 
	 * @param gap gap
	 */
	public void setGap(int gap) {
		this.gap = gap;
	}

}
