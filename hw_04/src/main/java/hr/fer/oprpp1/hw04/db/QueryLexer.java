package hr.fer.oprpp1.hw04.db;

import java.util.Objects;
import java.util.Set;

/**
 * The class represents a query lexer.
 * 
 * @author Petra
 *
 */
public class QueryLexer {

	/**
	 * Comparison operators
	 */
	private static final Set<Character> OPERATORS = Set.of('>', '<', '=', '!');

	/**
	 * Character array of the input string that will be tokenized
	 */
	private char[] data;

	/**
	 * The last generated token
	 */
	private QueryToken token;

	/**
	 * Index of the element that is currently being parsed
	 */
	private int currentIndex = 0;

	/**
	 * Creates an instance of the collection and passes the text that will be
	 * tokenized.
	 * 
	 * @param text the text will be tokenized
	 */
	public QueryLexer(String text) {
		Objects.requireNonNull(text, "The given string mustn't be null");
		text = text.strip().replaceAll("[\t,\r,\\n]", "").replaceAll(" +", " ");
		this.data = text.toCharArray();
	
	}

	/**
	 * Generates and returns the next token.
	 * 
	 * @return the next token
	 */
	public QueryToken nextToken() {
		this.ifEOF();
		if (this.ifEmpty())
			return token;
		
		this.skipSpace();

		QueryTokenType type = null;
		StringBuilder s = new StringBuilder();
		boolean string = false;
		while (this.currentIndex < this.data.length) {

			if (!string) {
				if (this.data[this.currentIndex] == '"') {
					if (type != null)
						break;
					type = QueryTokenType.VALUE;
					s.append(this.data[this.currentIndex++]);
					string = true;
				} else if (OPERATORS.contains(this.data[this.currentIndex])) {
					if (type != null)
						break;
					type = QueryTokenType.OPERATOR;
					s.append(this.data[this.currentIndex++]);
					if (this.currentIndex >= this.data.length)
						break;
					if (this.data[this.currentIndex] == '=')
						s.append(this.data[this.currentIndex++]);
					break;
				} else if(this.skipSpace()){
					break;
				} else {
					type = QueryTokenType.FIELD;
					s.append(this.data[this.currentIndex++]);
				}
			} else {
				if (this.data[this.currentIndex] == '"') {
					s.append(this.data[this.currentIndex++]);
					break;
				}
				s.append(this.data[this.currentIndex++]);
			}

		}

		String value = s.toString();
		if (type == QueryTokenType.FIELD) {
			switch (value.toUpperCase()) {
			case "AND" -> token = new QueryToken(QueryTokenType.AND, value.toUpperCase());
			case "LIKE" -> token = new QueryToken(QueryTokenType.OPERATOR, value.toUpperCase());
			default -> token = new QueryToken(QueryTokenType.FIELD, value);
			}
		} else if (type == QueryTokenType.EOF)
			token = new QueryToken(type, null);
		else if (type == QueryTokenType.VALUE || type == QueryTokenType.OPERATOR)
			token = new QueryToken(type, value);
		else
			throw new QueryLexerException("Token '" + value + "' is invalid");

		return token;
	}

	/**
	 * Checks if EOF has already been returned.
	 */
	private void ifEOF() {
		if (token != null && token.getType() == QueryTokenType.EOF) {
			throw new QueryLexerException("There is no more tokens left.");
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
			token = new QueryToken(QueryTokenType.EOF, null);
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
	 * Returns the the most recently generated token. It can be called multiple
	 * times and it doesn't cause generation of the next token.
	 * 
	 * @return the most recently generated token
	 */
	public QueryToken getToken() {
		return token;
	}

}
