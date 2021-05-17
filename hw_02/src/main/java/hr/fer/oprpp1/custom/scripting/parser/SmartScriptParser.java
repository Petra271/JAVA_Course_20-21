package hr.fer.oprpp1.custom.scripting.parser;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;

/**
 * The class represents an object used for parsing a text file.
 * 
 * @author Petra
 *
 */
public class SmartScriptParser {

	/**
	 * Operators
	 */
	private static final Set<String> OPERATORS = Set.of("*", "/", "-", "+", "^");

	/**
	 * Document which will be parsed
	 */
	private DocumentNode document;

	/**
	 * Stack used for building a tree
	 */
	private ObjectStack stack;

	/**
	 * Lexer used for parsing the document
	 */
	private SmartScriptLexer lexer;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param text text that will be parsed
	 */
	public SmartScriptParser(String text) {
		Objects.requireNonNull(text, "The given text musn't be null");
		this.document = new DocumentNode();
		this.stack = new ObjectStack();
		this.lexer = new SmartScriptLexer(text);
		this.stack.push(document);

		try {
			parseText();
		} catch (SmartScriptLexerException e) {
			throw new SmartScriptParserException("The given text can not be parsed");
		}

	}

	/**
	 * Parses text when lexer is in TEXT state
	 */
	private void parseText() {

		while (true) {
			if (lexer.getState() == SmartScriptLexerState.TAG) {
				this.parseTag();
			} else if (lexer.getState() == SmartScriptLexerState.TEXT) {
				SmartScriptToken token = this.lexer.nextToken();

				if (token.getType() == SmartScriptTokenType.EOF)
					break;
				if (token.getType() == SmartScriptTokenType.TEXT) {
					TextNode textNode = new TextNode((String) token.getValue());
					Node parentNode = (Node) this.stack.peek();
					parentNode.addChildNode(textNode);
				} else if (token.getType() == SmartScriptTokenType.BEGIN) {
					lexer.setState(SmartScriptLexerState.TAG);
				} else {
					throw new SmartScriptParserException("Token " + lexer.getToken() + " can't be a part of the text.");
				}
			} else {
				throw new SmartScriptParserException("The state is invalid.");
			}
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException(
					"There is an error in the document. It contains more {$END$}-s than opened non-empty tags");
		}
	}

	/**
	 * Parsed text when lexer is in TAG state.
	 */
	private void parseTag() {

		SmartScriptToken token = lexer.nextToken();

		if (token.getValue().toString().isBlank()) {
			token = lexer.nextToken();
		}

		if (token.getType() == SmartScriptTokenType.TEXT) {

			switch (token.getValue().toString().toUpperCase()) {
			case "=" -> parseEcho();
			case "FOR" -> parseFor();
			case "END" -> {
				if (lexer.nextToken().getType() == SmartScriptTokenType.END) {
					lexer.setState(SmartScriptLexerState.TEXT);
					stack.pop();
				}
			}
			default -> throw new SmartScriptParserException("The tag " + token.getValue().toString() + " is invalid.");
			}
		} else {
			throw new SmartScriptParserException("The tag name is not given.");
		}
		lexer.setState(SmartScriptLexerState.TEXT);
	}

	/**
	 * Parser echo tag.
	 */
	private void parseEcho() {

		ArrayIndexedCollection col = new ArrayIndexedCollection();

		while (true) {

			if (lexer.nextToken().getType() == SmartScriptTokenType.END) {

				break;
			}
			col.add(parseForEchoElements(lexer.getToken()));
		}

		Element[] elements = Arrays.copyOf(col.toArray(), col.size(), Element[].class);
		EchoNode echoNode = new EchoNode(elements);
		Node parentNode = (Node) stack.peek();
		parentNode.addChildNode(echoNode);
	}

	/**
	 * Parses for-loop tag.
	 */
	private void parseFor() {

		int elementCount = 0;

		if (lexer.nextToken().getType() != SmartScriptTokenType.TEXT) {
			throw new SmartScriptParserException("The variable " + lexer.getToken().getValue() + " is invalid");
		}

		ElementVariable forTagName = new ElementVariable(lexer.getToken().getValue().toString());

		if (!Character.isLetter(lexer.getToken().getValue().toString().charAt(0))) {
			throw new SmartScriptParserException("Variable name must start with a letter.");
		}

		Element[] elements = new Element[3];
		while (elementCount < 4) {

			if (lexer.nextToken().getType() == SmartScriptTokenType.END) {
				if (elementCount > 1) {
					break;
				}
				throw new SmartScriptParserException("There are too few elements in the for-loop");
			} else {
				if (elementCount == 3) {

					throw new SmartScriptParserException("There are too many elements in the for-loop");
				} else {
					elements[elementCount] = parseForEchoElements(lexer.getToken());
				}
			}
			elementCount++;
		}

		ForLoopNode forLoopNode = new ForLoopNode(forTagName, elements[0], elements[1], elements[2]);
		Node parentNode = (Node) stack.peek();
		parentNode.addChildNode(forLoopNode);
		stack.push(forLoopNode);
	}

	/**
	 * Parses elements of a for-loop or an echo tag.
	 * 
	 * @param token
	 * @return
	 */
	private Element parseForEchoElements(SmartScriptToken token) {

		if (token.getType() == SmartScriptTokenType.OPERATOR) {
			String text = token.getValue().toString();
			if (OPERATORS.contains(text)) {
				return new ElementOperator(text);
			}
		}
		if (token.getType() == SmartScriptTokenType.TEXT) {
			String text = token.getValue().toString();
			if (text.startsWith("\"")) {
				return new ElementString(text);
			} else if (text.startsWith("@") && text.length() > 1 && Character.isLetter(text.charAt(1))) {
				return new ElementFunction(text);
			} else if (!Character.isLetter(text.charAt(0))) {
				throw new SmartScriptParserException("Variable " + text + " is invalid.");
			}
			return new ElementVariable(text);
		} else if (token.getType() == SmartScriptTokenType.INTEGER) {
			return new ElementConstantInteger((int) token.getValue());
		} else if (token.getType() == SmartScriptTokenType.DOUBLE) {
			return new ElementConstantDouble((double) token.getValue());
		}

		throw new SmartScriptParserException("Token " + " is invalid.");
	}

	/**
	 * Returns the document node, the parent of the tree.
	 * 
	 * @return document node
	 */
	public DocumentNode getDocumentNode() {
		return this.document;
	}
}
