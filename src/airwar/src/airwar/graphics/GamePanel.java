package airwar.graphics;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import airwar.player.Ship;
import airwar.structures.EnemyQueue;

public class GamePanel extends JPanel{		
	
	private static final long serialVersionUID = -561847312752914365L;		
	private static Ship ship;	
	private ImageIcon background;	
	public static boolean shoot = false;
	private int shipNum = 50;
	
	private Random rnd = new Random();
	private EnemyQueue queue;
	
	public GamePanel() {
		initialize();
		loadContent();
	}
	
	private void initialize() {
		ship = new Ship();
		queue = new EnemyQueue();
	}
	
	private void loadContent() {
		background = new ImageIcon(this.getClass().getResource("/airwar/images/Desierto basilisco2.jpg"));
		for(int i = 0; i < shipNum;i++){//cantidad enemigos
			queue.insert();
		}
	}

	@Override
	public void paint(Graphics g) {
		int value = rnd.nextInt(30);
		g.drawImage(background.getImage(), 0, 0, 800, 600, null);
		int x1 = Ship.getPositionX();
		int y1 = Ship.getPositionY();			
		ship.paintImage(g, x1, y1, shoot);
		if (queue.size() > 3 && value == 6) {
			queue.delete();
		}
		queue.action(g);
	}
}
