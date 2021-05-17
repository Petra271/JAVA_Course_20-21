package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;

/**
 * Represents a command which generates some textual output dynamically. It
 * inherits from {@link Node}.
 * 
 * @author Petra
 *
 */
public class EchoNode extends Node {

	/**
	 * Echo node elements
	 */
	private Element[] elements;

	/**
	 * Creates an instance of this class with the given elements.
	 * 
	 * @param elements echo node elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	/**
	 * Return the echo node elements.
	 * 
	 * @return the echo node elements
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Return the value in string form.
	 */
	@Override
	public String getValue() {

		StringBuilder s = new StringBuilder();

		s.append("{$=");
		for (Element e : this.elements) {

			if (e.asText().charAt(0) == '\"') {
				char[] array = e.asText().toCharArray();
				s.append(array[0]);
				for (int i = 1; i < array.length - 1; i++) {
					if (e.asText() == "\n" || e.asText() == "\t" || e.asText() == "\r" || e.asText() == "\\"
							|| array[i] == '\"') {
						s.append("\\").append(array[i]);
					} else {
						s.append(array[i]);
					}
				}
				s.append(array[array.length - 1] + " ");

			}

			else {
				s.append(e.asText() + " ");
			}

		}
		s.append("$}");

		return s.toString();
	}

}
