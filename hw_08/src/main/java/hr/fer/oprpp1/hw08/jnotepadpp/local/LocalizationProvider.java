package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * The class represents a language provider.
 * 
 * @author Petra
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	private String language;
	private ResourceBundle bundle;
	private static LocalizationProvider instance;

	private LocalizationProvider() {
		this.language = "en";
		setBundle();
	}

	@Override
	public String getString(String key) {
		return this.bundle.getString(key);
	}

	public static LocalizationProvider getInstance() {
		if(instance == null)
			instance = new LocalizationProvider();
		return instance;
	}

	/**
	 * Sets language based on the given value.
	 * 
	 * @param language language
	 */
	public void setLanguage(String language) {
		this.language = language;
		setBundle();
		fire();
	}

	/**
	 * Sets resource bundle.
	 */
	private void setBundle() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.oprpp1.hw08.jnotepadpp.local.prijevodi", locale);
	}

	/**
	 * Language getter.
	 * 
	 * @return current language
	 */
	public String getLanguage() {
		return this.language;
	}

}
