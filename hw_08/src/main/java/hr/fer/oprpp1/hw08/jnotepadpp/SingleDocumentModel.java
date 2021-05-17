package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Represents a model of single document, having information about file path
 * from which document was loaded (can be null for new document), document
 * modification status and reference to Swing component which is used for
 * editing (each document has its own editor component).
 * 
 * @author Petra
 *
 */
public interface SingleDocumentModel {

	/**
	 * Returns text area of a document.
	 * 
	 * @return text area of a document
	 */
	JTextArea getTextComponent();

	/**
	 * Returns document path.
	 * 
	 * @return document path
	 */
	Path getFilePath();

	/**
	 * Sets document path.
	 * 
	 * @param path document path
	 */
	void setFilePath(Path path);

	/**
	 * Checks if document is modified.
	 * 
	 * @return <code>true</code> is document is modified and <code>false</code>
	 *         otherwise
	 */
	boolean isModified();

	/**
	 * Sets modified status.
	 * 
	 * @param modified modified status
	 */
	void setModified(boolean modified);

	/**
	 * Adds a single document listener.
	 * 
	 * @param l added listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes single document listener.
	 * 
	 * @param l removed listener
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);

}
