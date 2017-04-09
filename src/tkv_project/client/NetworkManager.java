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
        // Loop and get updates for gameState from the server.
        try {
            while (true) {
                out.println("update");
                String message = in.readLine();
                if (message != null && !message.trim().isEmpty()) {
                    if (message.startsWith("!!!gsdata!!!")) {
                        String[][] newGameState = parseGameState(message.substring(12));
                        controller.updateGameState(newGameState);
                    }
                } else {
                    System.out.println("Reading message failed at NetworkManager!");
                    throw new IOException("Server returned a stream of \\n");
                }
                try {
                    Thread.sleep(controller.NETWORK_UPDATE_INTERVAL);
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                    if (controller.DEBUG) {
                        System.out.println("DEBUG: NetworkManager update thread interruped.");
                    }
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
        if (this.controller.VERBOSE_MESSAGE_DEBUG) {
            System.out.println(message);
        }
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
