package airwar2.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.swing.JFrame;

import airwar2.datastructures.EnemyBullets;
import airwar2.datastructures.EnemyQueue;
import airwar2.datastructures.PlayerBullets;
import airwar2.datastructures.PowerUpsList;
import airwar2.datastructures.Stack;
import airwar2.multithreading.ThreadPool;
import airwar2.player.KeyInput;
import airwar2.player.Player;
import airwar2.playmusic.MusicPlayer;
import airwar2.playmusic.SoundEffectPlayer;
import airwar2.powerups.PowerUps;
import airwar2.player.Bullet;
import airwar2.spritesheet.BufferedImageLoader;
import airwar2.spritesheet.SpriteSheet;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -3637593333786223621L;

	public static final int WIDTH = 320;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	public final String NAME = "AirWar";
	public int fps = 0;
	public int aps = 0;
	private static int x = 0;
	private static int y = 0;
	private int shipNum = 50;
	private int score = 0;
	private static String shooting = "";
	private static boolean gamePad = true; // Android false; PC true
	private volatile boolean isAlive = false; // volatile is needed to make multi-threading works
	private boolean is_shooting = false;
	private boolean is_powerUp = false;
	private int typeBullet = 0;

	private Thread thread;
	private JFrame jframe;
	private Player player;
	private PlayerBullets bullets;
	private EnemyBullets enemyBullets;
	private EnemyQueue enemyQueue;
	private MusicPlayer musicPlayer;
	private LevelHandler levelHandler;
	private PowerUpsList powerUpsList;
	private Stack stack;
	
	private BufferedImage backgroundImage = null;
	private BufferedImage iconImage = null;
	private BufferedImage spriteSheet = null;

	private Random rnd = new Random();

	private ServerSocket serversocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private final int PORT = 2222;

	public Game() {			
		this.setUpGame();
		this.init();
		this.runServer();		
		//this.start();
		jframe.setVisible(true);
	}

	private void setUpGame() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		this.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));	
		

		jframe = new JFrame();
		jframe.add(this);
		jframe.pack();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setResizable(false);
		jframe.setLocationRelativeTo(null);			
	}

	private void init() {

		BufferedImageLoader loader = new BufferedImageLoader();

		try {
			spriteSheet = loader.loadImage("/airwar2/images/spritesheet_imagenes.png");
			SpriteSheet spriteSheet = new SpriteSheet(this.spriteSheet);
			backgroundImage = spriteSheet.grabImage(3, 1, 32, 32);
			iconImage = spriteSheet.grabImage(1, 1, 32, 32);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		this.addKeyListener(new KeyInput(this));		
		jframe.setIconImage(iconImage);

		
		bullets = new PlayerBullets();
		enemyBullets = new EnemyBullets();
		enemyQueue = new EnemyQueue(this);		
		musicPlayer = new MusicPlayer("Dr.Willy", "Wizard&Warriors", "ForestOfFellatio");	
		levelHandler = new LevelHandler(this);
		powerUpsList = new PowerUpsList(this);
		stack = new Stack();
		player = new Player(200, 200, this);

		for (int i = 0; i < shipNum; i++) {
			enemyQueue.insert();
		}
		
		ThreadPool threadPool = new ThreadPool(2);		
		threadPool.runTask(musicPlayer);
		threadPool.runTask(this);
		//threadPool.join();
	}

	private synchronized void start() {
		if (isAlive) {
			return;
		}
		isAlive = true;
		thread = new Thread(this);
		thread.start();
	}

	private synchronized void stop() {
		if (!isAlive) {
			return;
		}
		isAlive = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	private void tick() {
		if (!gamePad) {
			if (y > 0) {
				player.setVelX(y);
			}
			if (y < 0) {
				player.setVelX(-Math.abs(y));
				;
			}
			if (x > 0) {
				player.setVelY(x);				
			}
			if (x < 0) {
				player.setVelY(-Math.abs(x));
			}
			if (shooting.equals("shooting")) {
				//bullets.addBullet(new Bullet(player.getX(), player.getY(), this));
				new SoundEffectPlayer("Laser_Shoot11").play(-25);
			}
		}
		//levelHandler.tick();
		player.tick();
		bullets.tick();	
		enemyBullets.tick();	
		powerUpsList.tick();
		aps++;
	}

	private void render() {

		BufferStrategy bufferStrategy = this.getBufferStrategy();
		int value = rnd.nextInt(30);

		if (bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bufferStrategy.getDrawGraphics();

		// g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		//g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
		levelHandler.render(g);
		player.render(g);
		bullets.render(g);
		enemyBullets.render(g);
		powerUpsList.render(g);

		if (enemyQueue.getSize() > 0 && value == 6) {
			enemyQueue.delete();
		}
		enemyQueue.action(g, (int) player.getX(), (int) player.getY());
		
		g.setColor(Color.CYAN);		
		g.drawString(String.valueOf(score), 600, 50);

		g.dispose();
		bufferStrategy.show();
		fps++;
	}

	public void keyPressed(KeyEvent e) {
		if (gamePad) {
			int key = e.getKeyCode();

			if (key == KeyEvent.VK_D) {
				player.setVelX(5);
			} else if (key == KeyEvent.VK_A) {
				player.setVelX(-5);
			} else if (key == KeyEvent.VK_W) {
				player.setVelY(-5);
			} else if (key == KeyEvent.VK_S) {
				player.setVelY(5);
			} else if (key == KeyEvent.VK_Q && !is_powerUp) {
				is_powerUp = true;		
				if (!stack.is_empty()) {
					typeBullet = stack.pop().getType();			
				}				
			} else if (key == KeyEvent.VK_SPACE && !is_shooting) {
				is_shooting = true;
				bullets.addBullet(new Bullet(player.getX(), player.getY(), typeBullet, this));				
				new SoundEffectPlayer("Laser_Shoot11").play(-10);				
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (gamePad) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_D) {
				player.setVelX(0);
			} else if (key == KeyEvent.VK_A) {
				player.setVelX(0);
			} else if (key == KeyEvent.VK_W) {
				player.setVelY(0);
			} else if (key == KeyEvent.VK_S) {
				player.setVelY(0);
			} else if (key == KeyEvent.VK_Q) {
				is_powerUp = false;
			} else if (key == KeyEvent.VK_SPACE) {
				is_shooting = false;
			}
		}
	}

	public BufferedImage getSpriteSheet() {
		return this.spriteSheet;
	}
	
	public EnemyBullets getEnemyBullets() {
		return this.enemyBullets;
	}
	
	public EnemyQueue getEnemyQueue() {
		return this.enemyQueue;
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public PlayerBullets getPlayerBullets() {
		return this.bullets;
	}
	
	public PowerUpsList getPowerUpsList() {
		return this.powerUpsList;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public Stack getStack() {
		return this.stack;
	}
	
	public void pushStack(PowerUps powerup, int type) {
		this.stack.push(powerup, type);
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
			}

		} catch (IOException e) {
			System.out.println("Error. Reciving the Message Failed");
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		isAlive = true;
		requestFocus();
		// Initialize the variable to limit the loop´s max speed
		final int NS_PER_SECOND = 1000000000; // How many nanoseconds are there
												// in a second
		final byte APS = 60; // Actualization Per Second
		final double NS_PER_ACTUALIZATION = NS_PER_SECOND / APS;

		long updateReference = System.nanoTime(); // Reference before the loop
													// starts
		long counterReference = System.nanoTime();

		double timeElapsed;
		double delta = 0; // Amount of time until an update is made

		requestFocus();

		while (isAlive) {
			final long startLoop = System.nanoTime(); // Starts counting the
														// time

			timeElapsed = startLoop - updateReference; // How much time has
														// passed since the loop
														// starts
			updateReference = startLoop;
			delta += timeElapsed / NS_PER_ACTUALIZATION;

			while (delta >= 1) {
				tick(); // Will be executed each 60 actualizations per second
				render();
				delta--;
			}

			// See the information about the FPS and APS
			if ((System.nanoTime() - counterReference) > NS_PER_SECOND) {

				jframe.setTitle(NAME + " || APS: " + aps + " || FPS: " + fps);
				aps = 0;
				fps = 0;
				counterReference = System.nanoTime();
			}
		}
	}

	public static void main(String[] args) {
		new Game();
	}

}
