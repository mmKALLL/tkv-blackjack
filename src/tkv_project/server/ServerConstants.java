package tkv_project.server;

final class ServerConstants {
    
    public static final boolean DEBUG = true;
    public static final boolean VERBOSE_MESSAGE_DEBUG = true;
    
    public static final String SERVER_VERSION = "0.4.18";
    public static final int DEFAULT_PORT = 47855;
    
    public static final int MAX_CONNECTIONS = 15; /*1000*/
    public static final int MAX_SIMULTANEOUS_GAMES = 1 /*500*/;
    public static final int MAX_PLAYERS_PER_GAME = 8; /*255 after serverController.getSendableGameState() works fast*/
    public static final long PLAYER_CONNECTION_TIMEOUT_MILLIS = 20000; // TODO: unused
    public static final int MAX_UPDATES_PER_SECOND = 10; // How fast a player can request gamestates; no caching so this is low
    public static final int MAX_DECKS_IN_GAME = 60;
    
    public static final int DEFAULT_DECKS_IN_GAME = 4;
    public static final int DEFAULT_STARTING_CASH = 1000;
    
    public static final char[] cardValues = {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    public static final char[] cardSuits = {'h', 'd', 'c', 's'};

}