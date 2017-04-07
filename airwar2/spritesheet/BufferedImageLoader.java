package airwar2.spritesheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Emanuel
 * @version 1.1.0
 *
 */
public class BufferedImageLoader {

	private BufferedImage image;

	/**
	 * Will return a loaded image, to be used
	 * 
	 * @param path
	 *            the image location
	 * @return the image
	 */
	public BufferedImage loadImage(String path) {
		try {
			image = ImageIO.read(this.getClass().getResource(path));
		} catch (IOException ex) {
			ex.printStackTrace();
			;
		}
		return image;
	}
}
