package hr.fer.oprpp1.hw04.db;

/**
 * This interface represents a field value getter.
 * 
 * @author Petra
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {

	/**
	 * Returns the value of the given field from {@link StudentRecord}
	 * 
	 * @param record a student record
	 * @return a student record field
	 */
	String get(StudentRecord record);

}
