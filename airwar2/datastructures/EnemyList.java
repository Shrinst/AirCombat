package airwar2.datastructures;

import java.awt.Graphics;

import airwar2.enemies.Enemies;
import airwar2.graphics.Game;

public class EnemyList {

	private Enemies head;
	private Enemies last;
	private int size;

	private Game game;

	/**
	 * Setup variables
	 * 
	 * @param game
	 *            reference to the game
	 */
	public EnemyList(Game game) {
		this.game = game;
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
	 * Add a node to the list
	 * 
	 * @param valueX
	 *            position x
	 * @param tipo
	 *            enemy type
	 */
	public void insert(int valueX, int tipo) {
		Enemies newNode = new Enemies(valueX, tipo, game);
		if (isEmpty()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}

	/**
	 * Move, draw and make the enemies shoot
	 * 
	 * @param g
	 *            graphic object
	 * @param targetX
	 *            x position
	 * @param targetY
	 *            y position
	 */
	public void action(Graphics g, int targetX, int targetY) {
		Enemies temp1 = new Enemies(0, 0, game);
		temp1 = head;
		for (int i = 0; i < size; i++) {
			if (temp1.getPosY() < 640) {
				temp1.render(g);
				temp1.tick(targetX, targetY);
				temp1.shoot();
			} else {
				this.delete(temp1.getPosY());
			}
			temp1 = temp1.getNext();
		}
	}

	/**
	 * Remove a node from the list
	 * 
	 * @param nodo
	 *            delete from the list
	 */
	public void delete(int nodo) {
		if (this.size > 0) {
			if (head == last && nodo == head.getPosY()) {
				head = last = null;
			} else if (nodo == head.getPosY()) {
				head = head.getNext();
			} else {
				Enemies anterior, temporal;
				anterior = head;
				temporal = head.getNext();
				while (temporal != null && temporal.getPosY() != nodo) {
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
	public Enemies getHead() {
		return this.head;
	}
}