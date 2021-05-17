package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

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
public class ColorCommand implements Command {

	private Color colour;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param color new colour used for drawing
	 */
	public ColorCommand(Color colour) {
		this.colour = colour;
	}

	/**
	 * Sets the drawing colour.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setColor(this.colour);
	}

}
