package tkv_project.client;

public interface UserInterface {
    
    void startGame();
    
    // IP address/hostname and port, separated by a single comma.
    String askServerDetails();
    
    /* USER-FACING ERROR HANDLING */
    void handleException(Exception e);
}