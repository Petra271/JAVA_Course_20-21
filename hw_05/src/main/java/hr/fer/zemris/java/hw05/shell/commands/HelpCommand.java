package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Represets help command. If started with no arguments, it lists names of all
 * supported commands. If started with single argument, it prints the name and
 * the description of the selected command.
 * 
 * @author Petra
 *
 */
public class HelpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Map<String, ShellCommand> cmds = env.commands();

		if (arguments.length() == 0)
			cmds.keySet().stream().forEach(env::writeln);
		else {
			ShellCommand cmd = cmds.get(arguments);

			if (cmd == null)
				this.getCommandDescription().stream().forEach(env::writeln);
			else
				cmd.getCommandDescription().stream().forEach(env::writeln);
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Help command");
		des.add("If started with no arguments, it lists names of all supported commands.");
		des.add("If started with single argument, it prints the name and the description of the selected command.");

		return des;
	}

}
