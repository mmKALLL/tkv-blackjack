package tkv_project.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.UUID;

// A wrapper for a socket connecting a player, with information about their game's state.
class Connection extends Thread {
    private long ID;
    private Socket socket;
    private ServerConstants serverConstants;
    private ServerController serverController;
    private long startTime = System.currentTimeMillis();
    private long updates = 0;
    private boolean nameRegistered = false;
    
    public Connection(Socket socket, long initialID, ServerConstants servConsts, ServerController servCtrl) {
        this.socket = socket;
        this.ID = initialID;
        this.serverConstants = servConsts;
        this.serverController = servCtrl;
    }
    
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            System.out.println("Client connected, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
            out.println("Welcome to the tkv-blackjack server called " + this.serverConstants.SERVER_NAME + ", version " + this.serverConstants.SERVER_VERSION + "!");
            
            
            // Get messages from the client until they disconnect
            while (true) {
                String clientMessage = in.readLine();
                if (serverConstants.VERBOSE_MESSAGE_DEBUG) {
                    System.out.println("\t" + clientMessage);
                }
                if (clientMessage != null) {
                    
                    if (clientMessage.contains("name")) {
                        if (!nameRegistered) {
                            // Try to add the player to the current game
                            int addState = serverController.addPlayer(ID, clientMessage.split(":")[1]);
                            nameRegistered = true;
                            // FIXME: Return values not used
                        } else {
                            System.out.println("Client sent multiple name messages; ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
                        }
                        out.println(serverController.getSendableGameState());
                        
                    } else if (clientMessage.contains("hit")) {
                        serverController.handleHit(ID);
                        out.println(serverController.getSendableGameState());
                        
                    } else if (clientMessage.contains("stand")) {
                        serverController.handleStand(ID);
                        out.println(serverController.getSendableGameState());
                        
                    } else if (clientMessage.contains("update")) {
                        this.updates++; // rudimentary system for managing overloading clients
                        if (this.updates / (1 + (System.currentTimeMillis() + 1 - startTime) / 1000) >= serverConstants.MAX_UPDATES_PER_SECOND) {
                            if (serverConstants.DEBUG) {
                                System.out.println("Client asked for too many updates, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
                            }
                            out.println("too many updates requested from server");
                            this.close();
                        } else {
                            if (serverConstants.VERBOSE_MESSAGE_DEBUG) {
                                System.out.println("Sending update for client, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
                            }
                            out.println(serverController.getSendableGameState());
                        }
                        
                    } else if (clientMessage.contains("quit")) {
                        out.println("Server says bye-bye! Thanks for playing.");
                        this.close();
                    }
                }
                // TODO: Sleep and handle InterruptedException; otherwise this loop can eat processors
            }
            
        } catch (IOException e) {
            if (this.serverConstants.DEBUG) {
                System.out.println();
                e.printStackTrace();
            }
            System.out.println("Error with client, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
        } finally {
            this.close();
        }
    }
    
    private void close() {
        try {
            socket.close();
        } catch (IOException e) {
            if (this.serverConstants.DEBUG) {
                System.out.println();
                e.printStackTrace();
            }
            System.out.println("!!! Error when closing socket on Connection with ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + "!!!");
        }
        System.out.println("Connection to client closed, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".\n\n");
    }
    
    public void setID(long newID) {
        this.ID = newID;
    }
    
    public long getID() {
        return this.ID;
    }
    
}