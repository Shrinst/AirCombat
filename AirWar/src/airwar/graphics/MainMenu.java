package airwar.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import airwar.server.Server;

public class MainMenu extends JPanel implements ActionListener{

	/**
	 * 
	 */	
	private static final long serialVersionUID = -294599945858489594L;
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	private JButton[] buttons = new JButton[3];
	private Image img;
	
	public MainMenu(Image img) {
		this.img = img;
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.addButtons();
		this.setVisible(true);		
	}	
	
	// Setup the Menu Buttons
	private void addButtons() {
		int posX = 100;
		int posY = 250;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setSize(150, 50);
			buttons[i].setLocation(posX, posY);
			buttons[i].setBackground(Color.DARK_GRAY);
			buttons[i].setForeground(Color.CYAN);
			buttons[i].setFont(new Font("Highlander", Font.PLAIN, 20));
			buttons[i].addActionListener(this);
			this.add(buttons[i]);
			posY += 100;
		}
		buttons[0].setText("Inicio Juego");
		buttons[1].setText("Estadisticas");
		buttons[2].setText("Creditos");
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		if (e.getSource() == buttons[0]) {			
			this.setVisible(false);
			((Window) getRootPane().getParent()).dispose();
			new GameWindow();			
			new Server();
		}
	}
}
