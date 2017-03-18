package airwar.graphics;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import airwar.player.Ship;

public class GamePanel extends JPanel{		
	
	private static final long serialVersionUID = -561847312752914365L;
	//private static Jet jet;	
	private static Ship ship;	
	private ImageIcon background;	
	
	public GamePanel() {
		initialize();
		loadContent();
	}
	
	private void initialize() {
		ship = new Ship();		
		//jet = new Jet();
	}
	
	private void loadContent() {
		background = new ImageIcon(this.getClass().getResource("/airwar/images/Desierto basilisco2.jpg"));
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, 800, 600, null);
		int x1 = Ship.getPositionX();
		int y1 = Ship.getPositionY();
		//jet.paintImage(g, x1, y1);	
		ship.paintImage(g, x1, y1);
	}
}
