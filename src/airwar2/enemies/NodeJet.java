package airwar2.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.ImageIcon;

import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

public class NodeJet {
	
	private int posX;
	private int posY;
	private int posYF;
	private int armor;
	private int type;
	public Random rnd = new Random();
	
	private final BufferedImage img0;
	private final BufferedImage img1;
	private final BufferedImage img2;
	private final BufferedImage img3;
	private NodeJet next;
	private int pos;
	
	public NodeJet(int valueX, int valueY, int tipo, Game game) {
		setNext(null);
		this.posX = valueX;
		this.posY = 0;
		this.type=tipo;
		if(type>17){
			posYF = rnd.nextInt(500-64);
		}
		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
        this.img0 = spriteSheet.grabImage(2, 1, 32, 32);
        this.img1 = spriteSheet.grabImage(1, 1, 32, 32);
        this.img2 = spriteSheet.grabImage(4, 1, 32, 32);
        this.img3 = spriteSheet.grabImage(4, 1, 32, 32);
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
		if(type<18){
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
		}else{
			if(this.posY<posYF){
				this.cambiarPosY(true);
				this.cambiarPosY(true);
			}
		}
		
	}
	
	public void render(Graphics g) {
		if(type<9){
			armor=0;
			g.drawImage(img0, this.posX, this.posY, 32, 64, null);
		}
		if(type<13 && type>8){
			armor=1;
			g.drawImage(img1, this.posX, this.posY, 32, 64, null);
		}
		if(type==18){
			armor=2;
			g.drawImage(img2, this.posX, this.posY, 32, 64, null);
		}
		if(type==19){
			armor=3;
			g.drawImage(img3, this.posX, this.posY, 32, 64, null);
		}
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
