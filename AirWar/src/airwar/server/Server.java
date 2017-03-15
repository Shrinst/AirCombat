package airwar.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket serversocket;
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private final int PORT = 2222;
	static String data ="algo";
	private volatile boolean shouldRun = true;	

	public Server() {
		try {
			System.out.println("Server Starting at Port Number: " + PORT);
			serversocket = new ServerSocket(PORT);

			// Client Connect
			System.out.println("Waiting for Clients to Connect...");
			socket = serversocket.accept();
			System.out.println("A Client has Connected");
			

			while (shouldRun) {	
				
				reciveMessage();				

				sendMessageToClient("Hola");

			}
			sendMessageToClient("Adios");
			sendMessageToClient("Air");
			sendMessageToClient("War");
			sendMessageToClient("Mola");

			System.out.println("The Server has Ended");
			serversocket.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendMessageToClient(String message) {
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			System.out.println("Error. Sending the Message Failed");
			e.printStackTrace();
		}
	}

	public void reciveMessage() {
		//String data;
		String[] split;
		try {

			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			data = bufferedReader.readLine();
			split = data.split(";");
			if (data != null) {
				//System.out.println("Message from the Client: " + data);							
			}			

		} catch (IOException e) {
			System.out.println("Error. Reciving the Message Failed");
			e.printStackTrace();			
		}
	}	

	public static void main(String[] args) {
		new Server();
	}

}
