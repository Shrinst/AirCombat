package airwar.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;

public class GameWindow extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3491837610114986480L;
	private final String NAME = "AirWar";
	private final int WIDTH = 800;
	private static int aps = 0;
	private static int fps = 0;
	private final int HEIGHT = 600;
	private static volatile boolean isAlive = false;
	private static Thread thread;
	private static JFrame window;
	
	public GameWindow() {
		this.setBackground(Color.DARK_GRAY);
		this.initialize();
		this.loadContent();	
		this.startGame();
		window.setVisible(true);	
	}	
		
	// Setup Variables
	private void initialize() {
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setSize(WIDTH, HEIGHT);
		window.setLocationRelativeTo(null);			
		window.add(this);		
	}
	
	// Setup Images
	private void loadContent() {
		
	}
	
	// Change the variable's state
	private void updateWindow() {
		aps++;
	}
	
	// Show the Graphics
	private void drawWindow() {
		Graphics g = getGraphics();
		g.drawLine(100, 200, 200, 300);
		g.dispose();
		fps++;
	}
	
	private synchronized void startGame() {
		isAlive = true;
		thread = new Thread(this, "AirWar");
		thread.start();		
	}
	
	private synchronized void stopGame() {
		isAlive = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	@Override
	public void paint(Graphics g) {
		//g.setColor(Color.BLACK);
		g.drawLine(100, 200, 200, 300);
		for (int i = 0; i < 1000; i++) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(40, 40, 100, 100);
			g.setColor(Color.WHITE);
			g.drawString(String.valueOf(i), 50, 50);	
			this.repaint();
		}
	}
	

	@Override
	public void run() {		
		// Initialize the variable to limit the loop´s max speed
		final int NS_PER_SECOND = 1000000000; // How many nanoseconds are there in a second
		final byte APS = 60; // Actualization Per Second
		final double NS_PER_ACTUALIZATION = NS_PER_SECOND / APS; // How many nanoseconds are there in an actualization
		
		long updateReference = System.nanoTime(); // Reference before the loop starts
		long counterReference = System.nanoTime();
		
		double timeElapsed;
		double delta = 0; // Amount of time until an update is made
		
		
		while (isAlive) {
			final long startLoop = System.nanoTime(); // Starts counting the time
			
			timeElapsed = startLoop - updateReference; // How much time has passed since the loop starts
			updateReference = startLoop;
			
			delta += timeElapsed / NS_PER_ACTUALIZATION;
			
			while (delta >= 1) {
				updateWindow(); // Will be executed each 60 actualizations per second
				drawWindow();
				this.repaint();
				delta--;
			}
			//drawWindow();
			
			// See the information about the FPS and APS
			if ((System.nanoTime() - counterReference) > NS_PER_SECOND) { // The counter will be updated each second
				window.setTitle(NAME + " || APS: " + aps + " || FPS: " + fps);
				aps = 0;
				fps = 0;
				counterReference = System.nanoTime();
			}
		}
	}
	
	public static void main(String[] args) {
		new GameWindow();
	}
}
