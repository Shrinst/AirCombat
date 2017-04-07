package airwar2.datastructures;

import java.awt.Graphics;
import airwar2.player.Bullet;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class PlayerBullets {

	private Bullet head;
	private Bullet last;
	private int size;
	public Bullet tempBullet;

	/**
	 * Setup variables
	 */
	public PlayerBullets() {
		head = last = null;
		size = 0;
	}

	/**
	 * Check if the list is empty
	 * 
	 * @return if the list is empty
	 */
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * Move all the bullets in the list
	 */
	public void tick() {
		for (Bullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;
			tempBullet.tick();

			if (tempBullet.getY() < 0) {
				this.removeBullet(tempBullet);
			}
		}
	}

	/**
	 * Draw all the bullets in the list
	 * 
	 * @param g
	 *            the graphic object
	 */
	public void render(Graphics g) {
		for (Bullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;
			tempBullet.render(g);
		}
	}

	/**
	 * Add a node to the list
	 * 
	 * @param node
	 *            the new node
	 */
	public void addBullet(Bullet node) {
		Bullet newNode = node;
		if (isEmpty()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}

	/**
	 * Remove a node from the list
	 * 
	 * @param deleteNode
	 *            the node that will be erased
	 */
	public void removeBullet(Bullet deleteNode) {
		if (this.size > 0) {
			if (head == last && deleteNode.getY() == head.getY()) {
				head = last = null;
			} else if (deleteNode.getY() == head.getY()) {
				head = head.getNext();
			} else {
				Bullet anterior, temporal;
				anterior = head;
				temporal = head.getNext();
				while (temporal != null && temporal.getY() != deleteNode.getY()) {
					anterior = anterior.getNext();
					temporal = temporal.getNext();
				}
				if (temporal != null) {
					anterior.setNext(temporal.getNext());
					if (temporal == last) {
						last = anterior;
					}
				}
			}
		}
		this.size--;
	}

	/**
	 * Return the list size
	 * 
	 * @return the list size
	 */
	public int getSize() {
		return size;
	}
}
