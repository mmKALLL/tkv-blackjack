package tkv_project.server;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class ConnectionManager {
    
    private ServerConstants serverConstants;
    private ServerController serverController;
    private Connection[] connections;
    private int currentConnections = 0;
    
    protected ConnectionManager(ServerConstants servConsts) {
        // TODO: Manage incoming connections, create sockets.
        this.serverConstants = servConsts;
        connections = new Connection[this.serverConstants.MAX_CONNECTIONS];
    }
        
    protected void addConnection() {
        // TODO: Increase currentConnections count, generate ID, add a new Connection to the connections array.
    }
    
    protected void getConnections() {
        return this.connections;
    }
    
    protected void generateID() {
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
        
        protected Connection(Socket socket, int initialID) {
            this.socket = socket;
            this.ID = initialID;
        }
        
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                
                out.println("Welcome to the tkv-blackjack game server, version " + this.ServerConstants.SERVER_VERSION + "!");
                
                // Get messages from the client until they disconnect
                while (true) {
                    String clientMessage = in.readLine();
                    // TODO: Do things when client says stuff.
                }
                
            } catch (IOException e) {
                
            } finally {
                try {
                    
                } catch (IOException e) {
                    
                }
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