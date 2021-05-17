package hr.fer.oprpp1.hw04.db;

/**
 * The interface represents a comparison operator.
 * 
 * @author Petra
 *
 */
@FunctionalInterface
public interface IComparisonOperator {

	/**
	 * Tests if two strings satisfy the given comparison operator.
	 * 
	 * @param value1 value of first string
	 * @param value2 value of second string
	 * @return <code>true</code> if the given strings satisfy the operator and
	 *         <code>false</code> otherwise
	 */
	boolean satisfied(String value1, String value2);

}
