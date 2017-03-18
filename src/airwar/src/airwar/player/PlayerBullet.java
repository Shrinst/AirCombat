package airwar.player;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class PlayerBullet {
	
	private int speed;
	private int damage;
	private static int posY;
	private ImageIcon image;
	
	public PlayerBullet() {
		PlayerBullet.posY = Ship.getPositionY();			
	}
	
	public PlayerBullet(int speed, int damage) {
		this.speed = speed;
		this.damage = damage;
	}
	
	public void move() {
		PlayerBullet.posY -= this.speed;
	}	
	
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	public Image getImage() {
		return this.image.getImage();
	}
	
	public void setPosY(int posY) {
		PlayerBullet.posY = posY;
	}
	
	public static int getPosY() {		
		return posY;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return this.speed;
	}	
	
	public void setDamage(int damage) {
		this.damage = damage;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void paintImage(Graphics g, int x, int y) {
		g.drawImage(image.getImage(), x, y, null);
	}
}
