package airwar2.datastructures;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class StatNode {

	private String stat;
	private StatNode next;

	/**
	 * Setup variables
	 * 
	 * @param stat
	 *            the string that that node will have
	 */
	public StatNode(String stat) {
		this.stat = stat;
		this.next = null;
	}

	/**
	 * Setup variables
	 * 
	 * @param stat
	 *            the string that that node will have
	 * @param next
	 *            the next object in the list
	 */
	public StatNode(String stat, StatNode next) {
		this.stat = stat;
		this.next = next;
	}

	/**
	 * Return the string
	 * 
	 * @return the string
	 */
	public String getStat() {
		return this.stat;
	}

	/**
	 * Return the next object in the list
	 * 
	 * @return the next object in the list
	 */
	public StatNode getNext() {
		return this.next;
	}
}
