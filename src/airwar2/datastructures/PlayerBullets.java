package airwar2.datastructures;

import java.awt.Graphics;

import airwar2.enemies.NodeJet;
import airwar2.player.Bullet;

public class PlayerBullets {
	
	private Bullet head;
	private Bullet last;
	private int size;	
	public Bullet tempBullet;	    
    
    public PlayerBullets() {        
    	head = last = null;
		size = 0;
    }
    
    public boolean isEmpty() {
		return head == null;
	}
    
    public void tick() {		
		for (Bullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux;			
			tempBullet.tick();
			if(tempBullet.getY()<0){
				this.removeBullet(tempBullet);
			}
			
			
		}
	}   
    
    public void render(Graphics g) {
		for (Bullet aux = this.head; aux != null; aux = aux.getNext()) {
			tempBullet = aux; 
			
			tempBullet.render(g);
		}
	} 
        
    public void addBullet(Bullet node) {
		Bullet newNode = node;
		if (isEmpty()) {
			head = newNode;
		} else {
			last.setNext(newNode);
		}
		last = newNode;
		size++;
	}       
    
	public void removeBullet(Bullet deleteNode) {
		if (this.size > 0) {
			if (head == last && deleteNode.getY() == head.getY()) {
				head = last = null;
			} else if (deleteNode.getY() == head.getY()) {
				head = head.getNext();
			} else {
				Bullet anterior, temporal;
				anterior=head;
				temporal=head.getNext();
				while(temporal!=null && temporal.getY()!=deleteNode.getY()){
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
}
