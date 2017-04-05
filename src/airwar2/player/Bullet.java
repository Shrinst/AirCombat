package airwar2.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.datastructures.EnemyList;
import airwar2.datastructures.PlayerBullets;
import airwar2.datastructures.PowerUpsList;
import airwar2.enemies.NodeJet;
import airwar2.graphics.Game;
import airwar2.powerups.PowerUps;
import airwar2.spritesheet.SpriteSheet;

public class Bullet {

	private double x;
	private double y;
	private int gameScore;
	private int type;

	private BufferedImage genericImage;
	private BufferedImage misilImage;

	private Bullet next;
	private Game game;

	private Rectangle playerBulletRectangle;
	private EnemyList enemyList;
	private PlayerBullets playerBullet;
	private PowerUpsList powerUpsList;
	private Player player;

	public Bullet(double x, double y, int type, Game game) {
		this.next = null;
		this.x = x;
		this.y = y;
		this.type = type;
		this.game = game;
		this.powerUpsList = game.getPowerUpsList();
		this.gameScore = this.game.getScore();

		this.enemyList = this.game.getEnemyQueue().getEnemyList();
		this.playerBullet = this.game.getPlayerBullets();
		this.player = game.getPlayer();

		SpriteSheet spriteSheet = new SpriteSheet(this.game.getSpriteSheet());
		this.genericImage = spriteSheet.grabImage(2, 1, 32, 32);
		this.misilImage = spriteSheet.grabImage(3, 1, 32, 32);

	}

	public void tick() {
		this.y -= 10;
		playerBulletRectangle = new Rectangle((int) x + 8, (int) y - 4, 16, 16);
		NodeJet temp = enemyList.getHead();
		for (int i = 0; i < enemyList.getSize(); i++) {
			if (playerBulletRectangle.intersects(temp.getBounds())) {
				if (this.y <= temp.getPosY()) {
					enemyList.delete(temp.getPosY());
					playerBullet.removeBullet(this);
					game.setScore(gameScore += 10);
				}
				if (Math.random() <= 0.30) {
					powerUpsList.addPowerUp(new PowerUps(temp.getPosX(), temp.getPosY(), 1, game));
				}
			}
			temp = temp.getNext();
		}
	}

	public void render(Graphics g) {
		if (type == 0) {
			g.drawImage(genericImage, (int) x + 8, (int) y - 4, 16, 16, null);
		}
		if (type == 1) {
			g.drawImage(misilImage, (int) x + 8, (int) y - 4, 16, 16, null);
		}
		//System.out.println(this.type);
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public double getY() {
		return this.y;
	}

	public Bullet getNext() {
		return this.next;
	}

	public void setNext(Bullet next) {
		this.next = next;
	}
}
