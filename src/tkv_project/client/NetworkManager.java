package tkv_project.client;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;

/*
    NetworkManager handles the connectivity of a single client to the game server,
    and had methods for exchanging messages over the network.
 */
class NetworkManager extends Thread {
    
    private Socket serverConnection;
    private BufferedReader in;
    private PrintWriter out;
    private BlackjackController controller;
    
    protected NetworkManager(BlackjackController control, InetSocketAddress sockaddr) {
        this.controller = control;
        try {
            serverConnection = new Socket(sockaddr.getAddress(), sockaddr.getPort());
            in = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
            out = new PrintWriter(serverConnection.getOutputStream(), true);
        } catch (IOException e) {
            controller.handleServerConnectionFailure(e);
        } finally {
            try {
                serverConnection.close();
            } catch (IOException e) {
                System.out.println("!!! Error when closing serverConnection in NetworkManager!!!");
            }
        }
    }
    
    private void receiveMessage() {
        String message;
        try {
        	message = in.readLine();
        	// TODO: implement once known in which format data is sent
        } catch (IOException e) {
        	controller.handleServerConnectionFailure(e);
    	} finally {
            try {
                serverConnection.close();
            } catch (IOException e) {
                System.out.println("!!! Error when closing serverConnection in NetworkManager!!!");
            }
        }
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
