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

	public void action(Graphics g) {
		NodeJet temp1 = new NodeJet(0, 0, 0, game);
		temp1 = head;
		for (int i = 0; i < size; i++) {
			temp1.render(g);
			temp1.tick();
			temp1 = temp1.getNext();
		}
	}

	public void delete(int nodo) {
		NodeJet temp1 = new NodeJet(0, 0, 0, game);
		NodeJet temp2 = new NodeJet(0, 0, 0, game);
		temp1 = head;
		for (int i = 0; i < nodo; i++) {
			temp1 = temp1.getNext();
		}
		temp2 = temp1;
		temp1 = temp1.getNext();
		temp2.setNext(temp1);
		size--;
	}

	public int getSize() {
		return size;
	}
}
