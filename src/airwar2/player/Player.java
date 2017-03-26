package airwar2.player;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import airwar2.graphics.Game;
import airwar2.spritesheet.SpriteSheet;

public class Player {
	
	private double x;
    private double y;
    
    private double velX = 0;
    private double velY = 0;
    
    private BufferedImage player;
    
    public Player(double x, double y, Game game) {
        this.x = x;
        this.y = y;
        
        SpriteSheet spriteSheet = new SpriteSheet(game.getSpriteSheet());
        this.player = spriteSheet.grabImage(1, 1, 32, 32);
    }
    
    public void tick() {
        x += velX;
        y += velY;
        
        if (x <= 0) {
            x = 0;
        }
        if (x >= 640 - 16) {
            x = 640 - 16;
        }
        if (y <= 0) {
            y = 0;
        }
        if (y >= 480 - 32) {
            y = 480 - 32;
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

}
