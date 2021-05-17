package hr.fer.zemris.java.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utils;

/**
 * Represents ls command. Takes a single argument – directory. Writes a
 * directory listing.
 * 
 * @author Petra
 *
 */
public class LsCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.split(" ").length != 1)
			this.getCommandDescription().stream().forEach(env::writeln);

		else {
			try {
				List<Path> listing = Files.list(Paths.get(Utils.parsePathString(arguments.strip())))
						.collect(Collectors.toList());

				SimpleDateFormat sdf = new SimpleDateFormat("  yyyy-MM-dd  HH:mm:ss  ");

				for (Path p : listing) {
					env.write(Files.isDirectory(p) ? "d" : "-");
					env.write(Files.isReadable(p) ? "r" : "-");
					env.write(Files.isWritable(p) ? "w" : "-");
					env.write(Files.isExecutable(p) ? "e" : "-");
					env.write(String.format("%10d", new File(p.toString()).length()));

					BasicFileAttributeView faView = Files.getFileAttributeView(p, BasicFileAttributeView.class,
							LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
					env.write(formattedDateTime);

					env.write(p.getFileName().toString() + "\n");
				}
			} catch (IOException e) {
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
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("ls command");
		des.add("Takes a single argument – directory.");
		des.add("Writes a directory listing.");
		return des;
	}

}
