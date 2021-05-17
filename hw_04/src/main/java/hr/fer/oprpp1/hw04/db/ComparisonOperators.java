package hr.fer.oprpp1.hw04.db;

/**
 * This class contains implementations of {@link IComparisonOperator}.
 * 
 * @author Petra
 *
 */
public class ComparisonOperators {

	/**
	 * LESS operator.
	 */
	public static final IComparisonOperator LESS = (v1, v2) -> v1.compareTo(v2) < 0;

	/**
	 * LESS_OR_EQUALS operator.
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) <= 0;

	/**
	 * GREATER operator.
	 */
	public static final IComparisonOperator GREATER = (v1, v2) -> v1.compareTo(v2) > 0;

	/**
	 * GREATER_OR_EQUALS operator.
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (v1, v2) -> v1.compareTo(v2) >= 0;

	/**
	 * EQUALS operator.
	 */
	public static final IComparisonOperator EQUALS = (v1, v2) -> v1.compareTo(v2) == 0;

	/**
	 * NOT_EQUALS operator.
	 */
	public static final IComparisonOperator NOT_EQUALS = (v1, v2) -> v1.compareTo(v2) != 0;

	/**
	 * LIKE operator.
	 */
	public static final IComparisonOperator LIKE = (v1, v2) -> {

		long count = v2.chars().filter(ch -> ch == '*').count();
		if (count > 1)
			throw new RuntimeException("Number of wildcard elements must be 1 at most.");

		if (v2.contains("*")) {

			if ((v2.length() - 1) > v1.length()) {
				return false;
			}

			int index = v2.indexOf("*");
			if (index == 0)
				return v1.endsWith(v2.substring(1));
			else if (index == v2.length() - 1)
				return v1.startsWith(v2.substring(0, v2.length() - 1));
			else
				return v1.startsWith(v2.substring(0, index)) && v1.endsWith(v2.substring(index + 1, v2.length() - 1));

		} else
			return v1.equals(v2);
	};

}
