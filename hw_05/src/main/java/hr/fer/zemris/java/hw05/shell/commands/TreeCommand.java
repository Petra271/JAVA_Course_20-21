package hr.fer.zemris.java.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.java.hw05.shell.Environment;
import hr.fer.zemris.java.hw05.shell.ShellCommand;
import hr.fer.zemris.java.hw05.shell.ShellStatus;
import hr.fer.zemris.java.hw05.shell.Utils;

/**
 * Represents tree command. Expects a single argument: directory name. Prints a
 * tree.
 * 
 * @author Petra
 *
 */
public class TreeCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.split(" ").length != 1)
			this.getCommandDescription().stream().forEach(env::writeln);

		else {
			Path path = Paths.get(Utils.parsePathString(arguments));
			if (!Files.isDirectory(path)) {
				env.writeln("Not a directory.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

			try {
				Files.walkFileTree(path, new FileVisitor<>() {
					int level = 1;

					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						env.writeln(String.format("%" + level + "s%s", "", dir.getFileName()));
						level += 2;
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						env.writeln(String.format("%" + level + "s%s", "", file.getFileName()));
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						level -= 2;
						return FileVisitResult.CONTINUE;
					}
				});
			} catch (IOException e) {
				env.writeln("An error occurred.");
			}

		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> des = new ArrayList<>();
		des.add("Tree command");
		des.add("Expects a single argument: directory name.");
		des.add("Prints a tree.");
		return des;
	}

}
