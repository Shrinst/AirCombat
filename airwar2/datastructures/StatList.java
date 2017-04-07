package airwar2.datastructures;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class StatList {

	private StatNode head, last;
	private int size;

	/**
	 * Setup variables
	 */
	public StatList() {
		this.head = this.last = null;
		this.size = 0;
	}

	/**
	 * Insert an object in the first position
	 * 
	 * @param message
	 *            the string of the node
	 */
	public void insertFirst(String message) {
		head = new StatNode(message, head);
		if (last == null) {
			last = head;
		}
		size++;
	}

	/**
	 * Return the head node
	 * 
	 * @return the head node
	 */
	public StatNode getHead() {
		return this.head;
	}

	/**
	 * Return the list size
	 * 
	 * @return the list size
	 */
	public int getSize() {
		return this.size;
	}
}
