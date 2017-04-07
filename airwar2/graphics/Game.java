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
import airwar2.datastructures.StackPowerUps;
import airwar2.datastructures.StatList;
import airwar2.datastructures.StatNode;
import airwar2.multithreading.ThreadPool;
import airwar2.player.KeyInput;
import airwar2.player.Player;
import airwar2.playmusic.MusicPlayer;
import airwar2.playmusic.SoundEffectPlayer;
import airwar2.powerups.PowerUps;
import airwar2.player.Bullet;
import airwar2.spritesheet.BufferedImageLoader;
import airwar2.spritesheet.SpriteSheet;

/**
 * 
 * @author Emanuel
 * @version 1.2.0
 *
 */
public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = -3637593333786223621L;

	public static final int WIDTH = 420;
	public static final int HEIGHT = 300;
	public static final int SCALE = 2;
	public final String NAME = "AirWar";
	public int fps = 0;
	public int aps = 0;
	private static int x = 0;
	private static int y = 0;
	private int shipNum = 50;
	private int score = 0;
	private int scoreP = 0;
	private static String androidShooting = "";
	private static String androidPowerUp = "";
	private static boolean gamePad; // Android false; PC true
	private volatile boolean isAlive = false; // volatile is needed to make
												// multi-threading works
	private boolean is_shooting = false;
	private boolean is_powerUp = false;
	private int typeBullet = 0;
	private int currentLevelImageColumn = 1;
	private int currentLevelImageRow = 3;
	private int currentLevel = 1;

	private Thread thread;
	private JFrame jframe;
	private Player player;
	private PlayerBullets bullets;
	private EnemyBullets enemyBullets;
	private EnemyQueue enemyQueue;
	private MusicPlayer musicPlayer;
	private PowerUpsList powerUpsList;
	private StackPowerUps stack;
	private SpriteSheet spriteSheet2Grab;
	private StatList statList;
	private ThreadPool threadPool;

	private BufferedImage backgroundImage = null;
	private BufferedImage iconImage = null;
	private BufferedImage spriteSheet = null;

	private Random rnd = new Random();

	private ServerSocket serversocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private final int PORT = 2222;

	/**
	 * Creates the class constructor
	 * 
	 * @param gamePad
	 *            gamePad defines how the user will play, true for keyboard
	 *            false for Android
	 */

	public Game(boolean gamePad) {
		Game.gamePad = gamePad;
		this.setUpGame();
		this.init();
		this.runServer();
		// this.start();
		jframe.setVisible(true);
	}

	/**
	 * Will setup the frame variable. This will be the place where the main
	 * Canvas is located
	 */
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

	/**
	 * Will initiate the main variables
	 */
	private void init() {

		BufferedImageLoader loader = new BufferedImageLoader();

		try {
			spriteSheet = loader.loadImage("/airwar2/images/spritesheet_imagenes.png");
			spriteSheet2Grab = new SpriteSheet(this.spriteSheet);
			iconImage = spriteSheet2Grab.grabImage(1, 1, 32, 32);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		this.addKeyListener(new KeyInput(this));
		jframe.setIconImage(iconImage);

		bullets = new PlayerBullets();
		enemyBullets = new EnemyBullets();
		enemyQueue = new EnemyQueue(this);
		musicPlayer = new MusicPlayer("BackInBlack", "ChopSuey", "Wizard&Warriors", "Dr.Willy", "ForestOfFellatio");
		powerUpsList = new PowerUpsList(this);
		stack = new StackPowerUps();
		statList = new StatList();
		player = new Player(200, 200, this);

		statList.insertFirst("Corazones: ");
		statList.insertFirst("PowerUps: ");
		statList.insertFirst("Nivel: ");
		statList.insertFirst("Porcenteje de Vida: ");
		statList.insertFirst("Tiempo: ");
		statList.insertFirst("Puntaje: ");

		for (int i = 0; i < shipNum; i++) {
			enemyQueue.insert();
		}

		threadPool = new ThreadPool(2);
		threadPool.runTask(musicPlayer);
		threadPool.runTask(this);
		// threadPool.join();
	}

	/**
	 * Will start the game´s thread
	 */
	private synchronized void start() {
		if (isAlive) {
			return;
		}
		isAlive = true;
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * Will stop the game's thread
	 */
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

	/**
	 * This method will update all the variables 60 times per second
	 */
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
			if (androidPowerUp.equals("powerup")) {
				if (!stack.is_empty()) {
					if (stack.getFirst().getType() != 3) {
						typeBullet = stack.pop().getType();
					} else {
						player.setShield(true);
						stack.pop();
					}
				} else {
					typeBullet = 0;
				}
			}
			if (androidShooting.equals("shooting")) {
				bullets.addBullet(new Bullet(player.getX(), player.getY(), typeBullet, this));
				new SoundEffectPlayer("Laser_Shoot11").play(-25);
			}
		}

		if (this.enemyQueue.getEnemyList().isEmpty() && this.enemyQueue.isEmpty()) {
			currentLevelImageColumn++;
			if (currentLevelImageColumn == 8) {
				currentLevelImageRow++;
				currentLevelImageColumn = 1;
			}
			currentLevel++;
			for (int i = 0; i < shipNum; i++) {
				enemyQueue.insert();
			}

		}

		if (score > 100) {
			int aux = scoreP;
			scoreP = score - (score - (score % 100));
			if (aux > scoreP) {
				player.addHeart();

			}
		} else {
			scoreP = score;
		}

		/*
		 * if (player.getHearts() <= 0) { threadPool.stop(); }
		 */

		player.tick();
		bullets.tick();
		enemyBullets.tick();
		powerUpsList.tick();
		aps++;
	}

	/**
	 * This method will draw all the graphics 60 times per second
	 */
	private void render() {

		BufferStrategy bufferStrategy = this.getBufferStrategy();
		int value = rnd.nextInt(60);

		if (bufferStrategy == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bufferStrategy.getDrawGraphics();

		backgroundImage = spriteSheet2Grab.grabImage(currentLevelImageColumn, currentLevelImageRow, 32, 32);
		g.drawImage(backgroundImage, 0, 0, this.getWidth() - 185, this.getHeight(), null);
		g.setColor(Color.gray);
		g.fillRect(this.getWidth() - 185, 0, 185, this.getHeight());

		player.render(g);
		bullets.render(g);
		enemyBullets.render(g);
		powerUpsList.render(g);

		if (enemyQueue.getSize() > 0 && value == 6) {
			enemyQueue.delete();
		}
		enemyQueue.action(g, player.getX(), player.getY());

		g.setColor(Color.CYAN);

		int yS = 50;
		StatNode current = this.statList.getHead();
		for (int i = 0; i < statList.getSize(); i++) {
			g.drawString(current.getStat(), 675, yS);
			yS += 50;
			current = current.getNext();
		}

		g.drawString(String.valueOf(score), 800, 50);
		g.drawString(String.valueOf(player.getlifePoints()), 800, 150);
		g.drawString(String.valueOf(currentLevel), 800, 200);

		int xH = 740;
		for (int i = 0; i < player.getHearts(); i++) {
			g.drawImage(iconImage, xH, 237, 16, 16, null);
			xH += 20;
		}

		int xP = 740;
		PowerUps aux = stack.getFirst();
		for (int i = 0; i < stack.getSize(); i++) {
			if (aux != null) {
				if (aux.getType() == 1) {
					g.drawString("1", xP, 300);
				} else if (aux.getType() == 2) {
					g.drawString("2", xP, 300);
				} else {
					g.drawString("3", xP, 300);
				}
				if (aux.getNext() != null) {
					aux = aux.getNext();
					xP += 10;
				}
			}
		}

		g.dispose();
		bufferStrategy.show();
		fps++;
	}

	/**
	 * Will detect when a player press a key. And will update something
	 * 
	 * @param e
	 *            Is the event in the canvas
	 */
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
					if (stack.getFirst().getType() != 3) {
						typeBullet = stack.pop().getType();
					} else {
						player.setShield(true);
						stack.pop();
					}
				} else {
					typeBullet = 0;
				}
			} else if (key == KeyEvent.VK_SPACE && !is_shooting) {
				is_shooting = true;
				bullets.addBullet(new Bullet(player.getX(), player.getY(), typeBullet, this));
				new SoundEffectPlayer("Laser_Shoot11").play(-10);
			}
		}
	}

	/**
	 * Will detect when a play released a key. And will update something
	 * 
	 * @param e
	 *            Is the event in the canvas
	 */
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

	/**
	 * It returns the game sprite sheet to be use in another class
	 * 
	 * @return the game sprite sheet
	 */
	public BufferedImage getSpriteSheet() {
		return this.spriteSheet;
	}

	/**
	 * It returns the game list of enemies to be use in another class
	 * 
	 * @return the game list of enemies
	 */
	public EnemyBullets getEnemyBullets() {
		return this.enemyBullets;
	}

	/**
	 * It returns the game queue of enemies to be use in another class
	 * 
	 * @return the game queue of enemies
	 */
	public EnemyQueue getEnemyQueue() {
		return this.enemyQueue;
	}

	/**
	 * It returns the game player to be use in another class
	 * 
	 * @return the game player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * It returns the game list of bullets to be use in another class
	 * 
	 * @return
	 */
	public PlayerBullets getPlayerBullets() {
		return this.bullets;
	}

	/**
	 * It returns the game list of power ups to be use in another class
	 * 
	 * @return the game list of power ups
	 */
	public PowerUpsList getPowerUpsList() {
		return this.powerUpsList;
	}

	/**
	 * It returns the type of bullet to be use in another class
	 * 
	 * @return the type of bullet
	 */
	public int getTypeBullet() {
		return this.typeBullet;
	}

	/**
	 * It sets the type of bullet that the player will have
	 * 
	 * @param typeBullet
	 *            the type of bullet that the player will have
	 */
	public void setTypeBullet(int typeBullet) {
		this.typeBullet = typeBullet;
	}

	/**
	 * It returns the game score
	 * 
	 * @return the game score
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * It sets the game score
	 * 
	 * @param score
	 *            game score
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * It returns the game stack of power ups
	 * 
	 * @return the game stack of power ups
	 */
	public StackPowerUps getStack() {
		return this.stack;
	}

	/**
	 * Will make a push of the power up to the game stack of power up
	 * 
	 * @param powerup
	 *            the power up object
	 * @param type
	 *            the type of power up
	 */
	public void pushStack(PowerUps powerup, int type) {
		this.stack.push(powerup, type);
	}

	/**
	 * This method will communicate with android controller
	 */
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

							sendMessageToClient(String.valueOf(x) + ";" + String.valueOf(y) + ";" + androidShooting);

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

	/**
	 * This method will send a message to the android controller
	 * 
	 * @param message
	 */
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

	/**
	 * This method will receive a message to the android controller
	 */
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
				androidShooting = split[2];
				androidPowerUp = split[3];
			}

		} catch (IOException e) {
			System.out.println("Error. Reciving the Message Failed");
			e.printStackTrace();
		}
	}

	/**
	 * This method will update the game 60 times per second
	 */
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
		new Game(true);
	}

}