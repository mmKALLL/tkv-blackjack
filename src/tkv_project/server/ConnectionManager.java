package tkv_project.server;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class ConnectionManager extends Thread {
    
    private ServerConstants serverConstants;
    private ServerController serverController;
    private ServerSocket servSock;
    private Connection[] connections;
    private int currentConnections = 0;
    
    protected ConnectionManager(ServerConstants servConsts) {
        this.serverConstants = servConsts;
        this.connections = new Connection[this.serverConstants.MAX_CONNECTIONS + 1]; // +1 to prevent rare crashes from race conditions
        this.servSock = new ServerSocket(serverConstants.DEFAULT_PORT);
    }
    
    // Start listening to incoming connections in a new thread.
    public void run() {
        try {
            while (true) {
                if (currentConnections < this.serverConstants.MAX_CONNECTIONS) {
                    connections[currentConnections] = new Connection(this.servSock.accept(), generateID()).start();
                    this.currentConnections++;
                } else {
                    // TODO: refuse the incoming connection; this server is full
                }
            }
        } finally {
            servSock.close();
        }
    }
        
    protected void addConnection() {
        // TODO: Increase currentConnections count, generate ID, add a new Connection to the connections array.
    }
    
    protected void getConnections() {
        return this.connections;
    }
    
    protected int generateID() {
        // TODO: UUID or similar; less than 0.0001% collision chance for MAX_CONNECTIONS
        // use int or long
    }
    
    protected void setServerController(ServerController newServerController) {
        this.serverController = newServerController;
    }
    
    // A wrapper for a socket connecting a player, with information about their game's state.
    private class Connection extends Thread {
        private int ID;
        private Socket socket;
        
        public Connection(Socket socket, int initialID) {
            this.socket = socket;
            this.ID = initialID;
        }
        
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                System.out.println("Client connected, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
                out.println("Welcome to the tkv-blackjack game server, version " + this.ServerConstants.SERVER_VERSION + "!");
                
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
                    System.out.println("!!! Error when closing socket on Connection with ID: " this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + "!!!");
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