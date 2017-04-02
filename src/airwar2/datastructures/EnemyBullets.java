package airwar2.datastructures;

import java.awt.Graphics;

import airwar2.enemies.EnemyBullet;

public class EnemyBullets {
	
	private EnemyBullet head;
	private EnemyBullet last;
	private int size;	
	public EnemyBullet tempBullet;	    
    
    public EnemyBullets() {        
    	head = last = null;
		size = 0;
    }
    
    public boolean isEmpty() {
		return head == null;
	}
    
    public void tick() {		
		for (EnemyBullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;			
					
			tempBullet.tick();				
		}
	}   
    
    public void render(Graphics g) {
		for (EnemyBullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux; 
			tempBullet.render(g);
		}
	} 
        
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
    
	public void removeBullet(EnemyBullet deleteNode) {
		for (EnemyBullet aux = this.head; aux != null; aux.setNext(aux.getNext())) {
			if (aux.getNext() == deleteNode) {
				aux.setNext(deleteNode.getNext());				
			}
		}		
		size--;
	}
	
	public int getSize() {
		return size;
	}	    

}
