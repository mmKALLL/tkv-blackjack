package tkv_project.client;

import java.net.InetSocketAddress;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Launcher {
	
	private static TextUI UI;
	private static BlackjackController controller;
	private static NetworkManager networkManager;
	
	public static void main(String[] args) {
		
		// Parse arguments
		// TODO
		
		// It is important that the following steps are in the following order.
		controller = new BlackjackController(UI, networkManager);
		UI = new TextUI(controller);
		
		// Build the SocketAddress and networkManager
		boolean ok = false;
		while(!ok) {
			String serverDetails = UI.askServerDetails();
			try {
				InetSocketAddress sockaddr = new InetSocketAddress(
						InetAddress.getByName(serverDetails.split(",")[0]),
						Integer.parseInt(serverDetails.split(",")[1]));
				networkManager = new NetworkManager(controller, sockaddr);
				ok = true;
			} catch (UnknownHostException e) {
				UI.handleException(e); // Not very nice to UI devs; limits flexibility
			}
		}
		
		networkManager.start(); // starts networkManager.run() in a separate thread
		UI.startGame();
		
	}
	
}
