package airwar2.graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class MainWindow extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8391951082367936184L;
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private ImageIcon mainBackground = new ImageIcon(this.getClass().getResource("/airwar/images/FondolAirWar.png"));
	private ImageIcon windowIcon = new ImageIcon(this.getClass().getResource("/airwar/images/IconoAirWar.png"));
	private MainPanel mainmenu;
	
	public MainWindow() {
		mainmenu = new MainPanel(mainBackground.getImage());
		this.setTitle("AirWar");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);	
		this.setIconImage(windowIcon.getImage());
		this.add(mainmenu);
		this.setVisible(true);
	}

	public static void main(String[] args) {	
		new MainWindow();
	}

}
