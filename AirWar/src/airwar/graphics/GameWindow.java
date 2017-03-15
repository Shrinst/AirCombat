package airwar.graphics;

import javax.swing.JFrame;

import airwar.enemies.Jet;

public class GameWindow extends JFrame implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3491837610114986480L;
	private final String NAME = "AirWar";
	
	private static final int WIDTH = 800;	
	private static final int HEIGHT = 600;
	private static int aps = 0;
	private static int fps = 0;
	private static volatile boolean isAlive = false;
	
	private static Thread thread;
	private static GamePanel gamePanel;
	private static Jet jet;
	
	public GameWindow() {		
		this.initialize();
		this.loadContent();	
		this.startGame();	
		this.setVisible(true);
	}	
		
	// Setup Variables
	private void initialize() {	
		gamePanel = new GamePanel();
		jet = new Jet();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);	
		this.add(gamePanel);
	}
	
	// Setup Images
	private void loadContent() {
		
	}
	
	// Change the variable's state
	private void updateWindow() {
		jet.move();
		gamePanel.repaint();
		aps++;
	}
	
	// Show the Graphics
	private void drawWindow() {
		gamePanel.validate();
		gamePanel.repaint();
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
				this.setTitle(NAME + " || APS: " + aps + " || FPS: " + fps);
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
