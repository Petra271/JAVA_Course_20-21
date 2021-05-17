package hr.fer.zemris.java.hw05.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.MyShell;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Represents a symbol command. Prints symbol of {@link MyShell} if used only
 * with symbol name. Sets symbol of given name to given character if used with
 * two arguments.
 * 
 * @author Petra
 *
 */
public class SymbolCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.strip().split(" ");
		if (args.length != 2 && args.length != 1) {
			env.writeln("Invalid input.");

		} else {

			if (args.length == 1) {
				Character c;
				switch (args[0].strip()) {
				case "PROMPT" -> c = env.getPromptSymbol();
				case "MULTILINE" -> c = env.getMultilineSymbol();
				case "MORELINES" -> c = env.getMorelinesSymbol();
				default -> {
					env.writeln("Invalid input.");
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
				}
				}

				env.writeln("Symbol for " + args[0] + " is '" + c + "'");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

			if (args.length == 2 && args[1].length() == 1) {
				Character c = args[1].charAt(0);
				Character s;
				switch (args[0].strip()) {
				case "PROMPT" -> {
					s = env.getPromptSymbol();
					env.setPromptSymbol(c);
				}
				case "MULTILINE" -> {
					s = env.getMultilineSymbol();
					env.setMultilineSymbol(c);
				}
				case "MORELINES" -> {
					s = env.getMorelinesSymbol();
					env.setMorelinesSymbol(c);
				}
				default -> {
					env.writeln("Invalid input.");
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
				}
				}

				env.writeln("Symbol for " + args[0] + " changed from '" + s + "' to '" + c + "'");

			}
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Symbol command.");
		des.add("Prints symbol of MyShell if used only with symbol name.");
		des.add("Sets symbol of given name to given character if used with two arguments.");
		return des;
	}

}
