package airwar2.enemies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

import airwar2.datastructures.EnemyBullets;
import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

/**
 * 
 * @author Vinicio
 * @version 1.2.0
 *
 */
public class Enemies {

	private int posX;
	private int posY;
	private int posYF;
	private int armor;
	private int type;
	private int clavo;// variable creada en honor a Milton Villegas Lemus
	public Random rnd = new Random();

	private final BufferedImage kamikaze;
	private final BufferedImage jet;
	private final BufferedImage tower1;
	private final BufferedImage tower2;
	private final BufferedImage boss1;
	private EnemyBullets enemyBullets;
	private Enemies next;
	private int width;
	private Game game;

	/**
	 * Setup the variables
	 * 
	 * @param valueX
	 *            the position
	 * @param tipo
	 *            the enemy type
	 * @param game
	 *            reference to the game
	 */
	public Enemies(int valueX, int tipo, Game game) {

		setNext(null);
		this.posX = valueX;
		this.posY = 0;
		this.game = game;
		this.type = tipo;
		this.enemyBullets = this.game.getEnemyBullets();
		if (type < 5) { // KAMIKAZE
			armor = 0;
			width = 32;
		}
		if (type > 4 && type < 12) { // JET
			armor = 0;
			width = 32;
		}
		if (type > 11 && type < 19) { // BOMBER
			armor = 1;
			width = 32;
		}
		if (type == 19) { // TOWER 1
			armor = 0;
			width = 32;
			posYF = rnd.nextInt(500 - 64);
		}
		if (type == 20) { // TOWER 2
			armor = 0;
			width = 32;
			posYF = rnd.nextInt(500 - 64);
		}
		if (type == 21) { // Boss
			armor = 9;
			width = 128;
		}
		SpriteSheet spriteSheet = new SpriteSheet(this.game.getSpriteSheet());
		this.kamikaze = spriteSheet.grabImage(7, 1, 32, 32);
		this.jet = spriteSheet.grabImage(6, 1, 32, 32);
		this.tower1 = spriteSheet.grabImage(4, 1, 32, 32);
		this.tower2 = spriteSheet.grabImage(5, 1, 32, 32);
		this.boss1 = spriteSheet.grabImage(1, 1, 128, 128);
	}

	/**
	 * Move the enemies
	 * 
	 * @param targetX
	 *            the player's x position
	 * @param targetY
	 *            the player's y position
	 */
	public void tick(int targetX, int targetY) {
		if (type < 5) {
			if (targetY > this.posY) { // if the bullet is above the player´s
										// position
				if (targetY + 16 > this.posY) {
					this.cambiarPosY(true, 2);
				}
				if (targetX + 8 > this.posX) {
					this.cambiarPosX(true, 5);
				}
				if (targetX + 8 < this.posX) {
					this.cambiarPosX(false, 5);
				}
			} else if (targetY <= this.posY) { // if the bullet is below the
												// player´s position
				this.cambiarPosY(true, 2);
			}
		}
		if (type > 4 && type < 12) {
			int x = 2;
			this.cambiarPosY(true, 4);
			int value = rnd.nextInt(x);
			if (value < (x / 2)) {
				if (this.getPosX() < 640 - 32) {
					this.cambiarPosX(true, 3);

				}
			} else {
				if (this.getPosX() > 32) {
					this.cambiarPosX(false, 3);
				}
			}
		}
		if (type > 11 && type < 19) {
			this.cambiarPosY(true, 2);
			int value = rnd.nextInt(2);
			if (value == 0) {
				if (this.getPosX() < 640 - 32) {
					this.cambiarPosX(true, 3);
				}
			} else {
				if (this.getPosX() > 32) {
					this.cambiarPosX(false, 3);
				}
			}
		}
		if (type > 18 && type < 21) {
			if (this.posY <= posYF) {
				this.cambiarPosY(true, 2);
			}
		}
		if (type == 21) {

		}
	}

