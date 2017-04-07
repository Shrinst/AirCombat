package airwar2.powerups;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

/**
 * 
 * @author Emanuel, David
 * @version 1.1.0
 *
 */
public class PowerUps {

	private PowerUps next;
	private int posValueX;
	private int posValueY;
	private int type;

	private BufferedImage image;

	/**
	 * Setup the variables
	 * 
	 * @param type
	 *            the power up type
	 */
	public PowerUps(int type) {
		this.type = type;
	}

	/**
	 * Setup the variables
	 * 
	 * @param valueX
	 *            the x position
	 * @param valueY
	 *            the y position
	 * @param type
	 *            the power up type
	 * @param game
	 *            reference to the game
	 */
	public PowerUps(int valueX, int valueY, int type, Game game) {
		next = null;
		this.posValueX = valueX;
		this.posValueY = valueY;
		this.type = type;

		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
		this.image = spriteSheet.grabImage(1, 1, 32, 32);
	}

	/**
	 * Move the power up
	 */
	public void tick() {
		posValueY++;
	}

	/**
	 * Draw the power ups depends on the type
	 * 
	 * @param g
	 *            the graphic object
	 */
	public void render(Graphics g) {
		g.drawImage(image, this.posValueX, this.posValueY, null);
	}

	/**
	 * Returns the next in the power up list
	 * 
	 * @return the next in the power up list
	 */
	public PowerUps getNext() {
		return next;
	}

	/**
	 * Set the next in the power up list
	 * 
	 * @param next
	 *            the next in the power up list
	 */
	public void setNext(PowerUps next) {
		this.next = next;
	}

	/**
	 * Returns the power up type
	 * 
	 * @return the power up type
	 */
	public int getType() {
		return this.type;
	}

	/**
	 * Set the power up type
	 * 
	 * @param type
	 *            the power up type
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Returns the x position
	 * 
	 * @return the x position
	 */
	public int getPosX() {
		return this.posValueX;
	}

	/**
	 * Returns the y position
	 * 
	 * @return the y position
	 */
	public int getPosY() {
		return this.posValueY;
	}

	/**
	 * Returns the collision rectangle
	 * 
	 * @return the collision rectangle
	 */
	public Rectangle getBounds() {
		return new Rectangle(this.posValueX, this.posValueY, image.getWidth(null), image.getHeight(null));
	}
}
