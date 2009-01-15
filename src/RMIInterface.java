/**
 * RMI Interface for the server
 * @author Eric Kisner, Emilien Girault
 * @date 1/12/09
 */
import java.rmi.*;

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

    /*
     TODO TO BE ADDED LATER
	//method to deal cards
	public PlayerCards deal(String login, double bet) throws RemoteException;
	
	//method to get dealer's cards
	public DealerCards deal() throws RemoteException;
    */
	
	//method to hit
	public Card hit(int userID) throws RemoteException;
	
	//mtethod to stand
	public void stand(int userID) throws RemoteException;
	
	//method to double
	public Card dble(int userID, double bet) throws RemoteException;
	
	//method for high-stakes
	public void highStakes(int userID) throws RemoteException;
	
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
			/** TODO Handle these params !*/	String creditCard, double startingCash) throws RemoteException;
	
	/**
     * Attempt to login
     * @param loginID
     * @param password
     * @return true if OK, false if the userID/password don't match
     * @throws java.rmi.RemoteException
     */
	public int login(String userName, String password) throws RemoteException;
	
	//return bank money
	public double getMoney(int userID) throws RemoteException;
	
	//delete this client's account
	public void delete(String userID, String password) throws RemoteException;
	
	//request account information
	public AccountInformation query(int userID) throws RemoteException;
}
