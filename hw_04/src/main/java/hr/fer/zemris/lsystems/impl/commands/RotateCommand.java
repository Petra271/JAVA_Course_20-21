package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Represents a command used for rotating the turtle's direction vector.
 * 
 * @author Petra
 *
 */
public class RotateCommand implements Command {

	private double angle;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param angle the angle by which the turtle's direction vector will be rotated
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	/**
	 * Rotates the turtle's direction vector.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
        currentState.setDirection(currentState.getDirection().rotated(Math.toRadians(angle)));
   
	}

}
