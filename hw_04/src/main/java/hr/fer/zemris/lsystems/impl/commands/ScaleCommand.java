package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents a command used for scaling the effective shift value.
 * 
 * @author Petra
 *
 */
public class ScaleCommand implements Command {

	private double factor;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param factor factor by which the effective shift value is scaled.
	 */
	public ScaleCommand(double factor) {
		this.factor = factor;
	}

	/**
	 * Sets the effective shift value.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		double length = state.getEffectiveShiftValue() * this.factor;
		state.setEffectiveShiftValue(length);
	}

}
