package game;
import java.rmi.Remote;
import java.rmi.RemoteException;
import game.Card;
import game.AccountInformation;

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
     * @param loginID
     * @param password
     * @return true if OK, false if the userID/password don't match
     * @throws java.rmi.RemoteException
     */
	public int login(String userName, String password) throws RemoteException;
    
    /**
     * Delete a user account
     * @param userID
     * @param password
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
     * Write a user's infos to the database
     * @param userID
     * @param ai user's infos
     * @throws java.rmi.RemoteException
     */
    public void writeInfos(int userID, AccountInformation ai) throws RemoteException;
    


	//method to deal cards
	public PlayerCards deal(int userID, double bet) throws RemoteException;

	//method to get dealer's cards
	public DealerCards deal() throws RemoteException;

	//method to hit
	public Card hit(int userID) throws RemoteException;

	//mtethod to stand
	public void stand(int userID) throws RemoteException;

	//method to double
	public Card dble(int userID, double bet) throws RemoteException;

	//method for high-stakes
	public void highStakes(int userID) throws RemoteException;
}