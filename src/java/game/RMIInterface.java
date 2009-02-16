package game;
import server.UnknownUserException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
/**
 * RMI interface for the server
 * @author Eric Kisner, Emilien Girault
 *
 */

public interface RMIInterface extends Remote
{

    /**
     * The server RMI name
     */
    public static String SERVER_NAME = "BlackJackServer";

    /**
     * login arror code
     */
    public static int LOGIN_FAILED = -1;

	
	/**
     * Register a new user and return their userID
     * @param userName
     * @param password
     * @param fName
     * @param lName
     * @param email
     * @param creditCard
     * @param startingCash
     * @return
     * @throws java.rmi.RemoteException
     */
	public int register(String userName, String password, String fName, String lName, String email,
                        	String creditCard, double startingCash) throws RemoteException;

    /**
     * Gets the users connected to the server
     * @return
     * @throws java.rmi.RemoteException
     */
    public ArrayList<Integer> getUsers() throws RemoteException;

	/**
     * Attempt to login
     * @param userName
     * @param password
     * @return true if OK, false if the userID/password don't match
     * @throws java.rmi.RemoteException
     */
	public int login(String userName, String password) throws RemoteException;


	/**
     * Logout
     * @param userID
     * @return true if the user got logged out
     * @throws java.rmi.RemoteException
     */
	public boolean logout(int userID) throws RemoteException;


    /**
     * Delete a user account
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
	public boolean deleteAccount(int userID) throws RemoteException, UnknownUserException;


	/**
	 * Kick a user
	 * @param userID
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
	 */
	public void kickUser(int userID) throws RemoteException, UnknownUserException;


    /**
     * Get the informations of a user
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
	public AccountInformation getInfos(int userID) throws RemoteException, UnknownUserException;

    /**
     * Get the bank of a user
     * @param userID
     * @return monetary amount
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
    public double getBank(int userID) throws RemoteException, UnknownUserException;

    /**
     * Updates the money in a user's bank
     * @param userID
     * @param money
     * @return the user's updated bank
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
    public double updateBank(int userID, double money) throws RemoteException, UnknownUserException;

    /**
     * Updates the database for user based on their action
     * @param userID
     * @param character
     * @throws java.rmi.RemoteException
     */
    public void updateUserCardStats(int userID, char character) throws RemoteException;

    /**
     * Retrieves this user's card stats
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    public AccountCardStats getCardStats(int userID) throws RemoteException;

	/**
     * Retrieves the server stats
     * @return server stats
     * @throws java.rmi.RemoteException
     */
    public ServerStatistics getCurrentServerStats() throws RemoteException;

	/**
     * Retrieves the server stats corresponding to a given entry
     * @param serverID
     * @return server stats corresponding to a given entry
     * @throws java.rmi.RemoteException
     */
    public ServerStatistics getServerStats(int serverID) throws RemoteException;

	/**
     * Get all the server stats entries
     * @return server stats entries
     * @throws RemoteException
     */
    public ArrayList<Integer> getServerStatsEntries() throws  RemoteException;


    /**
     * Write a user's infos to the database
     * @param userID
     * @param ai user's infos
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
    public void writeInfos(int userID, AccountInformation ai) throws RemoteException, UnknownUserException;
    

    /**
     * method to deal cards
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
    public Hand dealPlayer(int userID) throws RemoteException, UnknownUserException;

    /**
     * method to get dealer's cards
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Hand dealDealer(int userID) throws RemoteException;

    /**
     * method to check if player or dealer busts
     * @param userID
     * @param playerOrDealer
     * @return
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
    public boolean bust(int userID, boolean playerOrDealer) throws RemoteException, UnknownUserException;

    /**
     * method for dealer to stand at 16 and above
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean dealerStand(int userID) throws RemoteException;

    /**
     * method to check win type
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
    public String checkWin(int userID, double bet) throws RemoteException, UnknownUserException;

    /**
     * method to hit
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     * @throws UnknownUserException
     */
    public Card hit(int userID) throws RemoteException, UnknownUserException;

    /**
     * method for dealer hit
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Card dealerHit(int userID) throws RemoteException;


    /**
     * method to get servers' start time
     * @return start time of the server in millis
     * @throws java.rmi.RemoteException
     */
    public long getStartTime() throws RemoteException;

     /**
     * method to get servers' current time
     * @return current time of the server in millis
     * @throws java.rmi.RemoteException
     */
    public long getCurrentTime() throws RemoteException;
}