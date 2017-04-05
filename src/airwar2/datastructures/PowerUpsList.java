package airwar2.datastructures;

import java.awt.Graphics;
import java.awt.Rectangle;

import airwar2.graphics.Game;
import airwar2.powerups.PowerUps;

public class PowerUpsList {

	private PowerUps head;
	private PowerUps last;
	private int size;
	public PowerUps tempBullet;
	private Game game;

	public PowerUpsList(Game game) {
		head = last = null;
		size = 0;
		this.game = game;
		//this.addPowerUp(new PowerUps(100, 100, 1, this.game));
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void tick() {
		for (PowerUps aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;

			tempBullet.tick();
		}
	}

	public void render(Graphics g) {
		for (PowerUps aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;
			tempBullet.render(g);
		}
	}

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

	public void removePowerUp(int deleteNode) {
		if (this.size > 0) {
			if (head == last && deleteNode == head.getPosY()) {
				head = last = null;
			} else if (deleteNode == head.getPosY()) {
				head = head.getNext();
			} else {
				PowerUps anterior, temporal;
				anterior = head;
				temporal = head.getNext();
				while (temporal != null && temporal.getPosY() != deleteNode) {
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

	public int getSize() {
		return size;
	}
	
	public PowerUps getHead() {
		return this.head;
	}
}
