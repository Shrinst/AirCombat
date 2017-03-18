package airwar.player;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class PlayerBullet {
	
	private int speed;
	private int damage;
	private int posY;
	private ImageIcon image;
	
	public PlayerBullet(int damage, int speed) {
		this.posY = Ship.getPositionY();
		this.speed = speed;
		this.damage = damage;		
	}
	
	public void move() {
		this.posY += this.speed;
	}	
	
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	
	public Image getImage() {
		return this.image.getImage();
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public int getDamage() {
		return this.damage;
	}
	
	public void paintImage(Graphics g, int x, int y) {
		g.drawImage(image.getImage(), x, y, null);
	}
}