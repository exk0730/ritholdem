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
import java.util.Date;


public class Server extends java.rmi.server.UnicastRemoteObject implements RMIInterface {


    /**
     * Database
     */
    public Data data; //TODO change into private
    public Deck deck; //TODO catch IndexOutOfBoundsExceptions - deck runs out of cards
    private Hand playerHand, dealerHand;
    private CheckLogic checkLogic;
    private ArrayList<Integer> users = new ArrayList<Integer>();
    private static Date startTime;
    private Date currentTime;
    /**
     * Unique instance (Singleton Design Pattern)
     */
    private static Server instance = null;
    /**
     * Server's ID
     */
     private final int SERVER_ID = 1;
	/**
	 * Stats reporter
	 */
	private StatsReport statsreport = null;

    /**
     * Private constructor (use instance() instead)
     * @throws java.sql.SQLException
     */
    private Server() throws SQLException, RemoteException {
        data = Data.instance();
		statsreport = StatsReport.instance();
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
			//Start the stats reporter
			statsreport.start();
			
            //Bind the server to RMI
			Naming.rebind(SERVER_NAME, this);

        } catch(Exception e){
            System.out.println("Server error: Impossible to bind object to registry.");
            System.out.println("Did you launch 'rmiregistry' in the 'classes' folder of the project?");
            System.exit(1);
        }
    }

    private void initGame(boolean newHand){
        try{
            deck = Deck.instance();
        }
        catch(Exception e) { System.err.println("Error in retrieving isntance of deck"); }
        if(newHand){
            playerHand = new Hand();
            dealerHand = new Hand();
            checkLogic = new CheckLogic(playerHand, dealerHand);
        }
    }

    /**
     * Client hit
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized Card hit(int userID) throws RemoteException, UnknownUserException
    {
        Card temp = null;
        if(userExists(userID)){
            try{
                temp = deck.getNextCard();
            }
            catch(IndexOutOfBoundsException ioobe){
                initGame(false);
                temp = deck.getNextCard();
            }
            checkLogic.updatePlayer(temp);
        }
        else{
            throw new UnknownUserException();
        }
   		return temp;
    }

    /**
     * Hit method for new dealer card
     * @return
     * @throws java.rmi.RemoteException
     */
    public synchronized Card hit() throws RemoteException {
        Card temp = null;
    	/**
    	 * TODO use userID somewhere
    	 */
        try{
            temp = deck.getNextCard();
        }
        catch(IndexOutOfBoundsException ioobe){
            initGame(false);
            temp = deck.getNextCard();
        }
        checkLogic.updateDealer(temp);
        return temp;
    }
    
    /**
     * Return dealer's hand
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized Hand deal() throws RemoteException
    {
        try{
            dealerHand.nextHand();
        }
        catch(IndexOutOfBoundsException ioobe){
            initGame(true);
            dealerHand = new Hand();
            dealerHand.nextHand();
        }
        checkLogic = new CheckLogic(playerHand, dealerHand);
    	return dealerHand;
    }

    /**
     * Return player's hand
     * @param userID
     * @param bet
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized Hand deal(int userID, double bet)throws RemoteException, UnknownUserException
    {
        if(userExists(userID)){
            try{
                playerHand.nextHand();
            }
            catch(IndexOutOfBoundsException ioobe){
                initGame(true);
                playerHand = new Hand();
                playerHand.nextHand();
            }
        }
        else{
            throw new UnknownUserException();
        }
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
    public synchronized boolean bust(int userID, boolean playerOrDealer) throws RemoteException, UnknownUserException
    {
        boolean bust = false;
        if(userExists(userID)){
            if(playerOrDealer){
                bust = checkLogic.checkBust(true);
            }
            else{
                bust = checkLogic.checkBust(false);
            }
        }
        else{
            throw new UnknownUserException();
        }
        return bust;
    }

    /**
     * Dealer must stand at 16 and above
     * @return true if dealer has a higher hand than 15
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized boolean dealerStand() throws RemoteException {
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
    public synchronized String checkWin(int userID, double bet) throws RemoteException, UnknownUserException {
        String s = "";
        if(userExists(userID)){
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
            System.out.println("Just checked win - String s= " + s + " checklogic is as follows: " +
                    checkLogic.toString());
        }
        else{
            throw new UnknownUserException();
        }
        return s;
    }


    /**
     * Remove a user from the server
     * @param userID The userID to remove
     */
    protected synchronized void removeUser(int userID) throws RemoteException {
        for(int i = 0; i < users.size(); i++){
            if((int) users.get(i) == userID){
                users.remove(i);
            }
        }
    }

    /**
     * Get the collection of users connected to the server
     * @return ArrayList the collection of users
     */
    public synchronized ArrayList<Integer> getUsers() throws RemoteException {
        return users;
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
    public synchronized int register(String loginID, String password, String fName,
                            String lName, String email, String creditCard, double startingCash) throws RemoteException {
        int userID = RMIInterface.LOGIN_FAILED;
        try {
            userID = data.register(loginID, password, fName, lName, email, creditCard, startingCash);
        } catch(SQLException e) {
            System.err.println("Error in register(): " + e.getMessage());
        }
        return userID;
    }

    /**
     * Attempt to login
     * @param userName
     * @param password
     * @return LOGIN_FAILED if failed, otherwise the userID
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized int login(String userName, String password) throws RemoteException {
        int userID = LOGIN_FAILED;
        try {
            userID = data.login(userName, password);
            if(userExists(userID)){
                userID = LOGIN_FAILED;
            }
            else{
                initGame(true);
                playerHand = new Hand();
                dealerHand = new Hand();
                checkLogic = new CheckLogic(playerHand, dealerHand);
                users.add(userID);
            }
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
    public synchronized double getBank(int userID) throws RemoteException, UnknownUserException {
        double bank = 0;
        if(userExists(userID)){
            try {
                bank = data.getBank(userID);
            }
            catch(SQLException sqle){
                System.err.println("Error in retrieving bank: " + sqle.getMessage());
            }
            if(bank == -1){
                System.out.println("Error retrieving bank");
            }
        }
        else{
            throw new UnknownUserException();
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
    @Override
    public synchronized double updateBank(int userID, double money) throws RemoteException, UnknownUserException {
        double temp = -1;
        if(userExists(userID)){
            try {
                temp = data.updateBank(userID, money);
            }
            catch(SQLException sqle){
                System.err.println("Error in updating bank: " + sqle.getMessage());
            }
        }
        else{
            throw new UnknownUserException("Can't update bank for user with" +
                    " userID " + userID + ": doesn't exist in server list");
        }
        return temp;
    }

    /**
     * Updates user card stats of this user
     * @param userID
     * @param character
     * @throws java.rmi.RemoteException
     */
    @Override
    public synchronized void updateUserCardStats(int userID, char character) throws RemoteException {
        try{
            data.updateUserCardStats(userID, character);
        }
        catch(SQLException sqle){
            System.err.println("Error in updating user card stats: " + sqle.getMessage());
        }
    }

    /**
     * Retrieves this user's card stats
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    public synchronized AccountCardStats getCardStats(int userID) throws RemoteException {
        AccountCardStats acs = null;
        try{
            acs = data.getCardStats(userID);
        }
        catch(SQLException sqle){
            System.err.println("Error in getting user card stats: " + sqle.getMessage());
        }
        return acs;
    }

    /**
     * Delete a user account
     * @param userID
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
	public synchronized boolean deleteAccount(int userID) throws RemoteException, UnknownUserException {
        boolean ok = false;
        if(userExists(userID)){
            try {
                ok = data.deleteAccount(userID);
            } catch(SQLException e){
                System.err.println("Error in deleteAccount(): " + e.getMessage());
            }
        }
        else{
            throw new UnknownUserException("Can't delete user with userID " + userID + ": does not exist in server list");
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
    public synchronized AccountInformation getInfos(int userID) throws RemoteException, UnknownUserException {
        AccountInformation ai = null;
        if(userExists(userID)){
            try {
                ai = data.getInfos(userID);
            } catch(SQLException e){
                System.err.println("Error in getInfos(): " + e.getMessage());
            }
        }
        else{
            throw new UnknownUserException("Can't get infos for user with userID "
                    + userID + ": doesn't exist in server list");
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
    public synchronized void writeInfos(int userID, AccountInformation ai) throws RemoteException, UnknownUserException {
        if(userExists(userID)){
            try {
                data.writeInfos(userID, ai);
            } catch(SQLException e){
                System.err.println("Error in writeInfos(): " + e.getMessage());
            }
        }
        else{
            throw new UnknownUserException("Can't write infos for user with" +
                    " userID " + userID + ": doesn't exist in server list");
        }
    }

    /**
     * Check if a user exists
     * @param userID
     * @return
     */
    private synchronized boolean userExists(int userID){
        boolean exists = false;
        for(int i = 0; i < users.size(); i++){
            if(userID == users.get(i)){
                exists = true;
            }
        }
        return exists;
    }

    public synchronized void kickUser(int userID) throws RemoteException, UnknownUserException {
        if(!logout(userID)){
			throw new UnknownUserException();
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
            startTime = new Date();
        } catch(Exception e) {
            System.err.println("The server encountered an error while trying to connect to the database.");
            System.err.println("The error is: " + e.getMessage());
            System.exit(1);
        }
        
        System.out.println("Starting the server.");

		//Add a hook to unbind the server when we close it
		Runtime.getRuntime().addShutdownHook(new ServerExitThread());

		//Bind the server
        server.bind();
		
        System.out.println("Server started");
    }

    /**
    * method to get servers' start time
    * @return start time of the server in millis
    * @throws java.rmi.RemoteException
    */
    public long getStartTime() throws RemoteException {
        return startTime.getTime();
    }

    /**
    * method to get servers' current time
    * @return current time of the server in millis
    * @throws java.rmi.RemoteException
    */
    public long getCurrentTime() throws RemoteException {
        currentTime = new Date();
        return currentTime.getTime();
    }

	/**
     * Logout
     * @param userID
     * @return true if the user got logged out
     * @throws java.rmi.RemoteException
     */
	public boolean logout(int userID) throws RemoteException {
		boolean removed = false;
        if(userExists(userID)){
            try{ removeUser(userID); }
            catch(RemoteException re) { }
            removed = true;
        }
        return removed;
	}
}
