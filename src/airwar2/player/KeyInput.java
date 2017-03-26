package airwar2.player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import airwar2.graphics.Game;

public class KeyInput extends KeyAdapter{
	
	Game game;
    
    public KeyInput(Game game) {
        this.game = game;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        game.keyPressed(e);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        game.keyReleased(e);
    }

}
