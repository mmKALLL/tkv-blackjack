package tkv_project.client;

class BlackjackController {
    
    public static final String VERSION = "0.1";
    public static final boolean DEBUG = true;
    
    private static String[][] gameState;
    protected static boolean gameStateBuilt = false;
    
    
    protected BlackjackController() {
        
    }
    
    void initializeGameState() {
        // initialize gameState depending on server's maximum player amount, etc
        
        // ...
        
        gameStateBuilt = true;
    }
    
    protected void playHit() {
        // Player hits, do something with NetworkManager
        
    }
    
    protected void playStand() {
        
    }
    
    // A non-separated string where, for each card, there is a card number/symbol followed by suit symbol.
    // e.g. "", "6s", "Jh", "As", "5s5c", "9cQhAs5d", "9c9c9c9c9c9c9c".
    protected String getCurrentPlayerCards() {
        
    }
    
    // An array, where in index [0] is the current player's cards, and the other players' after that.
    protected String[] getAllPlayerCards() {
        
    }
    
    // No assumptions can be made on the game state's format at this point.
    protected String[][] getGameState() {
        return gameState;
    }
    
}