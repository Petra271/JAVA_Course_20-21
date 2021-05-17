package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;

/**
 * Represents a single for-loop construct. It inherits from {@link Node}.
 * 
 * @author Petra
 *
 */
public class ForLoopNode extends Node {

	/**
	 * For-loop variable
	 */
	private ElementVariable variable;

	/**
	 * Start expression of a for-loop
	 */
	private Element startExpression;

	/**
	 * End expression of a for-loop
	 */
	private Element endExpression;

	/**
	 * Step expression of a for-loop
	 */
	private Element stepExpresion;

	/**
	 * Creates an instance of this class with the given values.
	 * 
	 * @param variable        for-loop variable
	 * @param startExpression start expression of a for-loop
	 * @param endExpression   end expression of a for-loop
	 * @param stepExpresion   step expression of a for-loop
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpresion) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpresion = stepExpresion;
	}

	/**
	 * Return the for-loop value.
	 */
	@Override
	public Object getValue() {
		StringBuilder s = new StringBuilder();
		s.append("{$FOR ").append(this.variable.asText() + " ").append(this.startExpression.asText() + " ")
				.append(this.endExpression.asText() + " ");
		if (this.stepExpresion != null) {
			s.append(this.stepExpresion.asText());
		}
		s.append("$}");
		return s.toString();
	}

}
