package airwar2.enemies;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import airwar2.datastructures.EnemyBullets;
import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

public class NodeJet {

	private int posX;
	private int posY;
	private int posYF;
	private int armor;
	private int type;
	private int clavo;// variable creada en honor a Milton Villegas Lemus
	public Random rnd = new Random();

	private final BufferedImage kamikaze;
	private final BufferedImage jet;
	private final BufferedImage tower1;
	private final BufferedImage tower2;
	private EnemyBullets enemyBullets;
	private NodeJet next;
	private int pos;
	private Game game;

	public NodeJet(int valueX, int valueY, int tipo, Game game) {
		setNext(null);
		this.posX = valueX;
		this.posY = 0;
		this.game = game;
		this.type = tipo;
		this.enemyBullets = this.game.getEnemyBullets();
		if (type > 17) {
			posYF = rnd.nextInt(500 - 64);
		}
		SpriteSheet spriteSheet = new SpriteSheet(this.game.getSpriteSheet());
		this.kamikaze = spriteSheet.grabImage(2, 1, 32, 32);
		this.jet = spriteSheet.grabImage(1, 1, 32, 32);
		this.tower1 = spriteSheet.grabImage(3, 1, 32, 32);
		this.tower2 = spriteSheet.grabImage(4, 1, 32, 32);
	}

	public NodeJet getNext() {
		return next;
	}

	public int getpos() {
		return pos;
	}

	public void setNext(NodeJet next) {
		this.next = next;
	}

	public void tick(int targetX, int targetY) {

		if (type < 5) {
			if (targetY > this.posY) { // if the bullet is above the player´s
										// position
				if (targetY + 16 > this.posY) {
					this.cambiarPosY(true);
				}
				if (targetY + 16 < this.posY) {
					this.cambiarPosY(true);
				}
				if (targetX + 8 > this.posX) {
					this.cambiarPosX(true);
				}
				if (targetX + 8 < this.posX) {
					this.cambiarPosX(false);
				}
			} else if (targetY <= this.posY) { // if the bullet is below the
												// player´s position
				this.cambiarPosY(true);
			} else {
				if (this.posY < posYF) {
					this.cambiarPosY(true);
				}
			}
		}
		if (type < 18 && type > 4) {
			int x = 2;
			this.cambiarPosY(true);
			int value = rnd.nextInt(x);
			if (value < (x / 2)) {
				if (this.getPosX() < 800 - 32) {
					this.cambiarPosX(true);

				}
			} else {
				if (this.getPosX() > 0) {
					this.cambiarPosX(false);
				}
			}
		}
		if (type > 17) {
			if (this.posY < posYF) {
				this.cambiarPosY(true);
			}
		}

	}

	public void render(Graphics g) {
		if (type < 5) { // KAMIKAZE
			armor = 0;
			g.drawImage(kamikaze, this.posX, this.posY, 32, 64, null);
		}
		if (type < 13 && type > 4) { // JET
			armor = 1;
			g.drawImage(jet, this.posX, this.posY, 32, 64, null);
		}
		if (type == 18) { // TOWER 1
			armor = 2;
			g.drawImage(tower1, this.posX, this.posY, 32, 64, null);
		}
		if (type == 19) { // TOWER 2
			armor = 3;
			g.drawImage(tower2, this.posX, this.posY, 32, 64, null);
		}
	}

	public void shoot() {
		if (clavo == 120) {
			enemyBullets.addBullet(new EnemyBullet(this.getPosX(), this.getPosY(), game));
			clavo = 0;
		} else {
			clavo++;
		}
	}

	private void cambiarPosY(boolean Flag) {
		if (Flag == true) {
			this.posY += 2;

		} else {
			this.posY -= 2;
		}
	}

	private void cambiarPosX(boolean Flag) {
		if (Flag == true) {
			this.posX += 2;

		} else {
			this.posX -= 2;
		}
	}

	public int getPosX() {
		return this.posX;
	}

	public int getPosY() {
		return this.posY;
	}

	public void setPosX(int x) {
		this.posX = x;
	}

	public void setPosY(int y) {
		this.posY = y;
	}

}