package tkv_project.client;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.SocketException;

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
            try {
                serverConnection.close();
            } catch (IOException e2) {
                System.out.println("!!! Error when closing serverConnection in NetworkManager!!!");
                controller.handleServerConnectionFailure(e2);
            }
        }
    }
    
    protected void sendMessage(String msg) {
        /* send msg over the socket */
        this.out.println(msg);
    }
    
    public void run() {
        // TODO: Send a message to server and get the game's status. Then call controller.initializeGameState().
        
        try {
            out.println("update");
            while (true) {
                String message = in.readLine();
                if (message != null && !message.trim().isEmpty()) {
                    if (message.startsWith("!!!gsdata!!!")) {
                        String[][] newGameState = parseGameState(message.substring(12));
                        controller.updateGameState(newGameState);
                    }
                } else {
                    System.out.println("Reading message failed at NetworkManager!");
                }
                // TODO: Sleep and handle InterruptedException; otherwise this loop can eat processors
            }
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

    private String[][] parseGameState(String message) {
        System.out.println(message);
    	String[] messageInfo = message.split("#");
    	int numberOfPlayers = messageInfo.length;
    	String[][] newGameState = new String[numberOfPlayers][4];

    	for (int i = 0; i < numberOfPlayers; i++) {
    		String[] playerInfo = messageInfo[i].split("&");

    		for (int j = 0; j < 4; j++) {
    			newGameState[i][j] = playerInfo[j];
    		}
    	}

    	return newGameState;
    }
    
}
