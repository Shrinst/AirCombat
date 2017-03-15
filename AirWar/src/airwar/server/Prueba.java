package airwar.server;

public class Prueba {
	
	private Server server;
	private static Thread thread;
	private boolean hola;
	
	public Prueba() {
		server = new Server();
		this.startI();
	}
	
	private void startI() {
		System.out.println(Server.data);	
	}

	
	
	public static void main(String[] args) {
		new Prueba();
	}
}
