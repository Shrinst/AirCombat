package airwar.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener{
	
	// atributos
		private int state;	


		// M�todo que detecta cuando se presiona una tecla y ejecuta una acci�n
		// determinada.
		public int getState() {
			return this.state;
		}


		@Override
		public void keyPressed(KeyEvent e) {
			int codigo = e.getKeyCode();			
		}


		@Override
		public void keyReleased(KeyEvent e) {
			
			
		}


		@Override
		public void keyTyped(KeyEvent e) {
		
			
		}
		
}
