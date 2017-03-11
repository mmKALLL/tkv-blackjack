package tkv_project.client;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
    NetworkManager handles the connectivity of a single client to the game server,
    and had methods for exchanging messages over the network.
 */
class NetworkManager extends Thread {
    
    private Socket serverConnection;
    private BufferedReader in;
    private Controller controller;
    
    protected NetworkManager(Controller control, InetSocketAddress sockaddr) {
        this.controller = control;
        serverConnection = new Socket(sockaddr.getAddress(), sockaddr.getPort());
        in = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
    }
    
    private receiveMessage() {
        // TODO
    }
    
    void sendMessage(String msg) {
        /* send msg over the socket */
        // TODO
    }
    
    protected void run() {
        // Wait for messages, etc...
        
        // TODO: Send a message to server and get the game's status. Then call controller.initializeGameState().
    }
    
}