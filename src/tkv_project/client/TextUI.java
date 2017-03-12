package tkv_project.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.ConnectException;
import java.lang.InterruptedException;

class TextUI extends UserInterface {
    
    private static BlackjackController controller;
    private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    protected TextUI(BlackjackController control) {
        controller = control;
        System.out.println("Welcome to tkv-blackjack client, version " + controller.CLIENT_VERSION + "!");
    }
    
    // returns IP address/hostname and port, separated by a single comma.
    protected String askServerDetails() {
        String hostname = "";
        String port = "";
        
        boolean ok = false;
        while (!ok) {
            try {
                System.out.println("Input server hostname or IP address (default: " + controller.DEFAULT_HOSTNAME + ").");
                hostname = in.readLine();
                if (hostname.trim().length() == 0) {
                    hostname = controller.DEFAULT_HOSTNAME;
                }
                
                System.out.println("Input server port (default: " + controller.DEFAULT_PORT + ").");
                port = in.readLine();
                if (port.trim().length() == 0) {
                    port = controller.DEFAULT_PORT;
                }
                
                ok = true;
            } catch (IOException e) {
                System.out.println("That is not a valid value; please try again.");
            }
        }
        
        return hostname + "," + port;
    }
    
    protected void startGame() {
        System.out.print("\nInitializing game... Please wait...");
        
        long startTime = System.currentTimeMillis();
        while (!controller.gameStateBuilt && (System.currentTimeMillis() - startTime) < controller.GAMESTATE_INIT_TIMEOUT) {
            try {
                Thread.sleep(1000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
                this.handleException(e);
            }
            System.out.print(".");
        }
        
        if ((System.currentTimeMillis() - startTime) > controller.GAMESTATE_INIT_TIMEOUT) {
            System.out.println("\nThe game server did not respond. Please try again later, or join to another server. The program will now exit.");
        }
        
        System.out.println();
        printGameScreen();
    }
    
    private void printGameScreen() {
        // TODO: Do a lot of stuff; get players' cards etc from controller, then display them with padding to create columns.
        
    }
    
    
    /* USER-FACING ERROR MESSAGE PRINTING */
    
    protected void handleException(Exception ex) {
        try {
            throw ex;
        }
        
        catch (UnknownHostException e) {
            System.out.println("The host you specified could not be found. Please double-check the IP address or hostname.");
        }
        
        catch (InterruptedException e) {
            if (controller.DEBUG) {
                System.out.println("Thread.sleep() was interrupted.\n\n");
                e.printStackTrace();
            }
        }
        
        catch (ConnectException e) {
            System.out.println("It appears the server has refused the connection attempt. Please try again later or switch to another server. The program will now exit.");
            // TODO: Add functionality to join another game; essentially re-launch the program.
            // See the "Basically, you can't. At least not in a reliable way." answer at http://stackoverflow.com/questions/4159802/how-can-i-restart-a-java-application
            System.exit(0);
        }
        
        catch (IOException e) {
            System.out.println("It appears the server has disconnected.");
            // TODO: Add functionality to join another game; essentially re-launch the program.
            // See the "Basically, you can't. At least not in a reliable way." answer at http://stackoverflow.com/questions/4159802/how-can-i-restart-a-java-application
            System.exit(0);
        }
        
        catch (Exception e) {
            System.out.println("Unknown exception of type " + e.getClass().getSimpleName() + " encountered. Please report this to the developers, if possible.\n");
            if (controller.DEBUG) {
                e.printStackTrace();
            }
            System.out.print("\n\n");
        }
    }
    
}