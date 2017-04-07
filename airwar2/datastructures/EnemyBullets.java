package airwar2.datastructures;

import java.awt.Graphics;

import airwar2.enemies.EnemyBullet;

/**
 * 
 * @author Emanuel, Vinicio
 *
 */
public class EnemyBullets {

	private EnemyBullet head;
	private EnemyBullet last;
	private int size;
	public EnemyBullet tempBullet;

	/**
	 * Setup variables
	 */
	public EnemyBullets() {
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
		for (EnemyBullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;

			tempBullet.tick();

			if (tempBullet.getY() > 640) {
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
		for (EnemyBullet aux = this.head; aux != null; aux = aux.getNext()) {
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
	public void addBullet(EnemyBullet node) {
		EnemyBullet newNode = node;
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
	public void removeBullet(EnemyBullet deleteNode) {
		if (this.size > 0) {
			if (head == last && deleteNode.getY() == head.getY()) {
				head = last = null;
			} else if (deleteNode.getY() == head.getY()) {
				head = head.getNext();
			} else {
				EnemyBullet anterior, temporal;
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

	/**
	 * Return the head
	 * 
	 * @return the head
	 */
	public EnemyBullet getHead() {
		return this.head;
	}
}
