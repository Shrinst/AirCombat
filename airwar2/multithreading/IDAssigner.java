package airwar2.multithreading;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class IDAssigner {

	private int baseID;

	/**
	 * Sets the ID
	 * 
	 * @param baseID
	 *            the ID
	 */
	public IDAssigner(int baseID) {
		this.baseID = baseID;
	}

	/**
	 * Increments the ID
	 * 
	 * @return the new ID
	 */
	public int next() {
		return baseID++;
	}

	/**
	 * Returns the ID
	 * 
	 * @return the ID
	 */
	public int getCurrentID() {
		return baseID;
	}

}
