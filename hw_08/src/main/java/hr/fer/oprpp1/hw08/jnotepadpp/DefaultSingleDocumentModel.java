package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Implementation of {@link SingleDocumentModel}.
 * 
 * @author Petra
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private Path path;

	private JTextArea editor;

	private List<SingleDocumentListener> listeners = new ArrayList<>();

	private boolean modified;

	private final BiConsumer<SingleDocumentListener, SingleDocumentModel> statusNotifier = (l, m) -> l
			.documentModifyStatusUpdated(m);

	private final BiConsumer<SingleDocumentListener, SingleDocumentModel> pathNotifier = (l, m) -> l
			.documentFilePathUpdated(m);

	/**
	 * Constructor.
	 * 
	 * @param filePath document path
	 * @param text     document content
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		this.path = filePath;
		this.editor = new JTextArea(text);

		this.editor.getDocument().addDocumentListener(new DocumentListener() {

			BiConsumer<SingleDocumentListener, SingleDocumentModel> notifier = (l, m) -> {
				modified = true;
				l.documentModifyStatusUpdated(m);
			};

			@Override
			public void removeUpdate(DocumentEvent e) {
				notifyListeners(notifier);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				notifyListeners(notifier);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				notifyListeners(notifier);
			}
		});

	}

	@Override
	public JTextArea getTextComponent() {
		return this.editor;
	}

	@Override
	public Path getFilePath() {
		return this.path;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "File path cannot be null.");

		this.path = path;
		notifyListeners(this.pathNotifier);
	}

	@Override
	public boolean isModified() {
		return this.modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		notifyListeners(this.statusNotifier);
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		this.listeners.remove(l);
	}

	/**
	 * Notifies listeners about a change.
	 * 
	 * @param notifier notifier
	 */
	private void notifyListeners(BiConsumer<SingleDocumentListener, SingleDocumentModel> notifier) {
		for (SingleDocumentListener l : listeners)
			notifier.accept(l, this);
	}

}
