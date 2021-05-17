package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.oprpp1.math.Vector2D;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents a command used for skipping.
 * 
 * @author Petra
 *
 */
public class SkipCommand implements Command{
	
	private double step;
	
	
	/**
	 * Creates an instance of this class.
	 * 
	 * @param step value used for calculating the shift length
	 */
	public SkipCommand(double step) {
		this.step = step;
	}

	/**
	 * Draws a line and sets the turtle's position.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		Vector2D currentPos = state.getPosition();
		double length = state.getEffectiveShiftValue() * this.step;
		Vector2D newPos = currentPos.added(state.getDirection().scaled(length));
	    state.setPosition(newPos);

	}

}
