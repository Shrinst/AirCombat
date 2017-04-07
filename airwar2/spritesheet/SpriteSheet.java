package airwar2.spritesheet;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class SpriteSheet {

	private BufferedImage image;

	/**
	 * This method will set image
	 * 
	 * @param image
	 *            the image to grab another one
	 */
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Will return an image from the original image
	 * 
	 * @param column
	 *            the column where the image is located
	 * @param row
	 *            the row where the image is located
	 * @param width
	 *            the image width
	 * @param height
	 *            the image height
	 * @return image from the original image
	 */
	public BufferedImage grabImage(int column, int row, int width, int height) {
		BufferedImage img = image.getSubimage((column * 32) - 32, (row * 32) - 32, width, height);
		return img;
	}
}
