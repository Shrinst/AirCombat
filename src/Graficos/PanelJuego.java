package Graficos;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Juego.Juego;
import enemies.*;


/**
 * Dibuja en la interfaz correspondiente a la ventana de juego
 * 
 * @author Emmanuel
 * @version 1.1.0
 * @since Tron 1.1.0
 *
 */
public class PanelJuego extends JPanel{
	// atributos
	
	public static Jet jet = new Jet();
	public static Juego juego;
	public final ImageIcon background;

	/**
	 * Crea el constructor de la clase. Se definen tamaño, y se instancian los
	 * objetos que es utilizan en la métodos dela calse
	 */
	public PanelJuego() {
		background = new ImageIcon(this.getClass().getResource("/Imagenes/Desierto basilisco2.jpg"));
	}

	@Override
	/**
	 * Método que, dependiendo del valor de cada casilla de la matriz principal,
	 * dibuja imágenes en pantalla
	 */
	public void paint(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, 800, 600, null);
		int x1 = 100;
		int y1 = 100;
		jet.paintImage(g, x1, y1);
		
		

	}


}
