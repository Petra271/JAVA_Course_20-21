package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utils;

/**
 * Represents hexdump command. Expects a single argument: file name. Produces
 * hex-output.
 * 
 * @author Petra
 *
 */
public class HexdumpCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		if (args.length != 1)
			this.getCommandDescription().stream().forEach(env::writeln);

		else {
			Path path = Paths.get(Utils.parsePathString(args[0]));
			if (Files.isDirectory(path)) {
				env.write("Can't produce hex-output for a directory.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

			try {
				InputStream is = Files.newInputStream(path);
				StringBuilder sb = new StringBuilder();
				StringBuilder sb2 = new StringBuilder();
				int i = 0;

				while (is.available() > 0) {
					sb.append(String.format("%08x", i * 16));
					sb.append(": ");
					for (int j = 0; j < 16; j++) {
						if (is.available() > 0) {
							int val = (int) is.read();
							sb.append(String.format("%02x ", val));

							if (val >= 32 & val <=127)
								sb2.append((char) val);
							else
								sb2.append(".");
						} else {
							sb.append("   ");
						}

						if (j == 7 || j == 15)
							sb.append("|");
					}
					env.write(sb.toString());
					env.write(sb2.toString());
					sb.setLength(0);
					sb2.setLength(0);
					env.write("\n");
					i++;
				}
			} catch (IOException e) {
				env.writeln("An error occurred during the hex-output production.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Hexdump command");
		des.add("Expects a single argument: file name.");
		des.add("Produces hex-output.");
		return des;
	}

}
