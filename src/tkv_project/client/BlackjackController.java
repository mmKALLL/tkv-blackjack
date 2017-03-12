package tkv_project.client;

class BlackjackController {
    
    public static final boolean DEBUG = true;
    
    public static final String CLIENT_VERSION = "0.5.2";
    public static final int GAMESTATE_INIT_TIMEOUT = 10000; // milliseconds
    public static final String DEFAULT_HOSTNAME = "localhost";
    public static final String DEFAULT_PORT = "47855";
    
    private UserInterface UI;
    private NetworkManager networkManager;
    private String[][] gameState;
    protected boolean gameStateBuilt = false;
    protected boolean canPlay = false;
    
    private String username = "Player 1";
    
    protected BlackjackController() {
        
    }
    
    protected BlackjackController(UserInterface ui, NetworkManager netManager) {
        this.UI = ui;
        this.networkManager = netManager;
    }
    
    void initializeGameState()/*arguments*/ {
        // TODO: initialize gameState depending on server's maximum player amount, etc
        // Could also use ArrayList<String>; it might be better actually.
        
        // ...
        
        gameStateBuilt = true;
    }
    
    // Called whenerver server has sent an update that was read by NetworkManager.
    void updateGameState() {
        // TODO: Update gameState, tell UI to print the game screen.
    }
    
    // Called by NetworkManager
    void startNewRound() {
        for (int i; i < gameState.length(); i++) {
            gameState[i] = {};
        }
        this.canPlay = true;
    }
    
    protected void handleServerConnectionFailure(Exception e) {
        if (this.DEBUG) {
            e.printStackTrace();
        }
        UI.handleException(e);
    }
    
    protected void playHit() {
        // TODO: Player hits, do something with NetworkManager
        
    }
    
    protected void playStand() {
        
    }
    
    // A non-separated string where, for each card, there is a card number/symbol followed by suit symbol.
    // e.g. "", "6s", "Jh", "As", "5s5c", "9cQhAs5d", "9c9c9c9c9c9c9c".
    protected String getCurrentPlayerCards() {
        // TODO
        return "BlackjackController.getCurrentPlayerCards is unimplemented.";
    }
    
    // An array, where in index [0] is the current player's cards, and the other players' after that.
    protected String[] getAllPlayerCards() {
        // TODO
        String[] result = {"BlackjackController.getAllPlayerCards is unimplemented."};
        return result;
    }
    
    // TODO: No assumptions can be made on the game state's format at this point.
    protected String[][] getGameState() {
        return gameState;
    }
    
    void setUserName(String name) {
        this.username = name;
    }
    
    void setUI(UserInterface ui) {
        this.UI = ui;
    }

    void setNetworkManager(NetworkManager netManager) {
        this.networkManager = netManager;
    }
    
}