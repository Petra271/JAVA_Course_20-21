package hr.fer.zemris.java.hw05.shell;

import java.util.SortedMap;

/**
 * Represents {@link MyShell} working environment.
 * 
 * @author Petra
 *
 */
public interface Environment {

	/**
	 * Reads a new line from console.
	 * 
	 * @return line
	 * @throws ShellIOException if an error occurs
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes text to console.
	 * 
	 * @param text text that will be written
	 * @throws ShellIOException if an error occurs
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes text and a new line to console.
	 * 
	 * @param text text that will be written
	 * @throws ShellIOException if an error occurs
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Returns all available commands.
	 * 
	 * @return all available commands.
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Returns multiline symbol.
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets multiline symbol.
	 * 
	 * @param symbol new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Returns prompt symbol.
	 * 
	 * @return prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets prompt symbol.
	 * 
	 * @param symbol new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Returns morelines symbol.
	 * 
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets morelines symbol.
	 * 
	 * @param symbol new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
