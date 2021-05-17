package hr.fer.oprpp1.hw04.db;

/**
 * The class represents a conditional expression which is a part of a query.
 * 
 * @author Petra
 *
 */
public class ConditionalExpression {

	/**
	 * Field getter
	 */
	private IFieldValueGetter fieldGetter;

	/**
	 * Comparison operator
	 */
	private IComparisonOperator comparisonOperator;

	/**
	 * String literal
	 */
	private String stringLiteral;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param fieldGetter        field getter
	 * @param comparisonOperator comparison operator
	 * @param stringLiteral      string literal
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, IComparisonOperator comparisonOperator,
			String stringLiteral) {

		this.fieldGetter = fieldGetter;
		this.comparisonOperator = comparisonOperator;
		this.stringLiteral = stringLiteral;
	}

	/**
	 * Returns the field getter.
	 * 
	 * @return the field getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns the comparison operator.
	 * 
	 * @return the comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Returns the string literal.
	 * 
	 * @return the string literal
	 */
	public String getStringLiteral() {
		return stringLiteral;
	}

}
