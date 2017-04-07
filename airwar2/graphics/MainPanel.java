package airwar2.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author Alejandro, Fabiola
 * @version 1.1.0
 *
 */
public class MainPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -294599945858489594L;
	private final int WIDTH = 800;
	private final int HEIGHT = 523;
	private JButton[] buttons = new JButton[3];
	private BufferedImage img;

	/**
	 * Setup the JPanel properties
	 */
	public MainPanel() {
		try {
			this.img = ImageIO.read(this.getClass().getResource("/airwar2/images/sidePlane1.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setSize(WIDTH, HEIGHT);
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		this.addButtons();
		this.setVisible(true);
	}

	/**
	 * Add the buttons to the panel
	 */
	private void addButtons() {
		int posX = 100;
		int posY = 400;
		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			buttons[i].setSize(200, 50);
			buttons[i].setLocation(posX, posY);
			buttons[i].setBackground(Color.DARK_GRAY);
			buttons[i].setForeground(Color.CYAN);
			buttons[i].setFont(new Font("Arial", Font.PLAIN, 20));
			buttons[i].addActionListener(this);
			this.add(buttons[i]);
			posX += 200;
		}
		buttons[0].setText("Instrucciones");
		buttons[1].setText("Jugar en Android");
		buttons[2].setText("Jugar en Desktop");
	}

	/**
	 * Draw the background image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
		g.setFont(new Font("Arial", Font.PLAIN, 30));
		g.drawString("Bienvenidos a...", 450, 150);
		g.drawString("AIRWAR", 500, 200);
	}

	/**
	 * Check if the button is pressed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == buttons[1]) {
			this.setVisible(false);
			((Window) getRootPane().getParent()).dispose();
			new Game(false);
		} else if (e.getSource() == buttons[2]) {
			this.setVisible(false);
			((Window) getRootPane().getParent()).dispose();
			new Game(true);
		} else {
			this.setVisible(false);
			((Window) getRootPane().getParent()).add(new InstructionPanel());
		}
	}
}
