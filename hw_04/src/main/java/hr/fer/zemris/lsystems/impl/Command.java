package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Represents a command which a turtle is able to execute.
 * 
 * @author Petra
 *
 */
public interface Command {

	/**
	 * An action which a turtle can execute.
	 * 
	 * @param ctx     turtle's state context
	 * @param painter an object used for drawing
	 */
	void execute(Context ctx, Painter painter);

}
