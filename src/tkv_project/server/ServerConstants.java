package tkv_project.server;

class ServerConstants {
    
    public static final String SERVER_VERSION = "0.1";
    
    public static final int MAX_CONNECTIONS = 1000;
    public static final int MAX_SIMULTANEOUS_GAMES = 1 /*500*/;
    public static final int MAX_PLAYERS_PER_GAME = 255;
    public static final long PLAYER_CONNECTION_TIMEOUT_MILLIS = 20000;
    public static final int MAX_DECKS_IN_GAME = 60;
    
    public static final char[] cardValues = {'1', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'};
    public static final char[] cardSuits = {'h', 'd', 'c', 's'};

}