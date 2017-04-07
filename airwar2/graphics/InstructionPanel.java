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
public class InstructionPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 857714817874631028L;

	private BufferedImage image;
	private JButton button;

	/**
	 * Setup the JPanel properties
	 */
	public InstructionPanel() {
		try {
			this.image = ImageIO.read(this.getClass().getResource("/airwar2/images/sidePlane1.jpg"));
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
		button = new JButton();
		button.setSize(200, 50);
		button.setLocation(500, 50);
		button.setBackground(Color.DARK_GRAY);
		button.setForeground(Color.CYAN);
		button.setFont(new Font("Arial", Font.PLAIN, 20));
		button.setText("Atrás");
		button.addActionListener(this);
		this.add(button);
	}

	/**
	 * Draw the background image
	 */
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, null);
	}

	/**
	 * Check if the button is pressed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == button) {
			this.setVisible(false);
			((Window) getRootPane().getParent()).add(new MainPanel());
		}
	}
}
