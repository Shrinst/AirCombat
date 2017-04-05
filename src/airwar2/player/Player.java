package airwar2.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.datastructures.EnemyList;
import airwar2.datastructures.PowerUpsList;
import airwar2.datastructures.Stack;
import airwar2.enemies.NodeJet;
import airwar2.graphics.Game;
import airwar2.playmusic.SoundEffectPlayer;
import airwar2.powerups.PowerUps;
import airwar2.spritesheet.SpriteSheet;

public class Player {

	private double x;
	private double y;

	private double velX = 0;
	private double velY = 0;

	private BufferedImage player;

	private Rectangle playerRectangle;
	private EnemyList enemyList;
	private PowerUpsList powerUpsList;
	private Game game;
	private Stack stack;

	public Player(double x, double y, Game game) {
		this.x = x;
		this.y = y;

		this.game = game;
		this.enemyList = this.game.getEnemyQueue().getEnemyList();
		this.powerUpsList = this.game.getPowerUpsList();
		this.stack = this.game.getStack();

		SpriteSheet spriteSheet = new SpriteSheet(this.game.getSpriteSheet());
		this.player = spriteSheet.grabImage(1, 1, 32, 32);
	}

	public void tick() {
		x += velX;
		y += velY;

		this.playerRectangle = this.getBounds();

		if (x <= 0) {
			x = 0;
		}
		if (x >= 640 - 16) {
			x = 640 - 16;
		}
		if (y <= 0) {
			y = 0;
		}
		if (y >= 610 - 32) {
			y = 610 - 32;
		}
		collideWithEnemy();
		collideWithPowerUp();
	}

	private void collideWithEnemy() {
		NodeJet temp = enemyList.getHead();
		for (int i = 0; i < enemyList.getSize(); i++) {
			if (playerRectangle.intersects(temp.getBounds())) {
				if (this.y <= temp.getPosY()) {
					if (temp.getType() < 5) {
						new SoundEffectPlayer("Allahu_Akbar").play(-10);
					}
					enemyList.delete(temp.getPosY());
				}
			}
			temp = temp.getNext();
		}
	}

	public void collideWithPowerUp() {
		PowerUps temp = powerUpsList.getHead();
		for (int i = 0; i < powerUpsList.getSize(); i++) {
			if (temp != null) {
				if (playerRectangle.intersects(temp.getBounds())) {
					if (this.y <= temp.getPosY()) {
						powerUpsList.removePowerUp(temp.getPosY());
						game.pushStack(temp, temp.getType());						
						temp = temp.getNext();
					}
				}
			} else {
				break;
			}
		}
	}

	public void render(Graphics g) {
		g.drawImage(this.player, (int) this.x, (int) this.y, null);		
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	public Rectangle getBounds() {
		return new Rectangle((int) this.getX(), (int) this.getY(), player.getWidth(null), player.getHeight(null));
	}

}
