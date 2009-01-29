package server;
/**
 * Server main class
 * @author Emilien Girault
 * @date 1/12/09
 */
import database.*;
import game.*;
import java.rmi.*;
import java.sql.*;

public class Server extends java.rmi.server.UnicastRemoteObject implements RMIInterface {


    /**
     * Database
     */
    public Data data; //TODO change into private
    public Deck deck; //TODO catch IndexOutOfBoundsExceptions - deck runs out of cards
    public PlayerCards playerHand;
    public DealerCards dealer;
    /**
     * Unique instance (Singleton Design Pattern)
     */
    private static Server instance = null;

    /**
     * Private constructor (use instance() instead)
     * @throws java.sql.SQLException
     */
    private Server() throws SQLException, RemoteException {
        data = Data.instance();
        deck = new Deck();
        playerHand = new PlayerCards(deck);
        dealer = new DealerCards(deck);
    }

    /**
     * Return the unique instance of the class (Singleton Design Pattern)
     * @return Server instance
     * @throws java.sql.SQLException
     */
    public static Server instance() throws Exception {
        if(instance == null){
          instance = new Server();
        }
        return instance;
    }

    /**
     * Bind to the RMI Registry
     */
    public void bind(){
        try {
            //System.out.println("name = " + server.data.getMaxUserID());
            //System.out.println("register = " + server.register("emilien", "mypass", "Emilien", "Girault", "bob@sponge.com", "", ""));
            Naming.rebind(SERVER_NAME, this);

        } catch(Exception e){
            System.out.println("Server error: Impossible to bind object to registry.");
            System.out.println("Did you launch 'rmiregistry' in the 'classes' folder of the project?");
            System.exit(1);
        }
    }
    //
    // RMI Methods
    //

    @Override
    public Card hit(int userID) throws RemoteException 
    {
    	/**
    	 * TODO use userID somewhere
    	 */
   		return deck.getNextCard();
    }
    
    @Override
    public DealerCards deal() throws RemoteException 
    {
    	dealer.nextHand();
    	return dealer;
    }
    
    public PlayerCards deal(int userID, double bet)throws RemoteException 
    {
    	/**
    	 * TODO subtract bet from database (FinancialData)
    	 * TODO use userID somewhere
    	 */
    	playerHand.nextHand();
    	return playerHand;
    }

    @Override
    public void stand(int userID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Card dble(int userID, double bet) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void highStakes(int userID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Register a user
     * @param loginID
     * @param password
     * @param fName
     * @param lName
     * @param email
     * @param creditCard
     * @param startingCash
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean register(String loginID, String password, String fName,
                            String lName, String email, String creditCard, double startingCash) throws RemoteException {
        boolean ok = false;
        try {
            ok = data.register(loginID, password, fName, lName, email, creditCard, startingCash);
        } catch(SQLException e) {
            System.err.println("Error in register(): " + e.getMessage());
        }
        return ok;
    }

    /**
     * Attempt to login
     * @param loginID
     * @param password
     * @return LOGIN_FAILED if failed, otherwise the userID
     * @throws java.rmi.RemoteException
     */
    @Override
    public int login(String userName, String password) throws RemoteException {
        int userID = LOGIN_FAILED;
        try {
            userID = data.login(userName, password);
        } catch(SQLException e) {
            System.err.println("Error in login(): " + e.getMessage());
        }
        return userID;
    }

    @Override
    public double getBank(int userID) throws RemoteException {
        double bank = 0;
        try {
            bank = data.getBank(userID);
        }
        catch(SQLException sqle){
            System.err.println("Error in retreiving bank: " + sqle.getMessage());
        }
        if(bank == -1){
            System.out.println("Error");
        }
        return bank;
    }

    /**
     * Delete a user account
     * @param userID
     * @param password
     * @throws java.rmi.RemoteException
     */
    @Override
	public boolean deleteAccount(int userID, String userName, String password) throws RemoteException {
        boolean ok = false;
        try {
            ok = data.deleteAccount(userID, userName, password);
        } catch(SQLException e){
            System.err.println("Error in deleteAccount(): " + e.getMessage());
        }
        return ok;
    }


    /**
     * Get the informations of a user
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
	@Override
    public AccountInformation getInfos(int userID) throws RemoteException {
        AccountInformation ai = null;
        try {
            ai = data.getInfos(userID);
        } catch(SQLException e){
            System.err.println("Error in getInfos(): " + e.getMessage());
        }
        return ai;
    }

    /**
     * Write a user's infos to the database
     * @param userID
     * @param ai user's infos
     * @throws java.rmi.RemoteException
     */
    @Override
    public void writeInfos(int userID, AccountInformation ai) throws RemoteException {
        try {
            data.writeInfos(userID, ai);
        } catch(SQLException e){
            System.err.println("Error in writeInfos(): " + e.getMessage());
        }
    }


    /**
     * The main program
     * /!\ Be sure to run rmiregistry in the class path before running this program
     * @param args arguments
     */
    public static void main(String args[]){
        Server server = null;
        try {
            server = instance();
        } catch(Exception e) {
            System.err.println("The server encountered an error while trying to connect to the database.");
            System.err.println("The error is: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Starting the server.");
        server.bind();
        System.out.println("Server started");
    }

}
