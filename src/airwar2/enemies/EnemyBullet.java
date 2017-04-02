package airwar2.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

public class EnemyBullet {
	
	private double x;
    private double y;
    
    private BufferedImage image;
    
    private EnemyBullet next;
    
    public EnemyBullet(double x, double y, Game game) {
    	this.next = null;
        this.x = x;
        this.y = y;
        
        SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
        this.image = spriteSheet.grabImage(5, 1, 32, 32);
    }   
    
    public void tick() {
        this.y += 10;
    }
    
    public void render(Graphics g) {
        g.drawImage(image, (int) x, (int) y, null);
    }
    
    public double getY() {
        return this.y;
    }
    
    public EnemyBullet getNext() {
		return this.next;
	}
    
    public void setNext(EnemyBullet next) {
    	this.next = next;
    }

}
