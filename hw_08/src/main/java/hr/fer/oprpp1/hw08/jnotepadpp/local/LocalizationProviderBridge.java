package hr.fer.oprpp1.hw08.jnotepadpp.local;

/**
 * This class represents a bridge to {@link LocalizationProvider}.
 * 
 * @author Petra
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	private boolean connected;
	private ILocalizationProvider parent;
	private ILocalizationListener l;

	/**
	 * Constructor.
	 * 
	 * @param parent language provider
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		this.l = () -> fire();
	}

	/**
	 * Disconnects from language provider.
	 */
	public void disconnect() {
		this.connected = false;
		this.parent.removeLocalizationListener(l);
	}

	/**
	 * Connects to language provider.
	 */
	public void connect() {
		if (!this.connected) {
			this.connected = true;
			this.parent.addLocalizationListener(l);
		}

	}

	@Override
	public String getString(String key) {
		return this.parent.getString(key);
	}

}
