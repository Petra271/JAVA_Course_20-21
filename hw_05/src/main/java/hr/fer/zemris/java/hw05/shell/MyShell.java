package hr.fer.zemris.java.hw05.shell;

/**
 * Represents a command line. It suppots operations: 'charsets', 'ls', 'cat',
 * 'tree', 'help', 'copy', 'mkdir', 'hexdump', 'symbol', 'exit'.
 * 
 * @author Petra
 *
 */
public class MyShell {

	public static void main(String[] args) {
		Environment environment = new ShellEnvironment();
		ShellStatus status = ShellStatus.CONTINUE;

		do {
			String line = environment.readLine().strip();
			String name = Utils.getCommandName(line);
			String arguments = line.substring(name.length()).strip();
			ShellCommand cmd = environment.commands().get(name);

			if (cmd == null) {
				environment.writeln("Invalid command.");
				environment.write(environment.getPromptSymbol() + " ");
			} else
				status = cmd.executeCommand(environment, arguments);
		} while (status != ShellStatus.TERMINATE);

	}

}
