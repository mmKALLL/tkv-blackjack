package tkv_project.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.UUID;

class ConnectionManager extends Thread {
    
    private ServerConstants serverConstants;
    private ServerController serverController;
    private ServerSocket servSock;
    private Connection[] connections;
    private int currentConnections = 0;
    
    protected ConnectionManager(ServerConstants servConsts) {
        this.serverConstants = servConsts;
        this.connections = new Connection[this.serverConstants.MAX_CONNECTIONS + 1]; // +1 to prevent rare crashes from race conditions
    }
    
    // Start listening to incoming connections in a new thread.
    public void run() {
        try {
            this.servSock = new ServerSocket(serverConstants.DEFAULT_PORT);
            while (true) {
                if (currentConnections < this.serverConstants.MAX_CONNECTIONS) {
                    connections[currentConnections] = new Connection(this.servSock.accept(), generateID(), this.serverConstants);
                    connections[currentConnections].start();
                    this.currentConnections++;
                } else {
                    // TODO: refuse the incoming connection; this server is full
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred when accepting a new client to the server socket.");
        } finally {
            try {
                servSock.close();
            } catch (IOException e) {
                System.out.println("!!! Error when closing servSock in ConnectionManager!!!");
            }
            System.out.println("Server ConnectionManager closed.");
        }
    }
        
    protected void addConnection() {
        // TODO: Increase currentConnections count, generate ID, add a new Connection to the connections array.
    }
    
    protected Connection[] getConnections() {
        return this.connections;
    }
    
    protected int generateID() {
        // TODO: UUID or similar; less than 0.0001% collision chance for MAX_CONNECTIONS
        // use int or long
        return currentConnections + 1;
    }
    
    protected void setServerController(ServerController newServerController) {
        this.serverController = newServerController;
    }
    
    // A wrapper for a socket connecting a player, with information about their game's state.
    private class Connection extends Thread {
        private int ID;
        private Socket socket;
        private ServerConstants serverConstants;
        
        public Connection(Socket socket, int initialID, ServerConstants servConsts) {
            this.socket = socket;
            this.ID = initialID;
            this.serverConstants = servConsts;
        }
        
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                System.out.println("Client connected, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
                out.println("Welcome to the tkv-blackjack game server, version " + this.serverConstants.SERVER_VERSION + "!");
                
                // TODO: Try to add the player to the current game if it's not full.
                
                // Get messages from the client until they disconnect
                while (true) {
                    String clientMessage = in.readLine();
                    // TODO: Do things with serverController when client says stuff.
                }
                
            } catch (IOException e) {
                System.out.println("Error with client, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("!!! Error when closing socket on Connection with ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + "!!!");
                }
                System.out.println("Connection to client closed, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
            }
        }
        
        public void setID(int newID) {
            this.ID = newID;
        }
        
        public int getID() {
            return this.ID;
        }
        
    }

}