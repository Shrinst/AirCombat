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


	// Método que detecta cuando se presiona una tecla y ejecuta una acción
	// determinada.
	public int getState() {
		return this.state;
	}
	public void keyPressed(KeyEvent arg0) {
		int codigo = arg0.getKeyCode();
	}

	// método de la interfaz KeyListener
	public void keyReleased(KeyEvent arg0) {

	}

	// método de la interfaz KeyListener
	public void keyTyped(KeyEvent arg0) {

	}
}
