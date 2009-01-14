//BlackjackInterface
import java.rmi.*;
import java.util.*;

public interface RMIInterface extends Remote
{
	//method to deal cards
	public PlayerCards deal(String login, double bet) throws RemoteException;
	
	//method to get dealer's cards
	public DealerCards deal() throws RemoteException;
	
	//method to hit
	public Card hit(String login) throws RemoteException;
	
	//mtethod to stand
	public void stand(String login) throws RemoteException;
	
	//method to double
	public Card dble(String login, double bet) throws RemoteException;
	
	//method for high-stakes
	public void highStakes(String login) throws RemoteException;
	
	//method to register
	public boolean register(String fname, String lname, String password,
									String creditCard, String loginID, double startingCash) throws RemoteException;
	
	//method to log in
	public void login(String loginID, String password) throws RemoteException;
	
	//return bank money
	public double getMoney(String loginID) throws RemoteException;
	
	//delete this client's account
	public void delete(String loginID, String password) throws RemoteException;
	
	//request account information
	public AccountInformation query(String login) throws RemoteException;
}