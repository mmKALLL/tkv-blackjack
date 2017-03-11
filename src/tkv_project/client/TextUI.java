package tkv_project.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.lang.InterruptedException;

class TextUI {
    
    private static BlackjackController controller;
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    protected TextUI(BlackjackController control) {
        controller = control;
        System.out.println("Welcome to tkv-blackjack client, version " + controller.CLIENT_VERSION + "!");
    }
    
    // IP address/hostname and port, separated by a single comma.
    protected String askServerDetails() {
        
    }
    
    protected void startGame() {
        System.out.print("\nInitializing game... Please wait...");
        
        long startTime = System.currentTimeMillis();
        while (!controller.gameStateBuilt | (System.currentTimeMillis() - startTime) > controller.INIT_TIMEOUT) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                this.handleException(e);
            }
            System.out.print(".");
        }
        
        System.out.println();
        printGameScreen();
    }
    
    private void printGameScreen() {
        /* do a lot of stuff; get players' cards etc from controller, then display them with padding to create columns */
        
    }
    
    
    /* USER-FACING ERROR MESSAGE PRINTING */
    
    protected static void handleException(UnknownHostException e) {
        System.out.println("The host you specified could not be found. Please double-check the IP address or hostname.");
    }
    
    protected static void handleException(InterruptedException e) {
        if (controller.DEBUG) {
            System.out.println("Thread.sleep() was interrupted.");
        }
    }
    
    protected static void handleException(Exception e) {
        System.out.println("Unknown exception of type " + e.getClass().getSimpleName() + " encountered. Please report this to the developers, if possible.");
        System.out.println();
        if (controller.DEBUG) {
            e.printStackTrace();
        }
    }
    
    
}