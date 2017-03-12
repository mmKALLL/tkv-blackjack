package tkv_project.server;

import java.net.Socket;
import java.net.InetSocketAddress;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

class ConnectionManager {
    
    private ServerConstants serverConstants;
    private Connection[] connections;
    private int currentConnections = 0;
    
    protected ConnectionManager() {
        // TODO: Manage incoming connections, create sockets.
    }
    
    protected void setServerConstants(ServerConstants servConsts) {
        this.serverConstants = servConsts;
        connections = new Connection[this.serverConstants.MAX_CONNECTIONS];
    }
    
    protected void addConnection() {
        // TODO: Increase currentConnections count, add a new Connection to the connections array.
    }
    
    // A wrapper for a socket connecting a player, with information about their game's state.
    private class Connection extends Thread {
        
        protected Connection(InetSocketAddress sockaddr) {
            
        }
        
        public void run() {
            
        }
        
    }

}