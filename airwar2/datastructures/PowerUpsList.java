package airwar2.datastructures;

import java.awt.Graphics;

import airwar2.graphics.Game;
import airwar2.powerups.PowerUps;

/**
 * 
 * @author Emanuel, David
 *
 */
public class PowerUpsList {

	private PowerUps head;
	private PowerUps last;
	private int size;
	public PowerUps tempBullet;

	/**
	 * Setup variables
	 * 
	 * @param game
	 *            reference to the game
	 */
	public PowerUpsList(Game game) {
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
	 * Move all the power ups in the list
	 */
	public void tick() {
		for (PowerUps aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;
			tempBullet.tick();

			if (tempBullet.getPosY() > 640) {
				this.removePowerUp(tempBullet);
			}
		}
	}

	/**
	 * Draw all the power ups in the list
	 * 
	 * @param g
	 *            the graphic object
	 */
	public void render(Graphics g) {
		for (PowerUps aux = this.head; aux != null; aux = aux.getNext()) {
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
	public void addPowerUp(PowerUps node) {
		PowerUps newNode = node;
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
	public void removePowerUp(PowerUps deleteNode) {
		if (this.size > 0) {
			if (head == last && deleteNode.getPosY() == head.getPosY()) {
				head = last = null;
			} else if (deleteNode.getPosY() == head.getPosY()) {
				head = head.getNext();
			} else {
				PowerUps anterior, temporal;
				anterior = head;
				temporal = head.getNext();
				while (temporal != null && temporal.getPosY() != deleteNode.getPosY()) {
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

	/**
	 * Return the head
	 * 
	 * @return the head
	 */
	public PowerUps getHead() {
		return this.head;
	}
}
