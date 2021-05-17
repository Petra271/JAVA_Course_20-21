package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utils;

/**
 * Represents a cat command. Takes one or two arguments. The first argument is
 * path to some file and it is mandatory. The second argument is charset name
 * that should be used to interpret chars from bytes. The command opens the
 * given file and writes its content to the console.
 * 
 * @author Petra
 *
 */
public class CatCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		String path = Utils.parsePathString(args[0]);

		if (args.length != 2 && args.length != 1)
			this.getCommandDescription().stream().forEach(env::writeln);

		else {
			Charset cs = Charset.defaultCharset();
			try {
				if (args.length == 2)
					cs = Charset.forName(args[1]);
				Files.lines(Paths.get(path), cs).forEach(env::writeln);
			} catch (IOException | IllegalCharsetNameException | UnsupportedCharsetException e) {
				env.writeln("An error has occurred.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Cat command");
		des.add("Takes one or two arguments.");
		des.add("The first argument is path to some file and it is mandatory.");
		des.add("The second argument is charset name that should be used to interpret chars from bytes.");
		des.add("The command opens the given file and writes its content to the console.");
		return des;
	}

}
