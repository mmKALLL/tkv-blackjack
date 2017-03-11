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
    
    protected NetworkManager(InetSocketAddress sockaddr) {
        /* initialize socket into ipaddr, in port x */
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
    
    public void run() {
        // Creating class should call NetworkManagerInstance.start() to execute this in a new thread.
        // Wait for messages, etc...
        
    }
    
}