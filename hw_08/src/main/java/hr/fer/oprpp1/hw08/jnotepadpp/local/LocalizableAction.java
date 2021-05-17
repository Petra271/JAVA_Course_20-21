package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.AbstractAction;

/**
 * The class represent localizable action.
 * 
 * @author Petra
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private String key;
	private ILocalizationListener listener;
	private ILocalizationProvider provider;

	/**
	 * Constructor.
	 * 
	 * @param key action name key
	 * @param provider language provider
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;
		this.listener = () -> change();
		change();
		this.provider.addLocalizationListener(this.listener);

	}

	private void change() {
		putValue(NAME, this.provider.getString(key));
	}

}
