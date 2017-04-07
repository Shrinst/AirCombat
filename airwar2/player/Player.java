package airwar2.player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.datastructures.EnemyList;
import airwar2.datastructures.PowerUpsList;
import airwar2.datastructures.StackPowerUps;
import airwar2.enemies.Enemies;
import airwar2.graphics.Game;
import airwar2.playmusic.SoundEffectPlayer;
import airwar2.powerups.PowerUps;
import airwar2.spritesheet.SpriteSheet;

/**
 * 
 * @author Emanuel
 * @version 1.2.0
 *
 */
public class Player {

	private int x;
	private int y;

	private int velX = 0;
	private int velY = 0;

	private boolean is_Shield;
	private int lifePoints = 100;
	private int hearts = 3;
	private int shiledLife = 3;

	private BufferedImage player;

	private Rectangle playerRectangle;
	private EnemyList enemyList;
	private PowerUpsList powerUpsList;
	private StackPowerUps stackPowerUps;
	private Game game;

	/**
	 * Setup the variables
	 * 
	 * @param x
	 *            the position on the x axis
	 * @param y
	 *            the position on the y axis
	 * @param game
	 *            the reference to the game
	 */
	public Player(int x, int y, Game game) {
		this.x = x;
		this.y = y;

		this.game = game;
		this.enemyList = this.game.getEnemyQueue().getEnemyList();
		this.powerUpsList = this.game.getPowerUpsList();
		stackPowerUps = game.getStack();

		SpriteSheet spriteSheet = new SpriteSheet(this.game.getSpriteSheet());
		this.player = spriteSheet.grabImage(1, 1, 32, 32);
	}

	/**
	 * Will move the player check for collisions
	 */
	public void tick() {
		x += velX;
		y += velY;

		this.playerRectangle = this.getBounds();

		if (x <= 0) {
			x = 0;
		}
		if (x >= 640 - 16) {
			x = 640 - 16;
		}
		if (y <= 0) {
			y = 0;
		}
		if (y >= 610 - 32) {
			y = 610 - 32;
		}
		collideWithEnemy();
		collideWithPowerUp();
		if (hearts > 0) {
			if (this.lifePoints <= 0) {
				this.lifePoints = 100;
				this.hearts--;
				stackPowerUps.clean();
				game.setTypeBullet(0);
			}
		}

		if (this.shiledLife <= 0) {
			this.is_Shield = false;
			this.shiledLife = 3;
		}
	}

	/**
	 * Will detect collision with an enemy ship
	 */
	private void collideWithEnemy() {
		Enemies temp = enemyList.getHead();
		for (int i = 0; i < enemyList.getSize(); i++) {
			if (playerRectangle.intersects(temp.getBounds())) {
				if (this.y <= temp.getPosY()) {
					if (temp.getType() < 5) {
						new SoundEffectPlayer("Allahu_Akbar").play(-10);
					}
					enemyList.delete(temp.getPosY());
					if (is_Shield) {
						--shiledLife;
					} else if (temp.getType() < 5) {
						this.lifePoints -= 100;
					} else {
						this.lifePoints -= 50;
					}
				}
			}
			temp = temp.getNext();
		}
	}

	/**
	 * Will detect collision with a power up
	 */
	public void collideWithPowerUp() {
		PowerUps temp = powerUpsList.getHead();
		for (int i = 0; i < powerUpsList.getSize(); i++) {
			if (temp != null) {
				if (playerRectangle.intersects(temp.getBounds())) {
					if (this.y <= temp.getPosY()) {
						if (game.getStack().getSize() < 5)
							game.pushStack(temp, temp.getType());
						powerUpsList.removePowerUp(temp);
					}
				}
				if (temp.getNext() != null) {
					temp = temp.getNext();
				}
			}
		}
	}

	/**
	 * Draw the player and his shield
	 * 
	 * @param g
	 *            the graphic object
	 */
	public void render(Graphics g) {
		g.drawImage(this.player, this.x, this.y, null);
		if (is_Shield) {
			g.setColor(Color.RED);
			g.drawRect(x, y, player.getWidth(), player.getHeight());
		}
	}

	/**
	 * Returns the player hearts
	 * 
	 * @return the player hearts
	 */
	public int getHearts() {
		return this.hearts;
	}

	/**
	 * If the player hearts is less than 4, adds one
	 */
	public void addHeart() {
		if (this.hearts <= 4) {
			this.hearts++;
		}
	}

	/**
	 * Returns if the player's shield is activate
	 * 
	 * @return if the player's shield is activate
	 */
	public boolean getShield() {
		return this.is_Shield;
	}

	/**
	 * Activates and deactivates the player shield
	 * 
	 * @param shield
	 *            the shield flag
	 */
	public void setShield(boolean shield) {
		this.is_Shield = shield;
	}

	/**
	 * Returns the player life points
	 * 
	 * @return the player life points
	 */
	public int getlifePoints() {
		return this.lifePoints;
	}

	/**
	 * Set the player life points
	 * 
	 * @param the
	 *            player life points
	 */
	public void setlifePoints(int lifePoints) {
		this.lifePoints = lifePoints;
	}

	/**
	 * Returns the shield life
	 * 
	 * @return the shield life
	 */
	public int getShiledLife() {
		return this.shiledLife;
	}

	/**
	 * Set the shield life
	 * 
	 * @param shieldLife
	 *            the shield life
	 */
	public void setShieldLife(int shieldLife) {
		this.shiledLife = shieldLife;
	}

	/**
	 * Returns the x position
	 * 
	 * @return the x position
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Returns the y position
	 * 
	 * @return the y position
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Set the x position
	 * 
	 * @param x
	 *            the x position
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the y position
	 * 
	 * @param y
	 *            the y position
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Set the x speed
	 * 
	 * @param velX
	 *            the x speed
	 */
	public void setVelX(int velX) {
		this.velX = velX;
	}

	/**
	 * Set the y speed
	 * 
	 * @param velY
	 *            the y speed
	 */
	public void setVelY(int velY) {
		this.velY = velY;
	}

	/**
	 * Returns the collision rectangle
	 * 
	 * @return the collision rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle((int) this.getX(), (int) this.getY(), player.getWidth(null), player.getHeight(null));
	}
}