package game;
import java.rmi.Remote;
import java.rmi.RemoteException;
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
     * Register a new user
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
	public boolean register(String userName, String password, String fName, String lName, String email,
                        	String creditCard, double startingCash) throws RemoteException;


	/**
     * Attempt to login
     * @param userName
     * @param password
     * @return true if OK, false if the userID/password don't match
     * @throws java.rmi.RemoteException
     */
	public int login(String userName, String password) throws RemoteException;
    
    /**
     * Delete a user account
     * @param userID
     * @param userName
     * @param password
     * @return
     * @throws java.rmi.RemoteException
     */
	public boolean deleteAccount(int userID, String userName, String password) throws RemoteException;


    /**
     * Get the informations of a user
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
	public AccountInformation getInfos(int userID) throws RemoteException;

    /**
     * Get the bank of a user
     * @param userID
     * @return monetary amount
     * @throws java.rmi.RemoteException
     */
    public double getBank(int userID) throws RemoteException;

    /**
     * Updates the money in a user's bank
     * @param userID
     * @param money
     * @return the user's updated bank
     * @throws java.rmi.RemoteException
     */
    public double updateBank(int userID, double money) throws RemoteException;

    /**
     * Adds emergency funding for this user
     * @param userID
     * @param money
     * @throws java.rmi.RemoteException
     */
    public void addEmergencyFunds(int userID, double money) throws RemoteException;

    /**
     * Retrieves emergency funding for this user
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    public double retrieveEmergencyFunds(int userID) throws RemoteException;
    /**
     * Write a user's infos to the database
     * @param userID
     * @param ai user's infos
     * @throws java.rmi.RemoteException
     */
    public void writeInfos(int userID, AccountInformation ai) throws RemoteException;
    

    /**
     * method to deal cards
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     */
    public PlayerCards deal(int userID, double bet) throws RemoteException;

    /**
     * method to get dealer's cards
     * @return
     * @throws java.rmi.RemoteException
     */
    public DealerCards deal() throws RemoteException;

    /**
     * method to check if player or dealer busts
     * @param userID
     * @param playerOrDealer
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean bust(int userID, boolean playerOrDealer) throws RemoteException;

    /**
     * method for dealer to stand at 16 and above
     * @return
     * @throws java.rmi.RemoteException
     */
    public boolean dealerStand() throws RemoteException;

    /**
     * method to check win type
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     */
    public String checkWin(int userID, double bet) throws RemoteException;

    /**
     * method to hit
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    public Card hit(int userID) throws RemoteException;

    /**
     * method for dealer hit
     * @return
     * @throws java.rmi.RemoteException
     */
    public Card hit() throws RemoteException;

    /**
     * mtethod to stand
     * @param userID
     * @throws java.rmi.RemoteException
     */
    public void stand(int userID) throws RemoteException;

    /**
     * method to double
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     */
    public Card dble(int userID, double bet) throws RemoteException;
}