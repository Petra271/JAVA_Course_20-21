package hr.fer.oprpp1.hw08.jnotepadpp;

import java.nio.file.Path;

/**
 * Represents a model capable of holding zero, one or more documents having a
 * concept of current document – the one which is shown to the user and on which
 * user works
 * 
 * @author Petra
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document.
	 * 
	 * @return new document model
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Returns current document.
	 * 
	 * @return current document model
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a document.
	 * 
	 * @param path path of document to be loaded
	 * @return loaded document model
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves a document.
	 * 
	 * @param model model of document to be saved
	 * @param path  path of saved document
	 */
	void saveDocument(SingleDocumentModel model, Path path);

	/**
	 * Closes a document.
	 * 
	 * @param model model of document to be closed
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds a multiple document listener.
	 * 
	 * @param l added listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes a multiple document listener.
	 * 
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Returns number of multiple document model documents.
	 * 
	 * @return number of multiple document model documents
	 */
	int getNumberOfDocuments();

	/**
	 * Returns document at the given index.
	 * 
	 * @param index document index
	 * @return document at the given index
	 */
	SingleDocumentModel getDocument(int index);

}
