package airwar.enemies;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

public class NodoJet {

	private int posX;
	private int posY;
	public Random rnd = new Random();

	public final ImageIcon img;
	public NodoJet next;
	private int pos;

	public NodoJet(int valueX, int valueY, int tipo) {
		setNext(null);
		this.posX = valueX;
		this.posY = -256 * valueY;
		img = new ImageIcon(this.getClass().getResource("/airwar/images/jet.png"));
	}

	public NodoJet getNext() {
		return next;
	}

	public int getpos() {
		return pos;
	}

	public void setNext(NodoJet next) {
		this.next = next;
	}

	public void move() {
		int x = 2;
		this.cambiarPosY(true);
		this.cambiarPosY(true);
		int value = rnd.nextInt(x);
		if (value < (x / 2)) {
			if (this.getPosX() < 800 - 32) {
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
			this.posY++;

		} else {
			this.posY--;
		}
	}

	public void cambiarPosX(boolean Flag) {
		if (Flag == true) {
			this.posX++;

		} else {
			this.posX--;
		}
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosX(int x) {
		this.posX = x;
	}

	public void setPosY(int y) {
		this.posY = y;
	}

	// depende de la dirección de la moto carga su correspondiente imagen.
	public void paintImage(Graphics g) {
		g.drawImage(img.getImage(), this.posX, this.posY, 32, 64, null);
	}

}
