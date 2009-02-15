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
    public static Client instance(String url) throws Exception {
        if(instance == null){
          instance = new Client(url);
        }
        return instance;
    }
    /**
     * Constructor
     */
    private Client(String host) throws Exception {
		server = (RMIInterface) Naming.lookup(PRE_HOST + host + POST_HOST + RMIInterface.SERVER_NAME);
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
    public int login(String userName, String pwd) throws RemoteException {
        int userID = RMIInterface.LOGIN_FAILED;

            userID = server.login(userName, pwd);
            currentUserID = userID;


        return userID;
    }


	/**
	 * Log out the current user
	 * @return
	 */
	public boolean logout() throws RemoteException{
		boolean res = false;
		res = server.logout(currentUserID);

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
                            String lName, String email, String creditCard, double startingCash) throws RemoteException{
        int id = RMIInterface.LOGIN_FAILED;

        id = server.register(loginID, password, fName, lName, email, creditCard, startingCash);
        currentUserID = id;

        return id;
    }

    /**
     * Returns this user's money
     * @return the user's money
     */
    public double getBank() throws UnknownUserException, RemoteException {
        double bank = 0;
        bank = server.getBank(currentUserID);
        return bank;
    }

    /**
     * Updates this user's bank
     * @param userID
     * @param money
     * @return
     */
    public double updateBank(int userID, double money) throws RemoteException, UnknownUserException {
        double bank = 0;
        bank = server.updateBank(userID, money);
        return bank;
    }

    /**
     * Adds money to this user's account - used from login panel
     * @param money
     * @return
     */
   public double addMoney(double money) throws RemoteException, UnknownUserException {
        double bank = 0;
        bank = server.updateBank(currentUserID, money);
        return bank;
    }

    /**
     * Update card stats based on a character
     * @param userID
     * @param character
     */
    public void updateUserCardStats(int userID, char character) throws RemoteException {
        server.updateUserCardStats(userID, character);

    }


    /**
     * Deals a hand for the player
     * @param userID
     * @param bet
     * @return
     */
    public Hand deal(int userID, double bet) throws UnknownUserException, RemoteException{
        Hand hand = null;
        hand = server.deal(userID, bet);
        return hand;
    }

    /**
     * Deals a hand for the dealer (server)
     * @return
     */
    public Hand deal() throws RemoteException {
        Hand dealer = null;
        dealer = server.deal();
        return dealer;
    }

    /**
     * User wants a new card
     * @param userID
     * @return
     */
    public Card hit(int userID) throws UnknownUserException, RemoteException {
        Card temp = null;
        temp = server.hit(userID);
        return temp;
    }

    /**
     * Server gets a new card
     * @return
     */
    public Card hit() throws RemoteException{
        Card temp = null;
        temp = server.hit();
        return temp;
    }

    /**
     * Checks if player or dealer busts
     * @param userID
     * @param playerOrDealer
     * @return
     */
    public boolean bust(int userID, boolean playerOrDealer) throws UnknownUserException, RemoteException {
        boolean bool = false;
        bool = server.bust(userID, playerOrDealer);
        return bool;
    }

    /**
     * Checks if dealer needs to stand (at 16 or more)
     * @return
     */
    public boolean dealerStand() throws RemoteException {
        boolean bool = false;
        bool = server.dealerStand();
        return bool;
    }

    /**
     * Checks this user's win status
     * @param userID
     * @param bet
     * @return
     */
    public String checkWin(int userID, double bet) throws UnknownUserException, RemoteException {
        String s = "";
        s = server.checkWin(userID, bet);
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
