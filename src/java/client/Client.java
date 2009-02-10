package client;
import java.rmi.Naming;
import java.rmi.RemoteException;
import game.*;
import jms.ClientTextListener;
import jms.JMSAsyncSubscriber;
import server.UnknownUserException;

/**
 * Client is the class that will call server methods
 * @author Eric Kisner / Emilien Girault
 */
public class Client {

    /**
     * Server RMI object
     * TODO: Will be private
     */
    public RMIInterface server;
    private static Client instance = null;
    private final String PRE_HOST = "//";
    private final String POST_HOST = ":1099/";
    private final int NOT_LOGGED_IN = -1;
    private int currentUserID;

	/**
	 * JMS subscriber to get messages
	 */
	private JMSAsyncSubscriber subscriber;

    /**
     * Singelton Design Pattern
     * @return the existing instance of Client or creats one
     */
    public static Client instance(String url){
        if(instance == null){
          instance = new Client(url);
        }
        return instance;
    }
    /**
     * Constructor
     */
    private Client(String host){
        try {
            server = (RMIInterface) Naming.lookup(PRE_HOST + host + POST_HOST + RMIInterface.SERVER_NAME);
        } catch(Exception e) {
            System.err.println("The client cannot resolve the server. Did you launch it?");
            System.err.println(e.getMessage() + "\n Exiting");
            System.exit(1);
        }
		subscriber = new JMSAsyncSubscriber(new ClientTextListener());
        currentUserID = NOT_LOGGED_IN;
    }

    /**
     * Logs a client in and returns there userID (-1 is failed login)
     * @param userName
     * @param pwd
     * @return int
     */
    public int login(String userName, String pwd) {
        int userID = RMIInterface.LOGIN_FAILED;
        try {
            userID = server.login(userName, pwd);
            currentUserID = userID;
        }
        catch(RemoteException re){
            System.err.println("There was an error logging in: " + re.getMessage());
            System.exit(1);
        }
        return userID;
    }

    /**
     * Returns this user's money
     * @param userID
     * @return
     */
    public double getBank(int userID) {
        double bank = 0;
        try {
            bank = server.getBank(userID);
        }
        catch(RemoteException re){
            System.err.println("There was an error retreiving bank: " + re.getMessage());
            System.exit(1);
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return bank;
    }

    /**
     * Updates this user's bank
     * @param userID
     * @param money
     * @return
     */
    public double updateBank(int userID, double money) {
        double bank = 0;
        try {
            bank = server.updateBank(userID, money);
        }
        catch(RemoteException re) {
            System.err.println("There was an error updating the bank: " + re.getMessage());
            System.exit(1);
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return bank;
    }

    /**
     * Adds emergency funds to this client
     * @param userID
     * @param money
     */
    public void addEmergencyFunds(int userID, double money) {
        try{
            server.addEmergencyFunds(userID, money);
        }
        catch(RemoteException re){
            System.err.println("There was an error registering ermergency funds: " + re.getMessage());
            System.exit(1);
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
    }

    /**
     * Retrieves emergency funds for this user
     * @param userID
     * @return
     */
    public double retrieveEmergencyFunds(int userID) {
        double temp = -1;
        try {
            temp = server.retrieveEmergencyFunds(userID);
        }
        catch(RemoteException re){
            System.err.println("There was an error retrieving ermergency funds: " + re.getMessage());
            System.exit(1);
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return temp;
    }

    /**
     * Registers a new user
     * @param loginID
     * @param password
     * @param fName
     * @param lName
     * @param email
     * @param creditCard
     * @param startingCash
     * @return
     */
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

    /**
     * Deals a hand for the player
     * @param userID
     * @param bet
     * @return
     */
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
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return hand;
    }

    /**
     * Deals a hand for the dealer (server)
     * @return
     */
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

    /**
     * User wants a new card
     * @param userID
     * @return
     */
    public Card hit(int userID){
        Card temp = null;
        try {
            temp = server.hit(userID);
        }
        catch(RemoteException re) {
            System.err.println("There was an error receiving a card: " + re.getMessage());
            System.exit(1);
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return temp;
    }

    /**
     * Server gets a new card
     * @return
     */
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

    /**
     * Checks if player or dealer busts
     * @param userID
     * @param playerOrDealer
     * @return
     */
    public boolean bust(int userID, boolean playerOrDealer) {
        boolean bool = false;
        try {
            bool = server.bust(userID, playerOrDealer);
        }
        catch(RemoteException re){
            System.err.println("There was an error checking bust: " + re.getMessage());
            System.exit(1);
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return bool;
    }

    /**
     * Checks if dealer needs to stand (at 16 or more)
     * @return
     */
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

    /**
     * Checks this user's win status
     * @param userID
     * @param bet
     * @return
     */
    public String checkWin(int userID, double bet) {
        String s = "";
        try {
            s = server.checkWin(userID, bet);
        }
        catch(RemoteException re){
            System.err.println("There was an error checking win: " + re.getMessage());
            System.exit(1);
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return s;
    }

    /**
     * Get this client's specific userID
     * @return
     */
    public int getCurrentUserID(){
        return currentUserID;
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
     * @param userName
     * @param password
     * @param fName
     * @param lName
     * @param email
     * @return
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
            if(!(ok = server.deleteAccount(userID))){
                System.out.println("Impossible to delete the account; check your username/password.");
            } else {
                System.out.println("The account have been deleted. Bye!");
            }
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return ok;
    }

    /**
     * Test method for getInfos()
     * @param userID
     * @return
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
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
        return ai;
    }

    /**
     * Test method for getInfos()
     * @param userID
     * @param ai
     */
    public void test_writeInfos(int userID, AccountInformation ai){
        try {
            System.out.println("\nWriting informations for user ID "+userID+"...");
            server.writeInfos(userID, ai);
            System.out.println("Writing successful!");
        } catch(RemoteException e) {
            System.err.println("Client error: " + e.getMessage());
        }
        catch(UnknownUserException uue){
            System.out.println(uue.getMessage());
        }
    }
}

    /**
     * Main program
     * @param args
    public static void main(String[] args){
        /**
         * This is a test program for deliverable 2
         * It only call some methods to get data from and to the database.
         

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

}*/
