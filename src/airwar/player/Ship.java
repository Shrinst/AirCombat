package airwar.player;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;


public class Ship {

	private static int posX;
	private static int posY;
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;

	private Image imagen;	
	boolean shoot = true;	

	public Ship() {
		posX = 400;
		posY = 300;		
		ImageIcon img = new ImageIcon(this.getClass().getResource("/airwar/images/1444842379440.png"));
		imagen = img.getImage();		
	}

	public void changePosX(boolean Flag, int amount) {
		if (Flag) {
			if (Ship.posX < (WIDTH - 40)) {
				Ship.posX += amount;
			}
		} else {
			if (Ship.posX > 0) {
				Ship.posX -= amount;
			}
		}
	}

	public void changePosY(boolean Flag, int amount) {
		if (Flag) {
			if (Ship.posY < (HEIGHT - 90)) {
				Ship.posY += amount;
			}
		} else {
			if (Ship.posY > 0) {
				Ship.posY -= amount;
			}
		}
	}

	public static int getPositionX() {
		return Ship.posX;
	}

	public static int getPositionY() {
		return Ship.posY;
	}	

	public void paintImage(Graphics g, int x, int y) {
		g.drawImage(imagen, x, y, 64, 128, null);		
	}
}
