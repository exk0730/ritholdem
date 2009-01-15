/**
 * RMI Interface for the server
 * @author Eric Kisner, Emilien Girault
 * @date 1/12/09
 */
import java.rmi.*;

public interface RMIInterface extends Remote
{
    /* //TO BE ADDED LATER
	//method to deal cards
	public PlayerCards deal(String login, double bet) throws RemoteException;
	
	//method to get dealer's cards
	public DealerCards deal() throws RemoteException;
    */
	
	//method to hit
	public Card hit(String login) throws RemoteException;
	
	//mtethod to stand
	public void stand(String login) throws RemoteException;
	
	//method to double
	public Card dble(String login, double bet) throws RemoteException;
	
	//method for high-stakes
	public void highStakes(String login) throws RemoteException;
	
	//method to register
	public boolean register(String loginID, String password, String fName, String lName, String email,
			/** TODO Handle these params !*/	String creditCard, double startingCash) throws RemoteException;
	
	//method to log in
	public void login(String loginID, String password) throws RemoteException;
	
	//return bank money
	public double getMoney(String loginID) throws RemoteException;
	
	//delete this client's account
	public void delete(String loginID, String password) throws RemoteException;
	
	//request account information
	public AccountInformation query(String login) throws RemoteException;
}