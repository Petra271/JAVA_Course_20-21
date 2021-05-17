package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Implementation of {@link ListModel} used for showing prime numbers one by one.
 * 
 * @author Petra
 *
 */
public class PrimListModel implements ListModel<Integer> {

	private List<Integer> elements = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();
	
	public PrimListModel() {
		this.elements.add(1);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * Adds a new prime numbers and notifies all listeners that an interval has been added.
	 * 
	 * @param element added value
	 */
	public void next(Integer element) {
		int pos = elements.size();
		elements.add(element);

		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

}