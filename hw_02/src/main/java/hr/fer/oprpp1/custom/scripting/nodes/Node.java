package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all nodes
 * 
 * @author Petra
 *
 */
public class Node {

	/**
	 * Collection of nodes
	 */
	private ArrayIndexedCollection col;

	/**
	 * Node value
	 */
	Object value;

	/**
	 * Creates an instance of this class.
	 */
	public Node() {
		col = new ArrayIndexedCollection();
	}

	/**
	 * Creates an instance of this class using the given object.
	 */
	public Node(Object value) {
		col = new ArrayIndexedCollection();
		this.value = value;
	}

	/**
	 * Adds given child to an internally managed collection of children.
	 * 
	 * @param child the node that will be added to the collection
	 */
	public void addChildNode(Node child) {
		col.add(child);
	}

	/**
	 * Returns the number of direct children.
	 * 
	 * @return the number of direct children.
	 */
	public int numberOfChildren() {
		return col.size();
	}

	/**
	 * Returns the child of this node.
	 * 
	 * @param index index of the child that will be returned
	 * @return child node
	 */
	public Node getChild(int index) {
		if (index < 0 || index > this.col.size() - 1) {
			throw new IndexOutOfBoundsException("The given index is out of bounds of this collection.");
		}
		return (Node) col.get(index);
	}

	/**
	 * Return the value of this node.
	 * 
	 * @return the value of the node
	 */
	public Object getValue() {
		return this.value;
	}

	/**
	 * Checks if two nodes are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		Node other = (Node) obj;
		if (this.numberOfChildren() != other.numberOfChildren()) {
			return false;
		}
		for (int i = 0; i < this.numberOfChildren(); i++) {
			if (!this.getChild(i).getValue().toString().strip()
					.equals(other.getChild(i).getValue().toString().strip())) {
				System.out.println(this.getChild(i).getValue());
				System.out.println(other.getChild(i).getValue());
				return false;
			}
		}
		return true;
	}

	/*
	 * Returns this object in string form
	 */
	@Override
	public String toString() {
		StringBuilder original = new StringBuilder();

		if (this.getValue() != null)
			original.append(this.getValue());
		for (int i = 0; i < this.numberOfChildren(); i++) {
			original.append(this.getChild(i).toString());
		}
		if (this.getClass() == ForLoopNode.class) {
			original.append("{$END$} ");
		}

		return original.toString();
	}

}
