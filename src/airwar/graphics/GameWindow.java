package airwar.graphics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

import airwar.logic.Controller;
import airwar.player.Ship;

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
	private static int x = 0;
	private static int y = 0;
	private static String shooting = "";
	private static boolean gamePad = true;
	
	private static Thread thread;
	private static GamePanel gamePanel;	
	private static Controller controller;
	private static Ship ship;	
	
	private ServerSocket serversocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private final int PORT = 2222;	
	
	public GameWindow() {		
		this.initialize();
		this.loadContent();	
		this.runServer();
		this.startGame();	
		this.setVisible(true);
	}	
		
	// Setup Variables
	private void initialize() {	
		gamePanel = new GamePanel();		
		controller = new Controller();
		ship =  new Ship();		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);	
		this.addKeyListener(controller);
		this.add(gamePanel);
	}
	
	// Setup Images
	private void loadContent() {
		
	}
	
	// Change the variable's state
	private void updateWindow() {
		controller.update();
		
		if (gamePad) {
			
			if (controller.UP) {
				ship.changePosY(false, 10);
			} 
			if (controller.DOWN) {
				ship.changePosY(true, 10);
			} 
			if (controller.LEFT) {
				ship.changePosX(false, 10);
			}
			if (controller.RIGHT) {
				ship.changePosX(true, 10);
			}
			if (controller.SPACE) {
				GamePanel.shoot = true;					
			}
			
		} else {
			if (x > 0) {
				ship.changePosY(false, x);
			}
			if (x < 0) {
				ship.changePosY(true, Math.abs(x));
			} 
			if (y > 0) {
				ship.changePosX(true, y);
			} 
			if ( y < 0) {
				ship.changePosX(false, Math.abs(y));
			}
			if (shooting.equals("shooting")) {
				GamePanel.shoot = true;				
			}
		}	
		
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
	
	private void runServer() {
		new Thread(new Runnable() {
			public void run() {				
				
				while (isAlive) {										
					
					try {
						System.out.println("Server Starting at Port Number: " + PORT);
						serversocket = new ServerSocket(PORT);

						// Client Connect
						System.out.println("Waiting for Clients to Connect...");
						socket = serversocket.accept();
						System.out.println("A Client has Connected");
						

						while (isAlive) {	
							
							reciveMessage();				

							sendMessageToClient(String.valueOf(x) + ";" + String.valueOf(y) + ";" + shooting);

						}

						System.out.println("The Server has Ended");
						serversocket.close();
						socket.close();

					} catch (IOException e) {
						e.printStackTrace();
					}	
									
				}
				
			}
		}).start();
	}
	
	private void sendMessageToClient(String message) {
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			System.out.println("Error. Sending the Message Failed");
			e.printStackTrace();
		}
	}
	
	public void reciveMessage() {
		String data;
		String[] split;
		try {

			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			data = bufferedReader.readLine();
			split = data.split(";");
			if (data != null) {
				x = Integer.valueOf(split[0]);
				y = Integer.valueOf(split[1]);
				shooting = split[2];
				//System.out.println(x + ":" + y + ":" + shooting);
				//System.out.println("Message from the Client: " + data);							
			}			

		} catch (IOException e) {
			System.out.println("Error. Reciving the Message Failed");
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
		
		requestFocus();
		
		
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
