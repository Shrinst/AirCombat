package airwar.player;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

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

	private ArrayList<PlayerBullet> bullets = new ArrayList<PlayerBullet>();

	public Ship() {
		posX = 400;
		posY = 300;
		ImageIcon img = new ImageIcon(this.getClass().getResource("/airwar/images/1444842379440.png"));
		imagen = img.getImage();
		bulletImage = new ImageIcon(this.getClass().getResource("/airwar/images/PlayerBullet.png"));
		bullet = new PlayerBullet();
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

	public PlayerBullet ganerateBullet() {
		return bullet;
	}

	public void shoot() {
		bullets.add(new PlayerBullet(10, 100));		
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (PlayerBullet.getPosY() < HEIGHT) {
					for (int i = 0; i < bullets.size(); i++) {
						PlayerBullet bullet = bullets.get(i);
						bullet.move();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {						
							e.printStackTrace();
						}
					}
				}
			}
		}).start();		
	}

	public void paintImage(Graphics g, int x, int y, boolean flag) {
		if (flag) {
			g.drawImage(bullet.getImage(), x + 25, PlayerBullet.getPosY(), 15, 15, null);
			g.drawImage(imagen, x, y, 64, 128, null);
		} else {
			g.drawImage(bullet.getImage(), x + 25, y + 30, 15, 15, null);
			g.drawImage(imagen, x, y, 64, 128, null);
		}
		
	}
}
