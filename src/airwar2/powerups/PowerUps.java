package airwar2.powerups;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

public class PowerUps {

	private PowerUps next;
	private int posValueX;
	private int posValueY;
	private int type;
	
	private Game game;
	private BufferedImage image;
	
	public PowerUps(int type) {
		this.type = type;
	}

	public PowerUps(int valueX, int valueY, int type, Game game) {
		next = null;
		this.posValueX = valueX;
		this.posValueY = valueY;
		this.type = type;
		this.game = game;
		
		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
		this.image = spriteSheet.grabImage(1, 1, 32, 32);
	}
	
	public void tick() {
		posValueY++;
	}
	
	public void render(Graphics g) {
		g.drawImage(image, this.posValueX, this.posValueY, null);
	}

	public PowerUps getNext() {
		return next;
	}

	public void setNext(PowerUps next) {
		this.next = next;
	}
	
	public int getType() {
		return this.type;
	} 
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getPosX() {
		return this.posValueX;
	}
	
	public int getPosY() {
		return this.posValueY;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(this.posValueX, this.posValueY, image.getWidth(null), image.getHeight(null));
	}
}
