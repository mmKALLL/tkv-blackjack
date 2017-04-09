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
                    connections[currentConnections] = new Connection(this.servSock.accept(), generateID(), this.serverConstants, this.serverController);
                    connections[currentConnections].start();
                    this.currentConnections++;
                } else {
                    // TODO: refuse the incoming connection; this server is full
                }
            }
        } catch (IOException e) {
            if (this.serverConstants.DEBUG) {
                System.out.println();
                e.printStackTrace();
            }
            System.out.println("Error occurred when accepting a new client to the server socket.");
        } finally {
            try {
                servSock.close();
            } catch (IOException e) {
                if (this.serverConstants.DEBUG) {
                    System.out.println();
                    e.printStackTrace();
                }
                System.out.println("!!! Error when closing servSock in ConnectionManager!!!");
            }
            System.out.println("Server ConnectionManager closed.");
        }
    }
    
    protected Connection[] getConnections() {
        return this.connections;
    }
    
    protected long generateID() {
        UUID id = UUID.randomUUID();
        return Math.abs(id.getMostSignificantBits());
    }
    
    protected void setServerController(ServerController newServerController) {
        this.serverController = newServerController;
    }

}