package airwar2.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import airwar2.datastructures.EnemyList;
import airwar2.datastructures.PlayerBullets;
import airwar2.datastructures.PowerUpsList;
import airwar2.enemies.Enemies;
import airwar2.graphics.Game;
import airwar2.powerups.PowerUps;
import airwar2.spritesheet.SpriteSheet;

/**
 * 
 * @author Emanuel, Vinicio
 * @version 1.2.0
 *
 */
public class Bullet {
	private int x;
	private int y;
	private int gameScore;
	private int type;

	private BufferedImage genericImage;
	private BufferedImage misilImage;
	private BufferedImage lazerImage;

	private Bullet next;
	private Game game;
	private Random rnd;
	private Rectangle playerBulletRectangle;
	private EnemyList enemyList;
	private PlayerBullets playerBullet;
	private PowerUpsList powerUpsList;

	/**
	 * Set the variables
	 * 
	 * @param x
	 *            the position in the x axis
	 * @param y
	 *            the position in the y axis
	 * @param type
	 *            the bullet type
	 * @param game
	 *            the reference to the game
	 */
	public Bullet(int x, int y, int type, Game game) {
		this.next = null;
		this.x = x + 2;
		this.y = y;
		this.type = type;
		this.game = game;
		this.powerUpsList = game.getPowerUpsList();
		this.gameScore = this.game.getScore();
		this.rnd = new Random();

		this.enemyList = this.game.getEnemyQueue().getEnemyList();
		this.playerBullet = this.game.getPlayerBullets();

		SpriteSheet spriteSheet = new SpriteSheet(this.game.getSpriteSheet());
		this.genericImage = spriteSheet.grabImage(2, 1, 32, 32);
		this.misilImage = spriteSheet.grabImage(3, 1, 32, 32);
		this.lazerImage = spriteSheet.grabImage(4, 1, 32, 32);

	}

	/**
	 * Will move the bullet, and check for collisions
	 */
	public void tick() {
		this.y -= 10;
		playerBulletRectangle = new Rectangle((int) x, (int) y, 32, 32);
		Enemies temp = enemyList.getHead();
		for (int i = 0; i < enemyList.getSize(); i++) {
			if (playerBulletRectangle.intersects(temp.getBounds())) {
				if (this.y <= temp.getPosY()) {
					if (temp.getArmor() - this.type <= 0) {
						enemyList.delete(temp.getPosY());
						game.setScore(gameScore += 10);
						if (Math.random() <= 0.30) {
							powerUpsList
									.addPowerUp(new PowerUps(temp.getPosX(), temp.getPosY(), rnd.nextInt(3) + 1, game));
						}
						playerBullet.removeBullet(this);
					} else {
						temp.setArmor(this.type);
						playerBullet.removeBullet(this);
					}
				}

			}
			temp = temp.getNext();
		}
	}

	/**
	 * Will draw a bullet image depends on the type
	 * 
	 * @param g
	 *            the graphic object
	 */
	public void render(Graphics g) {
		if (type == 0) {
			g.drawImage(genericImage, x, y, 24, 24, null);
		}
		if (type == 1) {
			g.drawImage(misilImage, x, y, 24, 24, null);
		}
		if (type == 2) {
			g.drawImage(lazerImage, x, y, 24, 24, null);
		}
	}

	/**
	 * Returns the bullet position
	 * 
	 * @return the bullet position
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Set the bullet's type
	 */
	public void setType() {
		type = 0;
	}

	/**
	 * Returns the next bullet of the list
	 * 
	 * @return the next bullet of the list
	 */
	public Bullet getNext() {
		return this.next;
	}

	/**
	 * Set the next bullet of the list
	 * 
	 * @param next
	 *            the next bullet of the list
	 */
	public void setNext(Bullet next) {
		this.next = next;
	}
}
