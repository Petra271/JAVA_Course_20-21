package hr.fer.zemris.java.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;

/**
 * Represents charsets command. Takes zero arguments. Lists names of supported
 * charsets for your Java platform.
 * 
 * @author Petra
 *
 */
public class CharsetsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments.length() != 0)
			this.getCommandDescription().stream().forEach(env::writeln);
		else {
			Map<String, Charset> charsets = Charset.availableCharsets();
			charsets.keySet().stream().forEach(env::writeln);
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charset";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Charsets command");
		des.add("Takes zero arguments.");
		des.add("Lists names of supported charsets for your Java platform.");
		return des;
	}

}
