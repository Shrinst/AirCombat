package airwar2.datastructures;

import java.awt.Graphics;
import java.util.Random;

import airwar2.enemies.NodeJet;
import airwar2.graphics.Game;

public class EnemyList {

	private NodeJet head;
	private NodeJet last;
	private int size;
	private Random rnd = new Random();
	private Game game;

	public EnemyList(Game game) {
		this.game = game;
		head = last = null;
		size = 0;

	}

	public boolean isEmpty() {
		return head == null;
	}

	public void insert() {
		int valueX = rnd.nextInt(800 - 32);// posiciones
		int valueY = rnd.nextInt(20);
		NodeJet newNode = new NodeJet(valueX, 0, valueY, game);
		if (isEmpty()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}

	public void action(Graphics g, int targetX, int targetY) {
		NodeJet temp1 = new NodeJet(0, 0, 0, game);
		temp1 = head;
		for (int i = 0; i < size; i++) {
			temp1.render(g);
			temp1.tick(targetX, targetY);
			temp1.shoot();
			temp1 = temp1.getNext();
		}
	}

	public void delete(int nodo) {
		if (this.size > 0) {
			if (head == last && nodo == head.getPosY()) {
				head = last = null;
			} else if (nodo == head.getPosY()) {
				head = head.getNext();
			} else {
				NodeJet anterior, temporal;
				anterior=head;
				temporal=head.getNext();
				while(temporal!=null && temporal.getPosY()!=nodo){
					anterior=anterior.getNext();
					temporal=temporal.getNext();
				}
				if(temporal!=null){
					anterior.setNext(temporal.getNext());
					if(temporal==last){
						last=anterior;
					}
				}
			}
		}
		this.size--;
	}

	public int getSize() {
		return size;
	}

	public NodeJet getHead() {
		return this.head;
	}
}