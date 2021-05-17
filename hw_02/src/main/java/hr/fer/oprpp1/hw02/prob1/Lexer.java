package hr.fer.oprpp1.hw02.prob1;

import java.util.Objects;

/**
 * The class represents a string input lexer.
 * 
 * @author Petra
 *
 */
public class Lexer {

	/**
	 * Character array of the input string that will be tokenized
	 */
	private char[] data;

	/**
	 * The last generated token
	 */
	private Token token;

	/**
	 * Index of the element that is currently being parsed
	 */
	private int currentIndex = 0;

	/**
	 * Lexer state
	 */
	private LexerState state = LexerState.BASIC;

	/**
	 * Creates an instance of the collection and passes the text that will be
	 * tokenized.
	 * 
	 * @param text the text will be tokenized
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text, "The given string mustn't be null");

		text = text.strip().replaceAll("[\t,\r,\\n]", "").replaceAll(" +", " ");
		this.data = text.toCharArray();
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @return the next token
	 */
	public Token nextToken() {
		if (this.state == LexerState.BASIC)
			return basicParser();
		else
			return extendedParser();
	}

	/**
	 * Checks if EOF has already been returned.
	 */
	private void ifEOF() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("There is no more tokens left.");
		}
	}

	/**
	 * Checks if the current element is the last one.
	 * 
	 * @return <code>true</code> if the current element is the last one and
	 *         <code>false</code> otherwise
	 */
	private boolean ifEmpty() {
		if (this.currentIndex == this.data.length || this.data.length == 0) {
			token = new Token(TokenType.EOF, null);
			return true;
		}
		return false;
	}

	/**
	 * Skips spaces.
	 */
	private boolean skipSpace() {
		if (this.data[currentIndex] == ' ') {
			this.currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * Generates a new token when lexer is in state BASIC
	 * 
	 * @return newly generated token
	 */
	public Token basicParser() {

		this.ifEOF();
		if (this.ifEmpty())
			return token;

		TokenType type = null;
		String value = "";
		while (this.currentIndex < this.data.length) {

			if (this.skipSpace())
				break;

			else if (Character.isLetter(this.data[currentIndex])) {
				if (type == null || type == TokenType.WORD) {
					type = TokenType.WORD;
					value += this.data[currentIndex++];
				} else {
					break;
				}
			}

			else if (this.data[currentIndex] == '\\') {
				if (type == null || type == TokenType.WORD) {
					type = TokenType.WORD;
					this.currentIndex++;

					if (this.currentIndex < this.data.length) {
						if (this.data[currentIndex] == '\\' || Character.isDigit(this.data[currentIndex])) {
							value += data[currentIndex++];
						} else {
							throw new LexerException(
									"The escape sequence \\" + this.data[currentIndex] + " is invalid");
						}
					} else {
						throw new LexerException("The input can not be only a backslash");
					}

				} else {
					break;
				}

			} else if (Character.isDigit(this.data[currentIndex])) {
				if (type == null || type == TokenType.NUMBER) {
					type = TokenType.NUMBER;
					value += this.data[currentIndex++];
				} else {
					break;
				}
			}

			else {
				if (type == null) {
					type = TokenType.SYMBOL;
					value += data[currentIndex++];
				} else {
					break;
				}
			}

		}

		if (type == TokenType.NUMBER) {
			try {
				long valueLong = Long.parseLong(value.toString());
				token = new Token(type, valueLong);
			} catch (NumberFormatException ex) {
				throw new LexerException("The number is invaid. It can not be parsed to long.");
			}
		} else if (type == TokenType.SYMBOL) {
			token = new Token(type, value.charAt(0));
		} else {
			token = new Token(type, value);
		}

		return token;
	}

	/**
	 * Generates a new token when the lexer is in EXTENED state.
	 * 
	 * @return a newly generated token
	 */
	public Token extendedParser() {

		this.ifEOF();
		if (this.ifEmpty())
			return token;

		String value = "";
		while (this.currentIndex < this.data.length) {
			if (this.skipSpace() || this.data[this.currentIndex] == '#')
				break;
			value += this.data[this.currentIndex++];
		}

		if (value.length() == 0)
			token = new Token(TokenType.SYMBOL, this.data[this.currentIndex++]);
		else
			token = new Token(TokenType.WORD, value);

		return token;
	}

	/**
	 * Returns the the most recently generated token. It can be called multiple
	 * times and it doesn't cause generation of the next token.
	 * 
	 * @return the most recently generated token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Sets the lexer state to the given state
	 * 
	 * @param state the state the lexer will be set to
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state, "The state mustn't be null");
		this.state = state;
	}

}
