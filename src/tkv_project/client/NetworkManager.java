package tkv_project.client;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.InputStreamWriter;
import java.io.IOException;

/*
    NetworkManager handles the connectivity of a single client to the game server,
    and had methods for exchanging messages over the network.
 */
class NetworkManager extends Thread {
    
    private Socket serverConnection;
    private BufferedReader in;
    private BufferedWriter out;
    private BlackjackController controller;
    
    protected NetworkManager(BlackjackController control, InetSocketAddress sockaddr) {
        this.controller = control;
        try {
            serverConnection = new Socket(sockaddr.getAddress(), sockaddr.getPort());
            in = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            out = new PrintWriter(serverConnection.getOutputStream(), true);
        } catch (IOException e) {
            controller.handleServerConnectionFailure(e);
        }
    }
    
    private void receiveMessage() {
        // TODO
    }
    
    void sendMessage(String msg) {
        /* send msg over the socket */
        this.out.println(msg);
    }
    
    public void run() {
        // Wait for messages, etc...
        
        // TODO: Send a message to server and get the game's status. Then call controller.initializeGameState().
    }
    
}
