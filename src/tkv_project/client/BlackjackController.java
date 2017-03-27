package tkv_project.client;

class BlackjackController {
    
    public static final boolean DEBUG = true;
    
    public static final String CLIENT_VERSION = "0.6.0";
    public static final int GAMESTATE_INIT_TIMEOUT = 10000; // milliseconds
    public static final String DEFAULT_HOSTNAME = "localhost";
    public static final String DEFAULT_PORT = "47855";
    
    private UserInterface UI;
    private NetworkManager networkManager;

    // gameState[i][0] = playerID, gameState[i][1] = playerName, gameState[i][2] = cards, gameState[i][3] = money
    private String[][] gameState;
    protected boolean gameStateBuilt = false;
    private int playerID;
    private String playerName = "";
    
    protected BlackjackController() {
        
    }
    
    protected BlackjackController(UserInterface ui, NetworkManager netManager) {
        this.UI = ui;
        this.networkManager = netManager;
    }
    
    // Called whenerver server has sent an update that was read by NetworkManager.
    void updateGameState(String[][] newGameState) {
        gameState = newGameState;
        gameStateBuilt = true;
        UI.update();
    }
    
    protected void handleServerConnectionFailure(Exception e) {
        if (this.DEBUG) {
            e.printStackTrace();
        }
        UI.handleException(e);
    }
    
    protected void playHit() {
        networkManager.sendMessage(playerID + " hit");
    }
    
    protected void playStand() {
        networkManager.sendMessage(playerID + " stand");   
    }
    
    // A non-separated string where, for each card, there is a card number/symbol followed by suit symbol.
    // e.g. "", "6s", "Jh", "As", "5s5c", "9cQhAs5d", "9c9c9c9c9c9c9c".
    protected String getThisPlayerCards() {
        for(String[] player : gameState) {
            if(player[0] == playerID) {
                return player[2];
            }
        }
        return "Something went wrong in getThisPlayerCards.";
    }
    
    protected String[][] getGameState() {
        return gameState;
    }

    protected String getThisPlayerName() {
        return playerName;
    }
    
    protected int getThisPlayerMoney() {
        return money;
    }

    protected int getNumOfPlayers() {
        if (this.gameStateBuilt) {
            if (this.gameState.length >= 2) {
                int i = 1;
                while(gameState[i][0] != null && gameState[i][0] != 0 && i < gameState.length) {
                    i++;
                }
                return i - 1;
            }
        }

        return 0;
    }

    void setUI(UserInterface ui) {
        this.UI = ui;
    }

    void setNetworkManager(NetworkManager netManager) {
        this.networkManager = netManager;
    }
    
}