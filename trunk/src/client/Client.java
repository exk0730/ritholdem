package client;
/**
 * Client
 * @author Emilien Girault
 * @date 1/12/09
 */

import java.rmi.Naming;
import java.rmi.RemoteException;
import game.*;

public class Client {

    /**
     * Server RMI object
     * TODO: Will be private
     */
    public RMIInterface server;

    /**
     * Constructor
     * TODO: Singleton Design Pattern?
     */
    public Client(){
        try {
            server = (RMIInterface) Naming.lookup(RMIInterface.SERVER_NAME);
        } catch(Exception e) {
            System.err.println("The client cannot resolve the server. Did you launch it?");
            System.exit(1);
        }
    }

    public int login(String userName, String pwd) {
        int userID = RMIInterface.LOGIN_FAILED;
        try {
            userID = server.login(userName, pwd);
        }
        catch(RemoteException re){
            System.err.println("There was an error logging in: " + re.getMessage());
            System.exit(1);
        }
        return userID;
    }

    public double getBank(int userID) {
        double bank = 0;
        try {
            bank = server.getBank(userID);
        }
        catch(RemoteException re){
            System.err.println("There was an error retreiving bank: " + re.getMessage());
            System.exit(1);
        }
        return bank;
    }

    public double updateBank(int userID, double money) {
        double bank = 0;
        try {
            bank = server.updateBank(userID, money);
        }
        catch(RemoteException re) {
            System.err.println("There was an error updating the bank: " + re.getMessage());
            System.exit(1);
        }
        return bank;
    }

    public boolean register(String loginID, String password, String fName,
                            String lName, String email, String creditCard, double startingCash){
        boolean ok = false;
        try {
            ok = server.register(loginID, password, fName, lName, email, creditCard, startingCash);
        }
        catch(RemoteException re){
            System.err.println("There was an error registering: " + re.getMessage());
            System.exit(1);
        }
        return ok;
    }

    public PlayerCards deal(int userID, double bet){
        PlayerCards hand = null;
        try {
            hand = server.deal(userID, bet);
        }
        catch(RemoteException re) {
            System.err.println("There was an error receiving player's hand: " + re.getMessage());
			re.printStackTrace();
            System.exit(1);
        }
        return hand;
    }

    public DealerCards deal() {
        DealerCards dealer = null;
        try {
            dealer = server.deal();
        }
        catch(RemoteException re) {
            System.err.println("There was an error receiving dealer's hand: " + re.getMessage());
            System.exit(1);
        }
        return dealer;
    }

    public Card hit(int userID){
        Card temp = null;
        try {
            temp = server.hit(userID);
        }
        catch(RemoteException re) {
            System.err.println("There was an error receiving a card: " + re.getMessage());
            System.exit(1);
        }
        return temp;
    }

    public Card hit(){
        Card temp = null;
        try {
            temp = server.hit();
        }
        catch(RemoteException re) {
            System.err.println("There was an error receiving a card: " + re.getMessage());
            System.exit(1);
        }
        return temp;
    }

    public boolean bust(int userID, boolean playerOrDealer) {
        boolean bool = false;
        try {
            bool = server.bust(userID, playerOrDealer);
        }
        catch(RemoteException re){
            System.err.println("There was an error checking bust: " + re.getMessage());
            System.exit(1);
        }
        return bool;
    }

    public boolean dealerStand() {
        boolean bool = false;
        try {
            bool = server.dealerStand();
        }
        catch(RemoteException re) {
            System.err.println("There was an error in dealer standing: " + re.getMessage());
            System.exit(1);
        }
        return bool;
    }

    public String checkWin(int userID, double bet) {
        String s = "";
        try {
            s = server.checkWin(userID, bet);
        }
        catch(RemoteException re){
            System.err.println("There was an error checking win: " + re.getMessage());
            System.exit(1);
        }
        return s;
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
    public AccountInformation test_getInfos(int userID){
        AccountInformation ai = null;
        try {
            System.out.println("\nFetching informations for user ID "+userID+"...");
            ai = server.getInfos(userID);
            if(ai == null){
                System.out.println("Impossible to get the informations; please check the user ID");
            } else {
                System.out.println(ai);
            }
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
        }
        return ai;
    }

    /**
     * Test method for getInfos()
     * @param userID
     */
    public void test_writeInfos(int userID, AccountInformation ai){
        try {
            System.out.println("\nWriting informations for user ID "+userID+"...");
            server.writeInfos(userID, ai);
            System.out.println("Writing successful!");
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

        //Create a client
        Client client = new Client();
        AccountInformation ai;
        int userID;

        //Login with an existing account
        userID = client.test_login("eric", "admin");
        client.test_deleteAccount(userID, "eric", "admin");

        //Get the informations of this account
        //ai = client.test_getInfos(userID);

        //Change the user's last name and email
//        ai.setLastName("Bob");
//        ai.setEmail("bob@sponge.com");
//        client.test_writeInfos(userID, ai);

        //Get the informations back
//        ai = client.test_getInfos(userID);

        

        //Try to login with a fake account, it should not work
//        client.test_login("john", "doe");
//
//        //Register a new account
//        client.test_register("john", "doe", "John", "Doe", "john.doe@gmail.com");
//
//        //Login with this new account, this should work
//        userID = client.test_login("john", "doe");

        //Get the informations of this account
//        client.test_getInfos(userID);
//
//        //Try to delete this account with fake credentials, this should not work
//        client.test_deleteAccount(userID, "john", "wrong_password");
//
//        //Really delete the account
//        client.test_deleteAccount(userID, "john", "doe");

    }

}
