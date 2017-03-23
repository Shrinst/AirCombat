package airwar.player;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class NodeBullet {
	
	private static int x;
	private static int y;
	
	private ImageIcon img;
	private NodeBullet next;
	
	public NodeBullet(int x, int y) {
		next = null;
		NodeBullet.x = x;
		NodeBullet.y = y;
		img = new ImageIcon(this.getClass().getResource("/airwar/images/PlayerBullet.png"));
	}
	
	public void tick() {
		new Thread(new Runnable() {
			public void run() {
				while (y > 0) {
					y -= 1;
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {					
						e.printStackTrace();
					}
				}				
			}
		}).start();		
	}
	
	public void render(Graphics g) {
		g.drawImage(img.getImage(), x, y, 15, 15, null);
	}
	
	public void setNext(NodeBullet next) {
		this.next = next;
	}
	
	public NodeBullet getNext() {
		return this.next;
	}
	
	public int getY() {
		return y;
	}
}
