package tkv_project.client;

class BlackjackController {
    
    public static final String CLIENT_VERSION = "0.4";
    public static final int INIT_TIMEOUT = 10000; // milliseconds
    public static final boolean DEBUG = true;
    
    private TextUI UI;
    private NetworkManager networkManager;
    private String[][] gameState;
    protected boolean gameStateBuilt = false;
    
    
    protected BlackjackController(TextUI ui, NetworkManager netManager) {
        this.UI = ui;
        this.networkManager = netManager;
    }
    
    void initializeGameState()/*arguments*/ {
        // initialize gameState depending on server's maximum player amount, etc
        
        // ...
        
        gameStateBuilt = true;
    }
    
    // Called whenerver server has sent an update that was read by NetworkManager.
    void updateGameState() {
        // TODO: Update gameState, tell UI to print the game screen.
    }
    
    protected void playHit() {
        // TODO: Player hits, do something with NetworkManager
        
    }
    
    protected void playStand() {
        
    }
    
    // A non-separated string where, for each card, there is a card number/symbol followed by suit symbol.
    // e.g. "", "6s", "Jh", "As", "5s5c", "9cQhAs5d", "9c9c9c9c9c9c9c".
    protected String getCurrentPlayerCards() {
        
        return "BlackjackController.getCurrentPlayerCards is unimplemented.";
    }
    
    // An array, where in index [0] is the current player's cards, and the other players' after that.
    protected String[] getAllPlayerCards() {
        return (String[]) {"BlackjackController.getAllPlayerCards is unimplemented."}
    }
    
    // No assumptions can be made on the game state's format at this point.
    protected String[][] getGameState() {
        return gameState;
    }
    
}