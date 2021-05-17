package hr.fer.oprpp1.hw08.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * The class represents localized menu.
 * 
 * @author Petra
 *
 */
public class LocalizableJMenu extends JMenu {

	private static final long serialVersionUID = 1L;

	private ILocalizationListener listener;
	private ILocalizationProvider provider;
	private String key;

	/**
	 * Constructor.
	 * 
	 * @param key      menu name key
	 * @param provider language provider
	 */
	public LocalizableJMenu(String key, ILocalizationProvider provider) {
		this.provider = provider;
		this.key = key;
		this.listener = () -> update();
		update();
		this.provider.addLocalizationListener(this.listener);
	}

	private void update() {
		setText(this.provider.getString(key));
	}
}