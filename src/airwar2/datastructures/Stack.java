package airwar2.datastructures;

import airwar2.powerups.PowerUps;

public class Stack {

	private PowerUps first;
	private int size;

	public Stack() {

		first = null;
		size = 0;
	}

	public boolean is_empty() {
		return first == null;
	}

	public void push(PowerUps powerUp, int type) {
		PowerUps node = new PowerUps(type);
		node.setNext(first);
		first = node;
		size++;
	}

	public PowerUps pop() {
		PowerUps aux = first;
		first = first.getNext();
		size--;
		return aux;
	}

	public PowerUps getFirst() {
		return first;
	}

	public int getSize() {
		return size;
	}

	public void clean() {
		while (!is_empty()) {
			pop();
		}
	}
}
