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
	private ImageIcon bulletImage;
	private static PlayerBullet bullet;
	boolean shoot = true;

	public Ship() {
		posX = 400;
		posY = 300;
		ImageIcon img = new ImageIcon(this.getClass().getResource("/airwar/images/1444842379440.png"));
		imagen = img.getImage();
		bulletImage = new ImageIcon(this.getClass().getResource("/airwar/images/PlayerBullet.png"));
		bullet = new PlayerBullet(100, 10);	
		bullet.setImage(bulletImage);
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
	
	public void  shoot() {
		System.out.println("HOla");
		System.out.println(bullet == null);
		while (shoot) {
			bullet.move();
		}
		//bullet = null;
	}

	public void paintImage(Graphics g, int x, int y) {		
		g.drawImage(bullet.getImage(), x, y, 15, 15, null);
		g.drawImage(imagen, x - 25, y - 30, 64, 128, null);
	}
}