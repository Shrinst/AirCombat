package airwar2.player;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import airwar2.graphics.Game;

/**
 * 
 * @author Emanuel
 * @version 1.2.0
 *
 */
public class KeyInput extends KeyAdapter {

	Game game;

	/**
	 * Setup the variables
	 * 
	 * @param game
	 *            the reference to the game
	 */
	public KeyInput(Game game) {
		this.game = game;
	}

	/**
	 * Will detect when a player press a key. And will update something
	 * 
	 * @param e
	 *            Is the event in the canvas
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		game.keyPressed(e);
	}

	/**
	 * Will detect when a player release a key. And will update something
	 * 
	 * @param e
	 *            Is the event in the canvas
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}

}
