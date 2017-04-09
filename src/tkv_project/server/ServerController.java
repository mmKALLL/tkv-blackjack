package tkv_project.server;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

class ServerController {

    private ServerConstants serverConstants;
    private ConnectionManager connectionManager;
    // TODO: multiple game support
    
    // Game state. First index is player order, with [0] being the dealer.
    // gameState[i][0] = playerID, gameState[i][1] = playerName, gameState[i][2] = current cards, gameState[i][3] = money
    // gameState[i][4] and [i][5] are reserved for future use regarding doubling and surrenders.
    // gameState[i][6] and [i][7] are reserved for future use regarding real-time actions made by players.
    private String[][] gameState;
    private boolean gsUnderEdit = false;
    
    private int playerCount = 1; // handle removing players
    private List<String> deck;
    
    
    public ServerController(ServerConstants servConsts) {
        // TODO: initialize gameState and any necessary related stuff
        gameState = new String[serverConstants.MAX_PLAYERS_PER_GAME + 1][4];
    }

    protected void setConnectionManager(ConnectionManager manager) {
        this.connectionManager = manager;
    }
    
    protected void initialize() {
        
        System.out.println("Initializing ServerController...");
        
        deck = new ArrayList<String>();
        
        for (int i = 0; i < this.serverConstants.DEFAULT_DECKS_IN_GAME; i++) {
            for (char suit : this.serverConstants.cardSuits) {
                for (char value : this.serverConstants.cardValues) {
                    
                    deck.add(value + "" + suit);
                }
            }
        }
        
        System.out.println("\tDeck built.");
        
        // a full deck for when the cards run low and the deck needs to be shuffled
        List<String> fullDeck = deck;
        
        // Dealer's info
        gameState[0][0] = "0";
        gameState[0][1] = "Dealer";
        gameState[0][2] = "";
        gameState[0][3] = "0";
        
        playerCount = 1;
        
        
        System.out.println("\tInitialization success!");

    }
    
    // 0 on success; some basic checks to combat most race conditions
    // negative value signals that gsUnderEdit is left true due to an error state
    // positive value means to try again soon
    protected int addPlayer(long id, String name) {
        if (!gsUnderEdit) {
            gsUnderEdit = true;
            
            int oldPlayerCount = playerCount;
            if (gameState[playerCount] == null) {
                gameState[playerCount][0] = Long.toString(id);
                gameState[playerCount][1] = name;
                gameState[playerCount][2] = "";
                gameState[playerCount][3] = Integer.toString(serverConstants.DEFAULT_STARTING_CASH);
                
                playerCount++;
            } else {
                gsUnderEdit = true;
                System.out.println("gameState already had a player in slot " + playerCount + "; gs left in an corrupted state; returned -1");
                return -1;
            }
            
            if (gsUnderEdit == false || Long.parseLong(gameState[playerCount-1][0]) != id || playerCount != oldPlayerCount + 1) {
                gsUnderEdit = true;
                System.out.println("gs badly wrong, left in an corrupted state; returned -2");
                return -2;
            }
            
        } else {
            System.out.println("gs was under simultaneous use; returned -1");
            return 1;
        }
        
        System.out.println("player add successful; name: " + name + ", id: " + id);
        gsUnderEdit = false;
        return 0;
    }

    // Adds the chosen card to the player's cards and counts the total value to see if player has exceeded the limit.
    // If limit is exceeded, add a dot '.' to indicate that the player is done for the round.
    protected void handleHit(long id) {

        // A card is chosen at random from the remaining cards.
        Random rand = new Random();
        int randomCard = rand.nextInt(deck.size());
        String chosenCard = deck.get(randomCard);
        deck.remove(randomCard);

        for (String[] player : gameState) {
            if (player[0] == Long.toString(id)) {
                player[2] += chosenCard;

                int total = 0;

                for (int i = 0; i < player[2].length(); i += 2) {
                    char cardValue = player[2].charAt(i);
                    if (Character.isLetter(cardValue)) {
                        if (cardValue == 'A') {
                            // TODO: Handle counting when player gets an ace. Easiest would be to assign A to 1 or 11
                        } else {
                            total += 10;
                        }
                    } else {
                        total += Character.getNumericValue(cardValue);
                    }
                }

                if (total >= 21) {
                    player[2] += ".";
                }

                break;
            }
        }

    }
    
    // Adds a dot '.' to the end of the player's cards to indicate that he/she is done for the round.
    protected void handleStand(long id) {
        for (String[] player : gameState) {
            if (player[0] == Long.toString(id)) {
                player[2] += ".";
                break;
            }
        }
    }

    // Returns the gameState in a one-line-string in the format of:
    // firstPlayersValue1 + ! + firstPlayersValue2 + ! + firstPlayersValue3 + ? + secondPlayersValue1 + ! + secondPlayersValue2 ...
    protected String getSendableGameState() {
        if (serverConstants.VERBOSE_MESSAGE_DEBUG) {
            System.out.println("\tBuilding SendableGameState in ServerController...");
        }
        String gs = "!!!gsdata!!!";
        for (int j = 0; j < playerCount; j++) {
            for (int i = 0; i < 4; i++) {               // 4 since a player has currently 4 slots in use (ID, name, cards and money)
                gs += gameState[j][i];
                gs += "&";
            }
            gs += "#";
        }
        if (serverConstants.VERBOSE_MESSAGE_DEBUG) {
            System.out.println("\tSendableGameState built! Contents: " + gs);
        }
        return gs;
    }
    
}
