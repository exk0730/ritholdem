/**
 * Client
 * @author Emilien Girault
 * @date 1/12/09
 */

import java.rmi.*;

public class Client {
    
    /**
     * The server RMI name
     */
    public static String SERVER_NAME = "BlackJackServer";

    /**
     * Server RMI object
     * TODO: Will be private
     */
    public RMIInterface server;

    public Client(){
        try {
            server = (RMIInterface) Naming.lookup(SERVER_NAME);
        } catch(Exception e) {
            System.out.println("The client cannot resolve the server. Did you launch it?");
            System.exit(1);
        }
    }

    /**
     * Main program
     * @param args
     */
    public static void main(String[] args){

        /**
         * This is a test program for deliverable 2
         * It only call some methods to get data from and to the database.
         */
        Client client = new Client();
        

        try {
            System.out.println("register = " + client.server.register("emilien", "p4ss", "Emilien", "Girault", "bla@bla.com", "1456", 8));
            System.out.println("lll");

        } catch(Exception e){
            System.out.println("The client encountered an error. The message is: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
