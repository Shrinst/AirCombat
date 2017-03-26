package airwar2.datastructures;

import java.awt.Graphics;
import java.util.Random;

import airwar2.enemies.NodeJet;
import airwar2.graphics.Game;

public class EnemyQueue {
	
	private NodeJet head;
	private NodeJet last;
	private int size;
	public Random rnd = new Random();
	private EnemyList list;
	private Game game;

	public EnemyQueue(Game game) {
		this.game = game;
		list = new EnemyList(game);
		head = last = null;
		size = 0;

	}
	
	public boolean isEmpty() {
		return head == null;
	}
	
	public void insert() {
		int valueX = rnd.nextInt(800 - 32);// posiciones
		int valueY = rnd.nextInt(30);// oleadas;
		NodeJet newNode = new NodeJet(valueX, valueY, 0, game);
		if (isEmpty()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}
	
	public void delete() {
		list.insert();
		head = head.getNext();
		size--;
	}

	public void action(Graphics g) {
		list.action(g);
	}
	
	public int getSize() {
		return size;
	}

}
