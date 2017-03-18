package airwar.enemies;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

public class Jet {

	private static int posX;
	private static int posY;
	public Random rnd = new Random();

	public final ImageIcon img;

	/**
	 * Crea el constructor de la clase.
	 */
	public Jet() {
		// Carga la imagen.
		img = new ImageIcon(this.getClass().getResource("/airwar/images/1444842379440.png"));

	}

	public void move() {		
		int x = 2;
		this.cambiarPosY(true);
		int value = rnd.nextInt(x);

		if (value < (x / 2)) {
			this.cambiarPosX(true);
			if (this.getPosX() < 286) {
				this.cambiarPosX(true);
			}
		} else {
			if (this.getPosX() > 0) {
				this.cambiarPosX(false);
			}
		}
	}

	public void cambiarPosY(boolean Flag) {
		if (Flag == true) {
			Jet.posY++;

		} else {
			Jet.posY--;
		}
	}

	public void cambiarPosX(boolean Flag) {
		if (Flag == true) {
			Jet.posX++;

		} else {
			Jet.posX--;
		}
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return Jet.posY;
	}

	public void setPosX(int x) {
		Jet.posX = x;
	}

	public void setPosY(int y) {
		Jet.posY = y;
	}

	// depende de la dirección de la moto carga su correspondiente imagen.
	public void paintImage(Graphics g, int x, int y) {
		g.drawImage(img.getImage(), x - 8, y - 50, 32, 64, null);
	}

}
