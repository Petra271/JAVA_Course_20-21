package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * Represents localization provider used for supporting multiple languages.
 * 
 * @author Petra
 *
 */
public interface ILocalizationProvider {

	/**
	 * Adds localization listener.
	 * 
	 * @param l added listener
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * Removes localization listener.
	 * 
	 * @param l removed listener
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * Returns string for the given key.
	 * 
	 * @param key key
	 * @return string for the given key
	 */
	String getString(String key);

}
