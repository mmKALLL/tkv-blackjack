package tkv_project.server;

public class ServerLauncher {
    
    private static ConnectionManager connectionManager;
    private static ServerConstants serverConstants;
    private static ServerController serverController;
    
    public static void main(String[] args) {
        serverConstants = new ServerConstants();
        
        System.out.println("launching tkv-blackjack server version " + serverConstants.SERVER_VERSION + "...");
        
        connectionManager = new ConnectionManager(serverConstants);
        serverController = new ServerController(serverConstants);
        
        serverController.setConnectionManager(connectionManager);
        connectionManager.setServerController(serverController);
        
        connectionManager.start();
        
        serverController.initialize();
    }
        
    
}