package hr.fer.oprpp1.hw04.db;

/**
 * This interface represents a filter used for filtering {@link StudentRecord}.
 * 
 * @author Petra
 *
 */
@FunctionalInterface
public interface IFilter {

	/**
	 * Checks if the filter accepts the given student record.
	 * 
	 * @param record a student record
	 * @return <code>true</code> if the filter accepts the given student record and
	 *         <code>false</code> otherwise
	 */
	boolean accepts(StudentRecord record);

}
