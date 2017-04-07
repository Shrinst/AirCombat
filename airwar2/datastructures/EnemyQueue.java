package airwar2.datastructures;

import java.awt.Graphics;
import java.util.Random;

import airwar2.enemies.Enemies;
import airwar2.graphics.Game;

public class EnemyQueue {

	private Enemies head;
	private Enemies last;
	private int size;
	public Random rnd = new Random();
	private EnemyList list;
	private Game game;

	/**
	 * Setup variables
	 * 
	 * @param game
	 *            reference to the game
	 */
	public EnemyQueue(Game game) {
		this.game = game;
		list = new EnemyList(game);
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
	 * Insert a node to the queue
	 */
	public void insert() {
		if (size < 49) {
			int valueX = rnd.nextInt(20) * 32;// posiciones X
			int type = rnd.nextInt(21);// tipo del enemigo
			Enemies newNode = new Enemies(valueX, type, game);
			if (isEmpty()) {
				head = newNode;
			} else {
				last.setNext(newNode);
			}
			last = newNode;
			size++;
		} else {
			Enemies newNode = new Enemies(100, 21, game);
			last.setNext(newNode);
			last = newNode;
			size++;
		}

	}

	/**
	 * Delete a node from the queue
	 */
	public void delete() {
		if (size == 1) {
			if (list.getSize() == 0) {
				list.insert(head.getPosX(), head.getType());
				head = head.getNext();
				size--;
			}
		} else {
			list.insert(head.getPosX(), head.getType());
			head = head.getNext();
			size--;
		}
	}

	/**
	 * Draw all the enemies in the queue
	 * 
	 * @param g
	 * @param targetX
	 * @param targetY
	 */
	public void action(Graphics g, int targetX, int targetY) {
		list.action(g, targetX, targetY);
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
	 * Return the list of enemies
	 * 
	 * @return the list of enemies
	 */
	public EnemyList getEnemyList() {
		return this.list;
	}
}
