package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utils;

/**
 * Represents mkdir command. Takes a single argument: file name. Creates a
 * directory.
 * 
 * @author Petra
 *
 */
public class MkdirCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		if (args.length != 1)
			this.getCommandDescription().stream().forEach(env::writeln);

		else {
			try {
				Files.createDirectories(Paths.get(Utils.parsePathString(args[0])));
			} catch (IOException e) {
				env.writeln("An error occurred while creating directory.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		}

		env.writeln("Directory successfully created.");
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Mkdir command");
		des.add("Takes a single argument: file name.");
		des.add("Creates a directory.");
		return des;
	}

}
