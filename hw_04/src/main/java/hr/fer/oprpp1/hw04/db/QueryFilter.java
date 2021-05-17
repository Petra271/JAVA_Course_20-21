package hr.fer.oprpp1.hw04.db;

import java.util.List;

/**
 * The class represents a query filter used for checking if a record satisfies
 * the given query.
 * 
 * @author Petra
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of queries
	 */
	List<ConditionalExpression> list;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param list query list
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		this.list = list;
	}

	/**
	 * Checks if the filter accepts the given student record.
	 * 
	 * @param record a student record
	 * @return <code>true</code> if the filter accepts the given student record and
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		for (ConditionalExpression e : list) {

			if (e.getFieldGetter() == FieldValueGetters.JMBAG) {
				if (!e.getComparisonOperator().satisfied(record.getJmbag(), e.getStringLiteral()))
					return false;
			}

			else if (e.getFieldGetter() == FieldValueGetters.FIRST_NAME) {
				if (!e.getComparisonOperator().satisfied(record.getFirstName(), e.getStringLiteral()))
					return false;
			}

			else if (e.getFieldGetter() == FieldValueGetters.LAST_NAME) {
				if (!e.getComparisonOperator().satisfied(record.getLastName(), e.getStringLiteral()))
					return false;
			}

			else
				throw new RuntimeException("Field getter is invalid.");

		}
		return true;
	}

}
