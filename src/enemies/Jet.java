package enemies;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import Logica.*;

/**
 * Crea la moto, la cual será el medio para que el juego pueda ser jugable
 * 
 * @author Emmanuel
 * @author Vinicio
 * @version 1.1.0
 * @since Tron 1.1.0
 */
public class Jet {

	// atributos

	private static Mando mando = new Mando();
	
	private static int posX ;
	private static int posY;
	public Random rnd = new Random();

	public final ImageIcon img;


	/**
	 * Crea el constructor de la clase. Defina donde se ubicará la moto por
	 * defecto
	 */
	public Jet() {
		// Carga las imágenes.
		img = new ImageIcon(this.getClass().getResource("/Imagenes/1444842379440.png"));

	}

	public void crearestela() {
		
	}

	/**
	 * Método que modifica la posición X de la moto en la matriz
	 * 
	 * @param Flag
	 *            booleano que dicta si se incrementa o decrementa la posición
	 *            de la moto. Verdadero la incrementa, Falso la decrementa
	 */
	public void cambiarPosY(boolean Flag) {
		if (Flag == true) {
			this.posY+=3;
		
		} else {
			this.posY-=3;
		}
	}
	public void cambiarPosX(boolean Flag) {
		if (Flag == true) {
			this.posX+=4;
		
		} else {
			this.posX-=4;
		}
	}


	/**
	 * Método que modifica la posición Y de la moto en la matriz
	 * 
	 * @param Flag
	 *            booleano que dicta si se incrementa o decrementa la posición
	 *            de la moto. Verdadero la decrementa, Falso la incrementa
	 * 
	 */

	/**
	 * Devuelve la posición que tiene la moto
	 * 
	 * @return Posición en X de la moto
	 */
	public int getPosX() {
		return posX;
	}
	/**
	 * Devuelve la posición que tiene la moto
	 * 
	 * @return Posición en Y de la moto
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