	/**
	 * Draw an image depends on the type
	 * 
	 * @param g
	 *            the graphic object
	 */
	public void render(Graphics g) {
		if (type < 5) { // KAMIKAZE
			g.drawImage(kamikaze, this.posX, this.posY, 32, 32, null);
		}
		if (type > 4 && type < 12) { // JET
			g.drawImage(jet, this.posX, this.posY, 32, 32, null);
		}
		if (type > 11 && type < 19) { // BOMBER
			g.drawImage(jet, this.posX, this.posY, 32, 32, null);
		}
		if (type == 19) { // TOWER 1
			g.drawImage(tower1, this.posX, this.posY, 32, 32, null);
		}
		if (type == 20) { // TOWER 2
			g.drawImage(tower2, this.posX, this.posY, 32, 32, null);
		}
		if (type == 21) { // Boss 1
			posY = 100;
			g.drawImage(boss1, this.posX, this.posY, 32 * 4, 32 * 4, null);
			g.drawRect(posX, posY, 128, 128);
		}
	}

	/**
	 * Make the enemies shoot
	 */
	public void shoot() {
		if (clavo == 60) {
			if (type > 4 && type < 12) {
				enemyBullets.addBullet(new EnemyBullet(this.getPosX(), this.getPosY(), 0, game));
				clavo = 0;
			} else if (type > 11 && type < 19) {
				enemyBullets.addBullet(new EnemyBullet(this.getPosX(), this.getPosY(), 1, game));
				clavo = 0;
			} else if (type == 19 && this.posY > posYF) {
				enemyBullets.addBullet(new EnemyBullet(this.getPosX(), this.getPosY(), 3, game));
				clavo = 0;
			} else if (type == 20 && this.posY > posYF) {
				enemyBullets.addBullet(new EnemyBullet(this.getPosX(), this.getPosY(), 4, game));
				clavo = 0;
			}
		} else {
			clavo++;
		}

	}

	/**
	 * Move the enemy in the y axis
	 * 
	 * @param Flag
	 *            true upwards, false downwards
	 * @param cant
	 *            how fast will move
	 */
	private void cambiarPosY(boolean Flag, int cant) {
		if (Flag == true) {
			this.posY += cant;

		} else {
			this.posY -= cant;
		}
	}

	/**
	 * Move the enemy in the x axis
	 * 
	 * @param Flag
	 *            true upwards, false downwards
	 * @param cant
	 *            how fast will move
	 */
	private void cambiarPosX(boolean Flag, int cant) {
		if (Flag == true) {
			this.posX += cant;

		} else {
			this.posX -= cant;
		}
	}

	/**
	 * Returns the next in the list
	 * 
	 * @return the next in the list
	 */
	public Enemies getNext() {
		return next;
	}

	/**
	 * Set the next in the list
	 * 
	 * @param next
	 *            the next in the list
	 */
	public void setNext(Enemies next) {
		this.next = next;
	}

	/**
	 * Returns the x position
	 * 
	 * @return the x position
	 */
	public int getPosX() {
		return this.posX;
	}

	/**
	 * Returns the y position
	 * 
	 * @return the y position
	 */
	public int getPosY() {
		return this.posY;
	}

	/**
	 * Set the x position
	 * 
	 * @param x
	 *            the x position
	 */
	public void setPosX(int x) {
		this.posX = x;
	}

	/**
	 * Set the y position
	 * 
	 * @param y
	 *            the y position
	 */
	public void setPosY(int y) {
		this.posY = y;
	}

	/**
	 * Return the enemy type
	 * 
	 * @return the enemy type
	 */
	public int getType() {
		return type;
	}

	/**
	 * Return the enemy armor
	 * 
	 * @return the enemy armor
	 */
	public int getArmor() {
		return this.armor;
	}

	/**
	 * Set the enemy armor
	 * 
	 * @param cant
	 *            the enemy armor
	 */
	public void setArmor(int cant) {
		this.armor -= (cant + 1);
	}

	/**
	 * Return the collision rectangle
	 * 
	 * @return the collision rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle((int) this.posX, (int) this.posY, this.width, this.width);
	}
}