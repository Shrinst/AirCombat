package airwar2.graphics;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;

/**
 * 
 * @author Alejandro
 * @version 1.2.0
 *
 */
public class MainWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8391951082367936184L;
	private final int WIDTH = 800;
	private final int HEIGHT = 523;

	private MainPanel mainpanel;

	/**
	 * Setup the variables
	 */
	public MainWindow() {
		try {
			mainpanel = new MainPanel();

		} catch (Exception e) {
			System.out.println(e);
		}
		this.setTitle("AirWar");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		try {
			AudioInputStream audio = AudioSystem
					.getAudioInputStream(this.getClass().getResource("/airwar2/music/Epic1.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			// clip.start();

		} catch (Exception e) {
			System.out.println(e);
		}

		this.add(mainpanel);
		this.setVisible(true);
	}

	/**
	 * Run the class
	 * 
	 * @param args
	 *            console arguments
	 */
	public static void main(String[] args) {
		new MainWindow();
	}
}