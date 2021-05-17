package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Represents a command used for adding a state to the stack.
 * 
 * @author Petra
 *
 */
public class PushCommand implements Command {

	/**
	 * Copies a state from the top of the stack and adds the new state to the stack.
	 */
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.pushState(ctx.getCurrentState().copy());
	}

}
