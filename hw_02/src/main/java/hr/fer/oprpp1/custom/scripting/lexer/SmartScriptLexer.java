package hr.fer.oprpp1.custom.scripting.lexer;

import java.util.Objects;
import java.util.Set;

public class SmartScriptLexer {

	/**
	 * Operators
	 */
	private static final Set<Character> OPERATORS = Set.of('*', '/', '-', '+', '^');

	private static final Set<Character> ESCAPE_TEXT = Set.of('\\', '{');
	private static final Set<Character> ESCAPE_TAG_SINGLE = Set.of('\\', '"');
	private static final Set<Character> ESCAPE_TAG_DOUBLE = Set.of('n', 'r', 't');

	/**
	 * Character array of the input that will be tokenized
	 */
	private char[] data;

	/**
	 * The last generated token
	 */
	private SmartScriptToken token;

	/**
	 * Index of the element that is currently being parsed
	 */
	private int currentIndex = 0;

	/**
	 * Lexer state
	 */
	private SmartScriptLexerState state;

	/**
	 * Creates an instance of the collection and passes the text that will be
	 * tokenized.
	 * 
	 * @param documentBody the text will be tokenized
	 */
	public SmartScriptLexer(String documentBody) {
		Objects.requireNonNull(documentBody, "The given string mustn't be null");

		documentBody = documentBody.strip().replaceAll(" +", " ");
		this.data = documentBody.toCharArray();
		this.state = SmartScriptLexerState.TEXT;
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @return the next token
	 */
	public SmartScriptToken nextToken() {
		if (this.state == SmartScriptLexerState.TEXT)
			return parseText();
		else
			return parseTag();
	}

	/**
	 * Checks if EOF has already been returned.
	 */
	private void ifEOF() {
		if (token != null && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("There is no more tokens left.");
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
			token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return true;
		}
		return false;
	}

	/**
	 * Skips spaces.
	 */
	private boolean skipSpace() {
		if (this.data[this.currentIndex] == ' ') {
			this.currentIndex++;
			return true;
		}
		return false;
	}

	/**
	 * Skips \r, \t, \r
	 */
	private boolean skipEmpty() {
		if (this.data[this.currentIndex] == '\n' || this.data[this.currentIndex] == '\t'
				|| this.data[this.currentIndex] == '\r') {
			this.currentIndex++;

			return true;
		}
		return false;
	}

	/**
	 * Checks is the given index is out of bounds.
	 * 
	 * @return <code>true</code> if the current index is in collection bounds and
	 *         <code>false</code> otherwise
	 */
	private boolean indexWithinBounds() {
		return this.currentIndex < this.data.length;
	}

	/**
	 * Generates a new token when lexer is in state TEXT.
	 * 
	 * @return newly generated token
	 */
	public SmartScriptToken parseText() {
		this.ifEOF();
		if (this.ifEmpty())
			return token;

		StringBuilder value = new StringBuilder();

		while (indexWithinBounds()) {
			this.skipEmpty();

			if (this.data[this.currentIndex] == '\\') {
				this.currentIndex++;

				if (!indexWithinBounds())
					break;

				if (ESCAPE_TEXT.contains(this.data[currentIndex])) {
					value.append(this.data[this.currentIndex++]);

				} else {
					throw new SmartScriptLexerException(
							"The escape sequence \\" + this.data[this.currentIndex] + " is invalid.");
				}

			} else if (this.data[this.currentIndex] == '{') {
				if (value.length() != 0)
					break;

				value.append(this.data[this.currentIndex++]);
				if (!indexWithinBounds())
					break;

				if (this.data[currentIndex] == '$') {
					value.append(this.data[this.currentIndex++]);
					this.skipSpace();
					break;
				} else {
					throw new SmartScriptLexerException("The BEGIN tag is invalid");
				}

			} else {
				value.append(this.data[this.currentIndex++]);
			}
		}

		if (value.toString().equals("{$")) {
			token = new SmartScriptToken(SmartScriptTokenType.BEGIN, value.toString());
		} else {
			token = new SmartScriptToken(SmartScriptTokenType.TEXT, value.toString());
		}

		return token;
	}

	/**
	 * Generates a new token when lexer is in state TAG
	 * 
	 * @return newly generated token
	 */
	public SmartScriptToken parseTag() {

		this.ifEOF();
		if (this.ifEmpty())
			return token;

		SmartScriptTokenType type = null;
		boolean text = false;
		StringBuilder value = new StringBuilder();

		while (true) {

			if (!indexWithinBounds())
				break;

			if (!text && this.skipSpace())
				break;

			if (text) {

				if (this.data[this.currentIndex] == '\\') {
					this.currentIndex++;

					if (!indexWithinBounds())
						break;
					if (ESCAPE_TAG_DOUBLE.contains(this.data[this.currentIndex])) {
						value.append("\\" + this.data[this.currentIndex++]);
					} else if (ESCAPE_TAG_SINGLE.contains(this.data[this.currentIndex])) {
						value.append(this.data[this.currentIndex++]);
					} else {
						throw new SmartScriptLexerException("The escape sequence is invalid.");
					}

				} else if (this.data[this.currentIndex] == '"') {
					value.append(this.data[this.currentIndex++]);
					this.skipSpace();
					break;
				} else {
					value.append(this.data[this.currentIndex++]);

				}
			}

			else if (this.data[this.currentIndex] == '$') {
				if (type != null)
					break;

				value.append(this.data[this.currentIndex++]);
				if (!indexWithinBounds())
					break;

				if (this.data[currentIndex] == '}') {

					value.append(this.data[this.currentIndex++]);
					type = SmartScriptTokenType.END;
					if (!indexWithinBounds())
						break;
					// this.skipSpace();
					break;
				} else {
					throw new SmartScriptLexerException("The END tag is invalid.");
				}
			}

			else if (this.data[this.currentIndex] == '"') {
				if (type != null && type != SmartScriptTokenType.TEXT)
					break;
				text = true;
				type = SmartScriptTokenType.TEXT;
				value.append(this.data[currentIndex++]);
			}

			else if (Character.isDigit(this.data[this.currentIndex])) {
				if (type == null) {
					type = SmartScriptTokenType.INTEGER;
				} else if (type != SmartScriptTokenType.INTEGER && type != SmartScriptTokenType.DOUBLE) {
					break;
				}
				value.append(this.data[this.currentIndex++]);
			}

			else if (this.data[this.currentIndex] == '.' && type == SmartScriptTokenType.INTEGER) {
				type = SmartScriptTokenType.DOUBLE;
				value.append(this.data[this.currentIndex++]);
			}

			else if (OPERATORS.contains(this.data[this.currentIndex])) {
				if (this.data[this.currentIndex] == '-' && Character.isDigit(this.data[this.currentIndex + 1])) {
					value.append(this.data[this.currentIndex++]);
				} else {

					type = SmartScriptTokenType.OPERATOR;
					value.append(this.data[this.currentIndex++]);
					this.skipSpace();
					break;
				}
			} else if (this.data[this.currentIndex] == '=') {
				type = SmartScriptTokenType.TEXT;
				value.append(this.data[this.currentIndex++]);
				this.skipSpace();
				break;
			} else if (this.skipSpace()) {
				break;
			} else {
				if (type != null && type != SmartScriptTokenType.TEXT)
					break;
				type = SmartScriptTokenType.TEXT;
				value.append(data[currentIndex++]);

			}

		}

		if (type == SmartScriptTokenType.TEXT) {
			token = new SmartScriptToken(type, value.toString());
		} else if (type == SmartScriptTokenType.INTEGER) {
			try {
				int number = Integer.parseInt(value.toString());
				return token = new SmartScriptToken(type, number);
			} catch (NumberFormatException ex) {
				throw new SmartScriptLexerException("The number " + value + " can not be parsed");
			}
		} else if (type == SmartScriptTokenType.DOUBLE) {
			try {
				double number = Double.parseDouble(value.toString());
				token = new SmartScriptToken(type, number);
			} catch (NumberFormatException ex) {
				throw new SmartScriptLexerException("The number " + value + " can not be parsed");
			}
		} else if (type == SmartScriptTokenType.END) {
			token = new SmartScriptToken(type, value.toString());
		} else {
			token = new SmartScriptToken(type, value.toString());
		}

		return token;
	}

	/**
	 * Returns the the most recently generated token. It can be called multiple
	 * times and it doesn't cause generation of the next token.
	 * 
	 * @return the most recently generated token
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Sets the lexer state to the given state
	 * 
	 * @param state the state the lexer will be set to
	 */
	public void setState(SmartScriptLexerState state) {
		Objects.requireNonNull(state, "The state mustn't be null");
		this.state = state;
	}

	/**
	 * Returns the current lexer state.
	 * 
	 * @return the current lexer state
	 */
	public SmartScriptLexerState getState() {
		return this.state;
	}
}