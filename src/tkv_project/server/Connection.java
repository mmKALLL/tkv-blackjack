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
            out.println("Welcome to the tkv-blackjack game server, version " + this.serverConstants.SERVER_VERSION + "!");
            
            // TODO: Try to add the player to the current game if it's not full.
            
            // Get messages from the client until they disconnect
            while (true) {
                String clientMessage = in.readLine();
                // TODO: Do things with serverController when client says stuff.
                if (clientMessage != null) {
                    if (clientMessage.contains("name")) {
                        serverController.setName(ID, clientMessage.split(":")[1]);
                    } else if (clientMessage.contains("hit")) {
                        serverController.handleHit(ID);
                        out.println(serverController.getSendableGameState());
                    } else if (clientMessage.contains("stand")) {
                        serverController.handleStand(ID);
                        out.println(serverController.getSendableGameState());
                    } else if (clientMessage.contains("quit")) {
                        out.println("Server says: Bye-bye!");
                        break;
                    }
                }
            }
            
        } catch (IOException e) {
            if (this.serverConstants.DEBUG) {
                System.out.println();
                e.printStackTrace();
            }
            System.out.println("Error with client, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                if (this.serverConstants.DEBUG) {
                    System.out.println();
                    e.printStackTrace();
                }
                System.out.println("!!! Error when closing socket on Connection with ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + "!!!");
            }
            System.out.println("Connection to client closed, ID: " + this.ID + ", address: " + socket.getRemoteSocketAddress().toString() + ".");
        }
    }
    
    public void setID(long newID) {
        this.ID = newID;
    }
    
    public long getID() {
        return this.ID;
    }
    
}