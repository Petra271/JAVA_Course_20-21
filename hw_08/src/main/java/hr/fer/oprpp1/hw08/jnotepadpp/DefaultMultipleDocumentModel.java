package hr.fer.oprpp1.hw08.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

/**
 * Implementation of {@link MultipleDocumentModel}.
 * 
 * @author Petra
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	private List<SingleDocumentModel> singleDocumentModels = new ArrayList<>();

	SingleDocumentModel current;

	private List<MultipleDocumentListener> listeners = new ArrayList<>();

	private final BiConsumer<MultipleDocumentListener, SingleDocumentModel> notifyRemoved = (l, m) -> l
			.documentRemoved(m);

	private final BiConsumer<MultipleDocumentListener, SingleDocumentModel> notifyAdded = (l, m) -> l.documentAdded(m);

	/**
	 * Constructor.
	 */
	public DefaultMultipleDocumentModel() {

		addChangeListener(c -> {

			int index = getSelectedIndex();
			SingleDocumentModel previous = this.current;
			if (index == -1)
				index = 0;
			this.current = this.singleDocumentModels.get(index);
			notifyCurrentChanged(previous, current);
		});
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return this.singleDocumentModels.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		return addNewDocument(null, "");
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return this.current;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path cannot be null.");

		SingleDocumentModel model;

		List<Path> paths = singleDocumentModels.stream().map(m -> m.getFilePath()).collect(Collectors.toList());

		if (paths.contains(path)) {
			model = singleDocumentModels.stream().filter(m -> m.getFilePath().equals(path)).findAny().get();
			notifyCurrentChanged(this.current, model);
			this.current = model;
			return model;
		}

		String text = null;
		try {
			text = Files.readString(path);
		} catch (IOException e) {
			throw new RuntimeException("File cannot be read.");
		}

		return addNewDocument(path, text);
	}

	/**
	 * Adds a new document to multiple document model.
	 * 
	 * @param path new document path
	 * @param text new document text
	 * @return new document
	 */
	private SingleDocumentModel addNewDocument(Path path, String text) {

		SingleDocumentModel model = new DefaultSingleDocumentModel(path, text);
		singleDocumentModels.add(model);
		notifyListeners(notifyAdded, model);

		notifyCurrentChanged(this.current, model);
		this.current = model;

		model.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				changeIcon(model);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = singleDocumentModels.indexOf(model);
				setTitleAt(index, model.getFilePath().getFileName().toString());
				setToolTipTextAt(index, model.getFilePath().toString());
			}
		});

		addTab(model);
		return model;

	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		for (SingleDocumentModel doc : singleDocumentModels) {

			if (doc.getFilePath() != null && doc.getFilePath().equals(newPath) && !doc.equals(model))
				throw new RuntimeException("File is already opened.");
		}

		if (newPath == null)
			newPath = model.getFilePath();
		else
			current.setFilePath(newPath);

		String text = model.getTextComponent().getText();

		try {
			Files.writeString(newPath, text, StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new RuntimeException("An error occurred while saving the file.");
		}

		current.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {

		if (singleDocumentModels.size() == 1)
			createNewDocument();

		int index = singleDocumentModels.indexOf(model) - 1;
		if (index < 0)
			index = 0;

		removeTabAt(singleDocumentModels.indexOf(model));
		setSelectedIndex(index);
		singleDocumentModels.remove(model);
		current = singleDocumentModels.get(index);
		notifyListeners(notifyRemoved, model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		this.listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return this.singleDocumentModels.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return this.singleDocumentModels.get(index);
	}

	/**
	 * Notifies listeners that current listener is changed.
	 * 
	 * @param oldDoc previous document
	 * @param newDoc new document
	 */
	private void notifyCurrentChanged(SingleDocumentModel oldDoc, SingleDocumentModel newDoc) {
		for (MultipleDocumentListener l : listeners)
			l.currentDocumentChanged(oldDoc, newDoc);
	}

	/**
	 * Notifies listeners that something has happened.
	 * 
	 * @param notifier notifier
	 * @param model    document
	 */
	private void notifyListeners(BiConsumer<MultipleDocumentListener, SingleDocumentModel> notifier,
			SingleDocumentModel model) {
		for (MultipleDocumentListener l : listeners)
			notifier.accept(l, model);
	}

	/**
	 * Changes tab icon
	 * 
	 * @param model model whose tab icon is changed
	 */
	private void changeIcon(SingleDocumentModel model) {
		if (model.isModified())
			setIconAt(singleDocumentModels.indexOf(model), getIcon("red.png"));
		else
			setIconAt(singleDocumentModels.indexOf(model), getIcon("green.png"));
	}

	/**
	 * Gets icon from resources.
	 * 
	 * @param type icon name
	 * @return new image icon
	 */
	private ImageIcon getIcon(String type) {
		InputStream input = JNotepadPP.class.getResourceAsStream("icons/" + type);

		if (input == null)
			throw new RuntimeException("Image with the given name does not exist.");

		byte[] bytes = null;
		try {
			bytes = input.readAllBytes();
			input.close();
		} catch (IOException e) {
			System.err.println("An error occurred while trying to get the icon image.");
		}

		Image image = new ImageIcon(bytes).getImage().getScaledInstance(10, 10, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(image);

		return icon;
	}

	/**
	 * Adds a new tab to tabbed pane.
	 * 
	 * @param model document for which a tab is added.
	 */
	private void addTab(SingleDocumentModel model) {

		JPanel panel = new JPanel(new BorderLayout());
		JTextArea area = model.getTextComponent();

		panel.add(new JScrollPane(area), BorderLayout.CENTER);

		String name = "untitled";
		String path = "";
		if (model.getFilePath() != null) {
			name = model.getFilePath().getFileName().toString();
			path = model.getFilePath().toAbsolutePath().toString();
		}

		addTab(name, getIcon("green.png"), panel, path);
		setSelectedComponent(panel);

	}

}
