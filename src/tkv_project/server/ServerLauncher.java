package tkv_project.server;

public class ServerLauncher {
    
    private static ConnectionManager connectionManager;
    private static ServerConstants serverConstants;
    private static ServerController serverController;
    
    public static void main(String[] args) {
        serverConstants = new ServerConstants();
        connectionManager = new ConnectionManager(serverConstants);
        serverController = new ServerController(serverConstants);
        
        serverController.setConnectionManager(connectionManager);
        connectionManager.setServerController(serverController);
    }
        
    
}