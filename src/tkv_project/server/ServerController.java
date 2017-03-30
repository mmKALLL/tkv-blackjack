package tkv_project.server;

class ServerController {

    private ServerConstants serverConstants;
    // TODO: multiple game support
    
    // First index is player order, with [0] being the dealer.
    // gameState[i][0] = playerID, gameState[i][1] = playerName, gameState[i][2] = current cards, gameState[i][3] = money
    // gameState[i][4] and [i][5] are reserved for future use regarding doubling and surrenders.
    // gameState[i][6] and [i][7] are reserved for future use regarding real-time actions made by players.
    private String[][] gameState;
    
    public ServerController(ServerConstants servConsts) {
        // TODO: initialize gameState and stuff
        gameState = new String[serverConstants.MAX_PLAYERS_PER_GAME][4];
    }
    
    protected handleHit() {
        // TODO: Handle a hit from some particular player, passing their ID as an argument; then update gameState
    }
    
    protected handleStand() {
        // TODO: Handle a hit from some particular player, passing their ID as an argument; then update gameState
    }
    
}