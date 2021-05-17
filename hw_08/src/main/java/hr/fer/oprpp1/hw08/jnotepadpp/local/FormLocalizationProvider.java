package hr.fer.oprpp1.hw08.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * {@link LocalizationProvider} proxy.
 * 
 * @author Petra
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor.
	 * 
	 * @param parent localization provider
	 * @param frame  frame
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}

}
