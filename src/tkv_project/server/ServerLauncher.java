package tkv_project.server;

public class ServerLauncher {
    
    private static ConnectionManager connectionManager;
    private static ServerConstants serverConstants;
    
    public static void main(String[] args) {
        connectionManager = new ConnectionManager();
        serverConstants = new ServerConstants();
        connectionManager.setServerConstants(serverConstants);
    }
        
    
}