package hr.fer.oprpp1.hw04.db;

/**
 * The class contains implementations of {@link IFieldValueGetter}.
 * 
 * @author Petra
 *
 */
public class FieldValueGetters {

	/**
	 * JMBAG getter
	 */
	public static final IFieldValueGetter JMBAG = StudentRecord::getJmbag;
	/**
	 * First name getter
	 */
	public static final IFieldValueGetter FIRST_NAME = StudentRecord::getFirstName;
	/**
	 * Last name getter
	 */
	public static final IFieldValueGetter LAST_NAME = StudentRecord::getLastName;

}
