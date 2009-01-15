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
            System.err.println("The client cannot resolve the server. Did you launch it?");
            System.exit(1);
        }
    }


    /**
     * Login test method
     * TODO To be removed after deliverable 2
     * @param userName
     * @param password
     * @return
     */
    public int test_login(String userName, String password){
        int userID = RMIInterface.LOGIN_FAILED;
        try {
            System.out.println("Trying to login with "+userName+" : "+password+"...");
            if((userID = server.login(userName, password)) != RMIInterface.LOGIN_FAILED){
                System.out.println("Login successful, userID = " + userID);
            } else {
                System.out.println("Login failed!");
            }
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
        }
        return userID;
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
        int userID;
        userID = client.test_login("john", "doe");

        
    }

}
