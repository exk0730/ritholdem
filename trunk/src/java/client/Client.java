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

	private Table table = null;

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
		subscriber = new JMSAsyncSubscriber(new ClientTextListener(this));
        currentUserID = NOT_LOGGED_IN;
    }

	/**
	 * Set the table
	 * @param t
	 */
	public void setTable(Table t){
		table = t;
	}

	/**
	 * Get the table
	 * @return
	 */
	public Table getTable(){
		return table;
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
	 * Log out the current user
	 * @return
	 */
	public boolean logout(){
		boolean res = false;
		try{
			res = server.logout(currentUserID);
		} catch(RemoteException re){
			System.err.println("There was an error logging out: " + re.getMessage());
            System.exit(1);
		}
		return res;
	}


    /**
     * Registers a new user and return their userID
     * @param loginID
     * @param password
     * @param fName
     * @param lName
     * @param email
     * @param creditCard
     * @param startingCash
     * @return
     */
    public int register(String loginID, String password, String fName,
                            String lName, String email, String creditCard, double startingCash){
        int id = RMIInterface.LOGIN_FAILED;
        try {
            id = server.register(loginID, password, fName, lName, email, creditCard, startingCash);
            currentUserID = id;
        }
        catch(RemoteException re){
            System.err.println("There was an error registering: " + re.getMessage());
            System.exit(1);
        }
        return id;
    }

    /**
     * Returns this user's money
     * @return the user's money
     */
    public double getBank() throws UnknownUserException {
        double bank = 0;
        try {
            bank = server.getBank(currentUserID);
        }
        catch(RemoteException re){
            System.err.println("There was an error retreiving bank: " + re.getMessage());
            System.exit(1);
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
     * Adds money to this user's account - used from login panel
     * @param money
     * @return
     */
   public double addMoney(double money) {
        double bank = 0;
        try {
            bank = server.updateBank(currentUserID, money);
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
     * Update card stats based on a character
     * @param userID
     * @param character
     */
    public void updateUserCardStats(int userID, char character) {
        try{
            server.updateUserCardStats(userID, character);
        }
        catch(RemoteException re) {
            System.err.println("There was an error updating userCardStats: " + re.getMessage());
            System.exit(1);
        }
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
        return temp;
    }

    /**
     * Deals a hand for the player
     * @param userID
     * @param bet
     * @return
     */
    public PlayerCards deal(int userID, double bet) throws UnknownUserException{
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
    public Card hit(int userID) throws UnknownUserException {
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
    public boolean bust(int userID, boolean playerOrDealer) throws UnknownUserException {
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
    public String checkWin(int userID, double bet) throws UnknownUserException {
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
     * Get this client's specific userID
     * @return
     */
    public int getCurrentUserID(){
        return currentUserID;
    }
}
