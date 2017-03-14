package enemies;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import Logica.*;

/**
 * Crea el jet, la cual será el medio para que el juego pueda ser jugable
 * @author Vinicio
 */
public class Jet {

	// atributos

	private static Mando mando = new Mando();
	
	private static int posX ;
	private static int posY;
	public Random rnd = new Random();

	public final ImageIcon img;


	/**
	 * Crea el constructor de la clase. 
	 */
	public Jet() {
		// Carga la imagen.
		img = new ImageIcon(this.getClass().getResource("/Imagenes/1444842379440.png"));

	}

	public void crearestela() {
		
	}

	/**
	 * Método que modifica la posición Y del Jet en la matriz
	 * 
	 * @param Flag
	 *            booleano que dicta si se incrementa o decrementa la posición
	 *            del Jet. Verdadero la incrementa, Falso la decrementa
	 */
	public void cambiarPosY(boolean Flag) {
		if (Flag == true) {
			this.posY+=3;
		
		} else {
			this.posY-=3;
		}
	}
	/**
	 * Método que modifica la posición X de la moto en la matriz
	 * 
	 * @param Flag
	 *            booleano que dicta si se incrementa o decrementa la posición
	 *            del Jet. Verdadero la decrementa, Falso la incrementa
	 * 
	 */
	public void cambiarPosX(boolean Flag) {
		if (Flag == true) {
			this.posX+=4;
		
		} else {
			this.posX-=4;
		}
	}


	

	/**
	 * Devuelve la posición que tiene el Jet
	 * 
	 * @return Posición en X del Jet
	 */
	public int getPosX() {
		return posX;
	}
	/**
	 * Devuelve la posición que tiene el Jet
	 * 
	 * @return Posición en Y del Jet
	 * 
	 */
	public int getPosY() {
		return this.posY;
	}
	// depende de la dirección de la moto carga su correspondiente imagen.
	public void paintImage(Graphics g, int x, int y) {
			g.drawImage(img.getImage(), x - 8, y - 50, 32, 64, null);
	}

}
