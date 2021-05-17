package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Represents {@link DefaultMultipleDocumentModel} listener.
 * 
 * @author Petra
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Called when current document is changed.
	 * 
	 * @param previousModel previous model
	 * @param currentModel  current model
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Called when new document is added.
	 * 
	 * @param model model of the new document
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Called when a document is removed.
	 * 
	 * @param model model of the removed document.
	 */
	void documentRemoved(SingleDocumentModel model);

}
