package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Represents exit command. Takes zero arguments. Exits the shell.
 * 
 * @author Petra
 *
 */
public class ExitCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.length() != 0) {
			this.getCommandDescription().stream().forEach(env::writeln);
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		env.write("Goodbye!");
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Exit command");
		des.add("Takes zero arguments.");
		des.add("Exits the shell.");
		return des;
	}

}
