package airwar.structures;

import java.awt.Graphics;

import airwar.player.NodeBullet;


public class PlayerBullets {

	private NodeBullet head;
	private NodeBullet last;
	private int size;	
	public NodeBullet tempBullet;

	public PlayerBullets() {
		head = last = null;
		size = 0;

	}

	public boolean isEmphy() {
		return head == null;
	}

	
	public void moveBullets() {
		int i = 0;
		for (NodeBullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;
			//System.out.println(b.size() + "Hola");
			
			if (tempBullet.getY() < 0) {
				delete(i);
			}
			
			tempBullet.tick();
			i++;
		}
	}
	
	public void render(Graphics g) {
		for (NodeBullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux; 
			
			tempBullet.render(g);
		}
	}

	public void insert(NodeBullet node) {
		NodeBullet newNode = node;
		if (isEmphy()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}


	public void delete(int nodo) {
		NodeBullet temp1 = new NodeBullet(0, 0);
		NodeBullet temp2 = new NodeBullet(0, 0);
		temp1 = head;
		for (int i = 0; i < nodo; i++) {
			temp1 = temp1.getNext();
		}
		temp2 = temp1;
		temp1 = temp1.getNext();
		temp2.setNext(temp1);
		size--;
	}

	public int size() {
		return size;
	}
}
