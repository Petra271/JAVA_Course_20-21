package hr.fer.zemris.java.hw05.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.zemris.java.hw05.shell.commands.CatCommand;
import hr.fer.zemris.java.hw05.shell.commands.CharsetsCommand;
import hr.fer.zemris.java.hw05.shell.commands.CopyCommand;
import hr.fer.zemris.java.hw05.shell.commands.ExitCommand;
import hr.fer.zemris.java.hw05.shell.commands.HelpCommand;
import hr.fer.zemris.java.hw05.shell.commands.HexdumpCommand;
import hr.fer.zemris.java.hw05.shell.commands.LsCommand;
import hr.fer.zemris.java.hw05.shell.commands.MkdirCommand;
import hr.fer.zemris.java.hw05.shell.commands.SymbolCommand;
import hr.fer.zemris.java.hw05.shell.commands.TreeCommand;

/**
 * Implementation of {@linkplain Environment}.
 * 
 * @author Petra
 *
 */
public class ShellEnvironment implements Environment {

	/**
	 * Multiline symbol
	 */
	private Character multiline = '|';
	/**
	 * Prompt symbol
	 */
	private Character prompt = '>';
	/**
	 * Moreline symbol
	 */
	private Character moreline = '\\';
	/**
	 * A map of commands.
	 */
	private SortedMap<String, ShellCommand> cmds;
	/**
	 * Scanner
	 */
	private Scanner sc = new Scanner(System.in);

	/**
	 * Creates an instance of this class.
	 */
	public ShellEnvironment() {
		System.out.print("Welcome to MyShell v 1.0\n> ");
		this.cmds = new TreeMap<>();
		ShellCommand help = new HelpCommand();
		ShellCommand tree = new TreeCommand();
		ShellCommand cat = new CatCommand();
		ShellCommand ls = new LsCommand();
		ShellCommand copy = new CopyCommand();
		ShellCommand mkdir = new MkdirCommand();
		ShellCommand hexdump = new HexdumpCommand();
		ShellCommand exit = new ExitCommand();
		ShellCommand charset = new CharsetsCommand();
		ShellCommand symbol = new SymbolCommand();
		this.cmds.put(help.getCommandName(), help);
		this.cmds.put(tree.getCommandName(), tree);
		this.cmds.put(cat.getCommandName(), cat);
		this.cmds.put(ls.getCommandName(), ls);
		this.cmds.put(copy.getCommandName(), copy);
		this.cmds.put(mkdir.getCommandName(), mkdir);
		this.cmds.put(hexdump.getCommandName(), hexdump);
		this.cmds.put(exit.getCommandName(), exit);
		this.cmds.put(charset.getCommandName(), charset);
		this.cmds.put(symbol.getCommandName(), symbol);
	}

	/**
	 * Reads a new line from console.
	 */
	@Override
	public String readLine() throws ShellIOException {
		StringBuilder sb = new StringBuilder();
		String l = this.sc.nextLine();
		while (l.endsWith(moreline.toString())) {
			sb.append(l.substring(0, l.length() - 1));
			write(multiline + " ");
			l = this.sc.nextLine();
		}
		sb.append(l);
		return sb.toString();
	}

	/**
	 * Writes text to console.
	 */
	@Override
	public void write(String text) throws ShellIOException {
		System.out.print(text);
	}

	/**
	 * Writes text and a new line to console.
	 */
	@Override
	public void writeln(String text) throws ShellIOException {
		System.out.println(text);
	}

	/**
	 * Returns supported commmands.
	 */
	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(this.cmds);
	}

	/**
	 * Returns multiline symbol.
	 */
	@Override
	public Character getMultilineSymbol() {
		return this.multiline;
	}

	/**
	 * Sets multiline symbol.
	 */
	@Override
	public void setMultilineSymbol(Character symbol) {
		this.multiline = symbol;
	}

	/**
	 * Returns prompt symbol.
	 */
	@Override
	public Character getPromptSymbol() {
		return this.prompt;
	}

	/**
	 * Sets prompt symbol.
	 */
	@Override
	public void setPromptSymbol(Character symbol) {
		this.prompt = symbol;
	}

	/**
	 * Returns morelines symbol.
	 */
	@Override
	public Character getMorelinesSymbol() {
		return this.moreline;
	}

	/**
	 * Sets morelines symbol.
	 */
	@Override
	public void setMorelinesSymbol(Character symbol) {
		this.moreline = symbol;
	}

}
