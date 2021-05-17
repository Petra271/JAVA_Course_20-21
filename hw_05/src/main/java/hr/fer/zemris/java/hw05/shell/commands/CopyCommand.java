package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utils;

/**
 * Represents copy command. Expects two arguments: source file name and
 * destination file name. Works only with files, not directories. If the second
 * argument is a directory, the given file will be copited into that directory.
 * 
 * @author Petra
 *
 */
public class CopyCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] args = arguments.split(" ");
		if (args.length != 2)
			this.getCommandDescription().stream().forEach(env::writeln);

		else {
			Path source = Paths.get(Utils.parsePathString(args[0]));
			Path dest = Paths.get(Utils.parsePathString(args[1]));

			if (Files.isDirectory(dest)) {
				dest = Paths.get(args[1] + "/" + args[0]);
			}

			if (Files.exists(dest)) {
				env.writeln("Do you want to overwrite the destination file? (Y/N)");
				env.write(env.getPromptSymbol() + " ");
				String res = env.readLine();
				if (!(res.equals("Y")) && !(res.equals("y"))) {
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
				}
			}

			try {
				InputStream is = Files.newInputStream(source);
				OutputStream os = Files.newOutputStream(dest, StandardOpenOption.CREATE);
				byte[] buff = new byte[4096];

				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					os.write(buff, 0, r);
				}

			} catch (IOException e) {
				env.writeln("An error occurred while copying.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

		}
		env.writeln("File successfully copied.");
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Copy command");
		des.add("Expects two arguments: source file name and destination file name.");
		des.add("Works only with files, not directories.");
		des.add("If the second argument is a directory, the given file will be copited into that directory.");
		return des;
	}

}
