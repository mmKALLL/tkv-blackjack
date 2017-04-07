package tkv_project.server;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

class ServerController {

    private ServerConstants serverConstants;
    private ConnectionManager connectionManager;
    // TODO: multiple game support
    
    // First index is player order, with [0] being the dealer.
    // gameState[i][0] = playerID, gameState[i][1] = playerName, gameState[i][2] = current cards, gameState[i][3] = money
    // gameState[i][4] and [i][5] are reserved for future use regarding doubling and surrenders.
    // gameState[i][6] and [i][7] are reserved for future use regarding real-time actions made by players.
    private String[][] gameState;
    private List<String> deck;
    
    public ServerController(ServerConstants servConsts) {
        // TODO: initialize gameState and any necessary related stuff
        gameState = new String[serverConstants.MAX_PLAYERS_PER_GAME][4];

        deck = new ArrayList<String>();
        
        for (int i = 0; i < servConsts.DEFAULT_DECKS_IN_GAME; i++) {
            for (char suit : servConsts.cardSuits) {
                for (char value : servConsts.cardValues) {
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

        // TODO: A lot of things
    }

    protected void setConnectionManager(ConnectionManager manager) {
        this.connectionManager = manager;
    }
    
    protected void setName(long id, String name) {
        for (String[] player : gameState) {
            if (player[0] == new Long(id).toString()) {
                player[1] = name;
                break;
            }
        }
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
            if (player[0] == new Long(id).toString()) {
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
            if (player[0] == new Long(id).toString()) {
                player[2] += ".";
                break;
            }
        }
    }
    
}
