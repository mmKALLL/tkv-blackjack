package tkv_project.client;

abstract class UserInterface {
    
    private BlackjackController controller;
    
    // returns IP address/hostname and port, separated by a single comma.
    abstract protected String askServerDetails();
    
    abstract protected void startGame();
    
    /* USER-FACING ERROR HANDLING */
    abstract protected void handleException(Exception e);
}