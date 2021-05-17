package hr.fer.oprpp1.custom.scripting.nodes;

/**
 * Represents a piece of textual data. It inherits from {@link Node}.
 * 
 * @author Petra
 *
 */
public class TextNode extends Node {

	/**
	 * The text which a node contains
	 */
	private String text;

	/**
	 * Creates an instance of this class with the given text.
	 * 
	 * @param text text which is put into the node
	 */
	public TextNode(String text) {
		this.text = text;
	}

	/**
	 * Returns the value of this text node
	 * 
	 * @return value of this text node
	 */
	@Override
	public String getValue() {
		return this.text;
	}

	/**
	 * Returns this object in string form. return string representation of this
	 * object
	 */
	@Override
	public String toString() {

		char[] ch = this.text.toCharArray();
		StringBuilder s = new StringBuilder();

		for (char c : ch) {
			if (c == '{' || c == '\\' || c == '\"') {
				s.append("\\");
			}
			if (c == '\n' || c == '\t' || c == '\r')
				continue;
			s.append(c);
		}

		return s.toString();
	}

}
