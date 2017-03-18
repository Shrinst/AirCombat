package airwar.structures;

import java.awt.Graphics;
import java.util.Random;
import airwar.enemies.*;

public class EnemyQueue {

	private NodoJet head;
	private NodoJet last;
	private int size;
	public Random rnd = new Random();

	public EnemyQueue() {
		head = last = null;
		size = 0;

	}

	public boolean isEmphy() {
		return head == null;
	}

	public void insert() {
		int valueX = rnd.nextInt(800-32);//posiciones
		int valueY = rnd.nextInt(30);//oleadas;
		NodoJet newNode = new NodoJet(valueX, valueY,0);
		if (isEmphy()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}

	public void action(Graphics g) {
		NodoJet temp1 = new NodoJet(0,0,0);
		temp1 = head;
		for (int i = 0; i < size; i++) {
			temp1.paintImage(g);
			temp1.move();
			temp1 = temp1.next;
		}
	}

	public void delete(int nodo) {
		NodoJet temp1 = new NodoJet(0,0,0);
		NodoJet temp2 = new NodoJet(0,0,0);
		temp1 = head;
		for (int i = 0; i < nodo; i++) {
			temp1 = temp1.next;
		}
		temp2 = temp1;
		temp1 = temp1.next;
		temp2.setNext(temp1);
		size--;
	}

	public int size() {
		return size;
	}
}
