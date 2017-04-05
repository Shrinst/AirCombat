package airwar2.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import airwar2.spritesheet.SpriteSheet;

public class LevelHandler {
	
	private int currentBackgroundIndex;	
	private Game game;	
	private BufferedImage backgroundImage;	
	private boolean changeLevelFlag;
	SpriteSheet spriteSheet;
	
	public LevelHandler(Game game) {
		this.game = game;
		this.currentBackgroundIndex = 3;
		spriteSheet = new SpriteSheet(game.getSpriteSheet());				
	}	
	
	public void tick() {
		 changeLevelFlag = game.getEnemyQueue().isEmpty();
	}
	
	public void render(Graphics g) {		
		if (changeLevelFlag) {
			currentBackgroundIndex++;
			System.out.println("Cambio Nivel");
			changeLevelFlag = false;			
		}
		this.backgroundImage = spriteSheet.grabImage(currentBackgroundIndex, 1, 32, 32);	
		g.drawImage(backgroundImage, 0, 0, game.getWidth(), game.getHeight(), null);		
	}
}
