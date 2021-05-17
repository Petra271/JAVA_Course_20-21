package hr.fer.zemris.java.hw05.shell;

import java.util.List;

/**
 * Represents a {@link MyShell} command.
 * 
 * @author Petra
 *
 */
public interface ShellCommand {

	/**
	 * This method is used for executing the command.
	 *
	 * @param env       environment
	 * @param arguments command arguments
	 * @return shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Returns command name.
	 * 
	 * @return command name
	 */
	String getCommandName();

	/**
	 * Returns command description.
	 * 
	 * @return command description
	 */
	List<String> getCommandDescription();

}
