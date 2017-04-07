package airwar2.datastructures;

import airwar2.powerups.PowerUps;

/**
 * 
 * @author David, Fabiola
 * @version 1.1.0
 *
 */
public class StackPowerUps {

	private PowerUps first;
	private int size;

	/**
	 * Setup variables
	 */
	public StackPowerUps() {

		first = null;
		size = 0;
	}

	/**
	 * Check if the stack is empty
	 * 
	 * @return if the stack is empty
	 */
	public boolean is_empty() {
		return first == null;
	}

	/**
	 * Add an object to the stack
	 * 
	 * @param PowerUp
	 *            the power object
	 * @param type
	 *            the type of power up
	 */
	public void push(PowerUps PowerUp, int type) {
		PowerUps node = new PowerUps(type);
		node.setNext(first);
		first = node;
		size++;
	}

	/**
	 * Return and erase the first object of the stack
	 * 
	 * @return first object of the stack
	 */
	public PowerUps pop() {
		PowerUps aux = first;
		first = first.getNext();
		size--;
		return aux;
	}

	/**
	 * Return the head
	 * 
	 * @return the head
	 */
	public PowerUps getFirst() {
		return first;
	}

	/**
	 * Return the stack size
	 * 
	 * @return the stack size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Clean all the stack
	 */
	public void clean() {
		while (!is_empty()) {
			pop();
		}
	}
}
