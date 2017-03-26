package airwar2.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

public class NodeJet {
	
	private int posX;
	private int posY;
	public Random rnd = new Random();

	private BufferedImage image;
	private NodeJet next;
	private int pos;
	
	public NodeJet(int valueX, int valueY, int tipo, Game game) {
		setNext(null);
		this.posX = valueX;
		this.posY = -256 * valueY;
		
		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
        this.image = spriteSheet.grabImage(2, 1, 32, 32);
	}
	
	public NodeJet getNext() {
		return next;
	}

	public int getpos() {
		return pos;
	}

	public void setNext(NodeJet next) {
		this.next = next;
	}
	
	public void tick() {
		int x = 2;
		this.cambiarPosY(true);
		this.cambiarPosY(true);
		int value = rnd.nextInt(x);
		if (value < (x / 2)) {
			if (this.getPosX() < 800 - 32) {
				this.cambiarPosX(true);

			}
		} else {
			if (this.getPosX() > 0) {
				this.cambiarPosX(false);
			}
		}
	}
	
	public void render(Graphics g) {
		g.drawImage(image, this.posX, this.posY, 32, 64, null);
	}
	

	private void cambiarPosY(boolean Flag) {
		if (Flag == true) {
			this.posY++;

		} else {
			this.posY--;
		}
	}

	private void cambiarPosX(boolean Flag) {
		if (Flag == true) {
			this.posX++;

		} else {
			this.posX--;
		}
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosX(int x) {
		this.posX = x;
	}

	public void setPosY(int y) {
		this.posY = y;
	}

}
