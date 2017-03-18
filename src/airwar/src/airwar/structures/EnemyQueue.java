package airwar.structures;

import java.awt.Graphics;
import java.util.Random;

import airwar.enemies.NodoJet;

public class EnemyQueue {

	private NodoJet head;
	private NodoJet last;
	private int size;
	public Random rnd = new Random();
	private EnemyList list = new EnemyList();

	public EnemyQueue() {
		head = last = null;
		size = 0;

	}

	public boolean isEmphy() {
		return head == null;
	}

	public void insert() {
		int valueX = rnd.nextInt(800 - 32);// posiciones
		int valueY = rnd.nextInt(30);// oleadas;
		NodoJet newNode = new NodoJet(valueX, valueY, 0);
		if (isEmphy()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}

	public void delete() {
		list.insert();
		head = head.next;
		size--;
	}

	public void action(Graphics g) {
		list.action(g);
	}
	
	public int size() {
		return size;
	}

}