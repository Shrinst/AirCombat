package airwar2.datastructures;

import java.awt.Graphics;
import java.util.LinkedList;

import airwar2.player.Bullet;

public class PlayerBullets {	

	private LinkedList<Bullet> bullets = new LinkedList<Bullet>();
	
	private Bullet tempBullet;       
    
    public PlayerBullets() {        
    }
    
    public void tick() {
        for (int i = 0; i < bullets.size(); i++) {
            tempBullet = bullets.get(i);
            
            if (tempBullet.getY() < 0) {
                removeBullet(tempBullet);
            }

            tempBullet.tick();
        }
    }
    
    public void render(Graphics g) {
        for (int i = 0; i < bullets.size(); i++) {
            tempBullet = bullets.get(i);

            tempBullet.render(g);
        }
    }
    
    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }
    
    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
        bullet = null;
    }
	    
}
