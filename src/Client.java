/**
 * Client
 * @author Emilien Girault
 * @date 1/12/09
 */

import java.rmi.*;

public class Client {

    /**
     * Server RMI object
     * TODO: Will be private
     */
    public RMIInterface server;

    public Client(){
        try {
            server = (RMIInterface) Naming.lookup(RMIInterface.SERVER_NAME);
        } catch(Exception e) {
            System.err.println("The client cannot resolve the server. Did you launch it?");
            System.exit(1);
        }
    }


    /**
     * Test method for login()
     * TODO To be removed after deliverable 2
     * @param userName
     * @param password
     * @return
     */
    public int test_login(String userName, String password){
        int userID = RMIInterface.LOGIN_FAILED;
        try {
            System.out.println("\nTrying to login with username '"+userName+"' and password '"+password+"'...");
            if((userID = server.login(userName, password)) != RMIInterface.LOGIN_FAILED){
                System.out.println("Login successful, your user ID is " + userID);
            } else {
                System.out.println("Login failed!");
            }
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
        }
        return userID;
    }

    /**
     * Test method for register()
     * TODO To be removed after deliverable 2
     * @param loginID
     * @param password
     * @param fName
     * @param lName
     * @param email
     */
    public boolean test_register(String userName, String password, String fName, String lName, String email){
        boolean ok = false;
        try {
            System.out.println("\nRegistering "+userName+" with password "+password+"...");
            if(!(ok = server.register(userName, password, fName, lName, email, "", 0))){
                System.out.println("Impossible to register; the specified login is already taken. Try another one.");
            } else {
                System.out.println("Registration succesful!");
            }
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
        }
        return ok;
    }

    /**
     * Test method for deleteAccount()
     * @param userID
     * @param userName
     * @param password
     * @return
     */
    public boolean test_deleteAccount(int userID, String userName, String password){
        boolean ok = false;
        try {
            System.out.println("\nDeleting "+userName+"'s account with password '"+password+"' and user ID "+userID+"...");
            if(!(ok = server.deleteAccount(userID, userName, password))){
                System.out.println("Impossible to delete the account; check your username/password.");
            } else {
                System.out.println("The account have been deleted. Bye!");
            }
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
        }
        return ok;
    }

    /**
     * Test method for getInfos()
     * @param userID
     */
    public void test_getInfos(int userID){
        try {
            System.out.println("\nFetching informations for user ID "+userID+"...");
            AccountInformation ai = server.getInfos(userID);
            if(ai == null){
                System.out.println("Impossible to get the informations; please check the user ID");
            } else {
                System.out.println(ai);
            }
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
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

        client.test_login("john", "doe");

        client.test_register("john", "doe", "John", "Doe", "john.doe@gmail.com");

        int userID = client.test_login("john", "doe");


        client.test_getInfos(userID);

        client.test_deleteAccount(userID, "john", "wrong_pass");
        
        client.test_deleteAccount(userID, "john", "doe");


        
    }

}
