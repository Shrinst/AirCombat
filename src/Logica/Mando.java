package Logica;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Graficos.PanelJuego;

/**
 * @author Vinicio Monge
 * @version
 * @since
 * 
 */
public class Mando implements KeyListener {

	// atributos
	private int state;
	public PanelJuego render = new PanelJuego();


	// M�todo que detecta cuando se presiona una tecla y ejecuta una acci�n
	// determinada.
	public int getState() {
		return this.state;
	}
	public void keyPressed(KeyEvent arg0) {
		int codigo = arg0.getKeyCode();
	}

	// m�todo de la interfaz KeyListener
	public void keyReleased(KeyEvent arg0) {

	}

	// m�todo de la interfaz KeyListener
	public void keyTyped(KeyEvent arg0) {

	}
}
