package tkv_project.server;

class ServerController {

    private ServerConstants serverConstants;
    private ConenctionManager connectionManager;
    // TODO: multiple game support
    
    // First index is player order, with [0] being the dealer.
    // gameState[i][0] = playerID, gameState[i][1] = playerName, gameState[i][2] = current cards, gameState[i][3] = money
    // gameState[i][4] and [i][5] are reserved for future use regarding doubling and surrenders.
    // gameState[i][6] and [i][7] are reserved for future use regarding real-time actions made by players.
    private String[][] gameState;
    
    public ServerController(ServerConstants servConsts) {
        // TODO: initialize gameState and any necessary related stuff
        gameState = new String[serverConstants.MAX_PLAYERS_PER_GAME][4];

        List<String> deck = new ArrayList<String>();
        
        for (int i = 0; i < servConsts.DEFAULT_DECKS_IN_GAME; i++) {
            for (char suit : sercvConsts.cardSuits) {
                for (char value : servConst.cardValues) {
                    deck.add(value + "" + suit);
                }
            }
        }

        // a full deck for when the cards run low and the deck needs to be shuffled 
        List<String> fullDeck = deck;

        // Dealer's info
        gameState[0][0] = "0";
        gameState[0][1] = "Dealer";
        
        int playerCount = 1;
        for (Connection player : connectionManager.getConnections()) {
            gameState[playerCount][0] = new Long(player.getID()).toString();;
            playerCount++;
        }

    }

    protected setConnectionManager(ConnectionManager manager) {
        this.connectionManager = manager;
    }
    
    protected void handleHit() {
        // TODO: Handle a hit from some particular player, passing their ID as an argument; then update gameState
    }
    
    protected void handleStand() {
        // TODO: Handle a stand from some particular player, passing their ID as an argument; then update gameState
    }
    
}