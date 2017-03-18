package airwar.logic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener{
	
	private final static int numberKey = 120;
	private final boolean[] keyWords = new boolean[numberKey];
	
	public boolean UP;
	public boolean DOWN;
	public boolean LEFT;
	public boolean RIGHT;
	public boolean SPACE;
	
	public void update() {
		UP = keyWords[KeyEvent.VK_W];
		DOWN = keyWords[KeyEvent.VK_S];
		LEFT = keyWords[KeyEvent.VK_A];
		RIGHT = keyWords[KeyEvent.VK_D];
		SPACE = keyWords[KeyEvent.VK_SPACE];
	}

	@Override
	public void keyPressed(KeyEvent e) {		
		keyWords[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {		
		keyWords[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {		
		
	}	
}