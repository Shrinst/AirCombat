package airwar.graphics;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import airwar.player.Ship;

import airwar.structures.*;

public class GamePanel extends JPanel{		
	
	private static final long serialVersionUID = -561847312752914365L;
	//private static Jet jet;	
	private static Ship ship;	
	private int shipNum = 50;

	public Random rnd = new Random();
	private ImageIcon background;	

	private EnemyQueue queue = new EnemyQueue();
	
	public GamePanel() {
		initialize();
		loadContent();
	}
	
	private void initialize() {
		ship = new Ship();
	}
	
	private void loadContent() {
		background = new ImageIcon(this.getClass().getResource("/airwar/images/Desierto basilisco2.jpg"));
		for(int i = 0; i < shipNum;i++){//cantidad enemigos
			queue.insert();
		}
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(background.getImage(), 0, 0, 800, 600, null);
		int x1 = Ship.getPositionX();
		int y1 = Ship.getPositionY();
		int value = rnd.nextInt(30);
		ship.paintImage(g, x1, y1);
		if(queue.size()>3 && value==6){
				queue.delete();	
		}
		queue.action(g);
	}
}