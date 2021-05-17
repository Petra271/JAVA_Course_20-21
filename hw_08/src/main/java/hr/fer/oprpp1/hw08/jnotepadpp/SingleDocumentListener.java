package hr.fer.oprpp1.hw08.jnotepadpp;

/**
 * Represents {@link DefaultSingleDocumentModel} listener.
 * 
 * @author Petra
 *
 */
public interface SingleDocumentListener {

	/**
	 * Called when modify status of a model is updated.
	 * 
	 * @param model model whose modify status is updated.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Called when file path of a model is updated.
	 * 
	 * @param model model whose file path is updated.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);

}
