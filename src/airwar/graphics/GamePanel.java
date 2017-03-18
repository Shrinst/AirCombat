package airwar.graphics;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import airwar.enemies.Jet;

public class GamePanel extends JPanel{	
	// atributos	
	
	private static final long serialVersionUID = -561847312752914365L;
	private static Jet jet = new Jet();		
	private  ImageIcon background;
	
	/**
	 * Crea el constructor de la clase. Se definen tamaño, y se instancian los
	 * objetos que es utilizan en la métodos dela clase
	 */
	public GamePanel() {
		loadContent();
	}
	
	private void loadContent() {
		background = new ImageIcon(this.getClass().getResource("/airwar/images/Desierto basilisco2.jpg"));
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, 800, 600, null);
		int x1 = 100;
		int y1 = 100;
		jet.paintImage(g, x1, y1);			

	}
}
