package airwar2.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.datastructures.EnemyList;
import airwar2.datastructures.PlayerBullets;
import airwar2.enemies.NodeJet;
import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

public class Bullet {
	
	private double x;
    private double y;
    
    private BufferedImage image;
    
    private Bullet next;
    
    private PlayerBullets playerBullet; 
    private Rectangle playerBulletRectangle;
    private EnemyList enemyList;
    
    public Bullet(double x, double y, Game game) {
    	this.next = null;
        this.x = x;
        this.y = y;      
        
        this.enemyList = game.getEnemyQueue().getEnemyList();
        this.playerBullet = game.getPlayerBullets();
        SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
        this.image = spriteSheet.grabImage(2, 1, 32, 32);     
        
        
    }   
    
    public void tick() {
        this.y -= 10;  
        playerBulletRectangle = new Rectangle((int) x+8, (int) y-4, 16, 16);
        NodeJet temp = enemyList.getHead();
        for (int i = 0; i < enemyList.getSize(); i++) {
        	if (playerBulletRectangle.intersects(temp.getBounds())){
        		if(this.y <= temp.getPosY()){
        			enemyList.delete(temp.getPosY());
        			playerBullet.removeBullet(this);
        			
        		}
        	}
        	temp = temp.getNext();
        }      
    }
    
    public void render(Graphics g) {
        g.drawImage(image, (int) x + 8, (int) y - 4, 16, 16, null);
        g.setColor(Color.red);
    }
    
    public double getY() {
        return this.y;
    }
    
    public Bullet getNext() {
		return this.next;
	}
    
    public void setNext(Bullet next) {
    	this.next = next;
    }
}
