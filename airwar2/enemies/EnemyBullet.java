package airwar2.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.datastructures.EnemyBullets;
import airwar2.graphics.Game;
import airwar2.player.Player;
import airwar2.spritesheet.SpriteSheet;

/**
 * 
 * @author Vinicio
 * @version 1.1.0
 *
 */
public class EnemyBullet {

	private int x;
	private int y;

	private BufferedImage image;
	private BufferedImage image1;

	private EnemyBullet next;
	private int type;
	private Game game;
	private int xF;
	private int yF;
	private boolean flag1;
	private boolean flag2;
	private EnemyBullets enemyBullets;
	private Rectangle playerRectagle;
	private Player player;

	/***
	 * Setup the variables
	 * 
	 * @param x
	 *            the x position
	 * @param y
	 *            the y position
	 * @param type
	 *            the bullet type
	 * @param game
	 *            reference to he game
	 */
	public EnemyBullet(int x, int y, int type, Game game) {
		this.next = null;
		this.x = x;
		this.y = y;
		this.type = type;
		this.game = game;
		this.enemyBullets = this.game.getEnemyBullets();
		this.player = this.game.getPlayer();

		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
		this.image = spriteSheet.grabImage(2, 1, 32, 32);
		this.image1 = spriteSheet.grabImage(6, 2, 32, 32);
		if (type == 4 || type == 3) {
			xF = game.getPlayer().getX();
			yF = game.getPlayer().getY();
			if (xF < x) {
				flag1 = true;
			}
			if (xF > x) {
				flag1 = false;
			}
			if (yF > y) {
				flag2 = true;
			}
			if (yF < y) {
				flag2 = false;
			}
		}
	}

	/**
	 * Move the bullets, check for collision
	 */
	public void tick() {
		playerRectagle = game.getPlayer().getBounds();
		if (this.type == 0 || this.type == 1) {
			this.y += 10;
		}
		if (this.type == 3 || this.type == 4) {
			int value = 3;
			if (flag2) {
				if (yF > y) {
					this.y += value;
				}
			} else {
				if (yF < y) {
					this.y -= value;
				}
			}

			if (flag1) {
				if (xF < x) {
					this.x -= value;
				}
			} else {
				if (xF > x) {
					this.x += value;
				}
			}
			if (flag2 == true && flag1 == true) {
				if (yF <= y && xF >= x) {
					this.y = 650;
				}
			}
			if (flag2 == false && flag1 == false) {
				if (yF >= y && xF <= x) {
					this.y = 650;
				}
			}
			if (flag2 == true && flag1 == false) {
				if (yF <= y && xF <= x) {
					this.y = 650;
				}
			}
			if (flag2 == false && flag1 == true) {
				if (yF >= y && xF >= x) {
					this.y = 650;
				}
			}

		}

		EnemyBullet temp = enemyBullets.getHead();
		for (int i = 0; i < enemyBullets.getSize(); i++) {
			if (temp != null) {
				if (playerRectagle.intersects(temp.getBounds())) {
					if (this.y <= temp.y) {
						enemyBullets.removeBullet(this);
						int lifePoints = player.getlifePoints();
						int shieldLife = player.getShiledLife();
						if (player.getShield()) {
							player.setShieldLife(--shieldLife);
						} else {
							this.player.setlifePoints(lifePoints -= temp.getType() * 5);
						}
					}
				}
				if (temp.getNext() != null) {
					temp = temp.getNext();
				}
			}
		}
	}

	/**
	 * Draw the image depends on the type
	 * 
	 * @param g
	 *            the graphic object
	 */
	public void render(Graphics g) {
		if (this.type == 0) {
			g.drawImage(image, (int) x, (int) y, 16, 16, null);
		} else {
			g.drawImage(image1, (int) x, (int) y, 16, 16, null);
		}

	}

	/**
	 * Return the y position
	 * 
	 * @return the y position
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Return the next object in the list
	 * 
	 * @return the next object in the list
	 */
	public EnemyBullet getNext() {
		return this.next;
	}

	/**
	 * Set the next object in the list
	 * 
	 * @param next
	 *            the next object in the list
	 */
	public void setNext(EnemyBullet next) {
		this.next = next;
	}

	/**
	 * Return the type
	 * 
	 * @return the type
	 */
	public int getType() {
		return this.type;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
	}

}
