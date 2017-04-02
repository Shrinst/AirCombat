package airwar2.player;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import airwar2.datastructures.EnemyList;
import airwar2.enemies.NodeJet;
import airwar2.graphics.Game;
import airwar2.playmusic.SoundEffectPlayer;
import airwar2.spritesheet.SpriteSheet;

public class Player {

	private double x;
	private double y;

	private double velX = 0;
	private double velY = 0;

	private BufferedImage player;

	private Rectangle playerRectangle;

	private EnemyList enemyList;

	public Player(double x, double y, Game game) {
		this.x = x;
		this.y = y;

		this.enemyList = game.getEnemyQueue().getEnemyList();

		SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
		this.player = spriteSheet.grabImage(1, 1, 32, 32);
	}

	public void tick() {
		x += velX;
		y += velY;

		playerRectangle = this.getBounds();

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

		NodeJet temp = enemyList.getHead();
		for (int i = 0; i < enemyList.getSize(); i++) {

			if (playerRectangle.intersects(temp.getBounds())) {
				if (this.y <= temp.getPosY()) {
					if (temp.getType() < 5) {
						enemyList.delete(temp.getPosY());						
						new SoundEffectPlayer("Allahu_Akbar").play(-10);
					} else {
						enemyList.delete(temp.getPosY());
					}
				}
			}
			temp = temp.getNext();
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
