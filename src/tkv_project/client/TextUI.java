package tkv_project.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

class TextUI {
    
    private static Controller controller;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    // Constructor
    protected TextUI(Controller control) {
        controller = control;
        System.out.println("Welcome to tkv-blackjack client, version " + controller.VERSION + "!");
    }
    
    // IP address/hostname and port, separated by a single comma.
    protected static String askServerDetails() {
        
    }
    
    
    protected static void handleException(UnknownHostException e) {
        System.out.println("The host you specified could not be found. Please double-check the IP address or hostname.");
    }
    
    protected static void handleException(Exception e) {
        System.out.println("Unknown exception encountered.")
        System.out.println();
        if (controller.DEBUG) {
            e.printStackTrace();
        }
    }
    
    
}