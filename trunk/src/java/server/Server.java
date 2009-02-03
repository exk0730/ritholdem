package server;
/**
 * Server main class
 * @author Emilien Girault / Eric Kisner
 * @date 1/12/09
 */
import database.*;
import game.*;
import java.rmi.*;
import java.sql.*;
import java.util.*;


public class Server extends java.rmi.server.UnicastRemoteObject implements RMIInterface {


    /**
     * Database
     */
    public Data data; //TODO change into private
    public Deck deck; //TODO catch IndexOutOfBoundsExceptions - deck runs out of cards
    private PlayerCards playerHand;
    private DealerCards dealer;
    private CheckLogic checkLogic;
    private ArrayList<Integer> users = new ArrayList<Integer>();
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
    }

    /**
     * Return the unique instance of the class (Singleton Design Pattern)
     * @return Server instance
     * @throws Exception
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

    private void initGame(){
        deck = new Deck();
        playerHand = new PlayerCards(deck);
        dealer = new DealerCards(deck);
        checkLogic = new CheckLogic(playerHand, dealer);
    }

    /**
     * Client hit
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public Card hit(int userID) throws RemoteException 
    {
    	/**
    	 * TODO use userID somewhere
    	 */
        Card temp = deck.getNextCard();
        checkLogic.updatePlayer(temp);
   		return temp;
    }

    /**
     * Hit method for new dealer card
     * @return
     * @throws java.rmi.RemoteException
     */
    public Card hit() throws RemoteException {
        Card temp = deck.getNextCard();
        checkLogic.updateDealer(temp);
        return temp;
    }
    
    /**
     * Return dealer's hand
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public DealerCards deal() throws RemoteException 
    {
    	dealer.nextHand();
        checkLogic = new CheckLogic(playerHand, dealer);
    	return dealer;
    }

    /**
     * Return player's hand
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public PlayerCards deal(int userID, double bet)throws RemoteException 
    {
    	/**
    	 * TODO use userID somewhere
    	 */
    	playerHand.nextHand();
    	return playerHand;
    }

    /**
     * Checks if the player or dealer busts
     * TODO: use userID
     * @param userID
     * @param playerOrDealer
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean bust(int userID, boolean playerOrDealer) throws RemoteException {
        if(playerOrDealer){
            return (checkLogic.checkBust(true));
        }
        else{
            return (checkLogic.checkBust(false));
        }
    }

    /**
     * Dealer must stand at 16 and above
     * @return true if dealer has a higher hand than 15
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean dealerStand() throws RemoteException {
        if(checkLogic.getCombinedDealerHand() >= 16) {
            return true;
        }
        else return false;
    }

    /**
     * Checks win type and concatenates to a string
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public String checkWin(int userID, double bet) throws RemoteException {
        String s = "";
        switch(checkLogic.returnTypeOfWin())
        {
            case -1:
                bet *= -1;
                s = "You lose.";
                break;
            case 1:
                s = "You win!";
                break;
            case 2:
                bet = bet * 1.5;
                s = "Winner, winner, chicken dinner!";
                break;
            default:
                bet = 0;
                s = "Push.";
                break;
        }
        s = s + "_" + bet;
        return s;
    }

    /**
     *
     * @param userID
     * @throws java.rmi.RemoteException
     */
    @Override
    public void stand(int userID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     *
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public Card dble(int userID, double bet) throws RemoteException {
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
     * @param userName
     * @param password
     * @return LOGIN_FAILED if failed, otherwise the userID
     * @throws java.rmi.RemoteException
     */
    @Override
    public int login(String userName, String password) throws RemoteException {
        int userID = LOGIN_FAILED;
        try {
            userID = data.login(userName, password);
            initGame();
            users.add(userID);
        } catch(SQLException e) {
            System.err.println("Error in login(): " + e.getMessage());
        }
        return userID;
    }

    @Override
    /**
     * returns the user's money
     * @param int userID
     * @return double
     */
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
     * updates a user's bank
     * @param userID
     * @param money
     * @return bank money
     * @throws java.rmi.RemoteException
     */
    public double updateBank(int userID, double money) throws RemoteException {
        double temp = -1;
        try {
            temp = data.updateBank(userID, money);
        }
        catch(SQLException sqle){
            System.err.println("Error in updating bank: " + sqle.getMessage());
        }
        return temp;
    }

    /**
     * Delete a user account
     * @param userID
     * @param userName
     * @param password
     * @return
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
     * Check if a user exists
     * @param userID
     * @return
     */
    private boolean userExists(int userID){
        boolean ok = false;
        for(int i = 0; i < users.size(); i++){
            if(userID == users.get(i)){
                ok = true;
            }
        }
        return ok;
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
