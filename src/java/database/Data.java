/**
 * Database connection layer
 * @author Emilien Girault, Tyler Schindel
 * @date 1/12/09
 */
package database;
import game.*;
import java.sql.*;
import java.util.ArrayList;


public class Data {

    /**
     * The unique instance of the class (Singleton Design Pattern)
     */
    private static Data instance = null;

    /**
     * Database connection
     */
    private DBConnection db;

	/**
	 * Last serverID for statistics
	 */
	private int serverID;

    /**
     * Return the unique instance of the class (Singleton Design Pattern)
     * @return Data instance
     * @throws java.sql.SQLException
     */
    public static Data instance() throws SQLException {
        if(instance == null){
            instance = new Data();
        }
        return instance;
    }

    /**
    * Private constructor (use instance() instead)
    */
    private Data() throws SQLException {
        db = new DBConnection();
		serverID = getLastServerID();
        updateServerInfo();
		serverID++;
    }


    //
    // /!\ All methods and accessors MUST be synchronized /!\
    //

    /**
     * Register a new user
     * @param userName the user's login
     * @param password the user's password
     * @param fName his first name
     * @param lName his last name
     * @param email
     * @param creditCard user's credit card
     * @param initCash start amount of money
     * @return true if he has been successfully registered, false if the login already exists
     * @throws java.sql.SQLException
     */
    public synchronized int register(String userName, String password, String fName, String lName, String email, String creditCard, double initCash) throws SQLException {
        boolean ok = false;
        PreparedStatement pst;
        ResultSet rs;
        int userID = -1;

        //Check if the username does not already exist
        pst = db.newPreparedStatement("SELECT COUNT(userID) FROM Users WHERE userName = (?)");
        pst.setString(1, userName);
        rs = pst.executeQuery();
        if(rs.next()){
            ok = (rs.getInt(1) == 0);
        }

        //Insert the new user
        if(ok){
            pst = db.newPreparedStatement("INSERT INTO Users (userName, pwd) VALUES(?, ?);");
            pst.setString(1, userName);
            pst.setString(2, password);
            pst.executeUpdate();

            //Get the AUTO_INCREMENT value
            rs = pst.getGeneratedKeys();
            if(rs.next()){
                userID = rs.getInt(1);
                ok = (userID != -1);
            }
        }

        //Insert the user infos
        if(ok){
            pst = db.newPreparedStatement(
                    "INSERT INTO UserInfo (userID, fName, lName, email, dateJoined) VALUES (?, ?, ?, ?, NOW())"
                    );
            pst.setInt(1, userID);
            pst.setString(2, fName);
            pst.setString(3, lName);
            pst.setString(4, email);
            pst.executeUpdate();

        }

        //Insert into financial data
        if(ok)
        {
            pst = db.newPreparedStatement(
                    "INSERT INTO FinancialData (userID, bank, creditCardNum) VALUES (?, ?, ?)"
                    );
            pst.setInt(1, userID);
            pst.setDouble(2, initCash);
            pst.setString(3, creditCard);
            pst.executeUpdate();
        }

        if(ok){
            pst = db.newPreparedStatement(
                    "INSERT INTO UserCardStats (userID) VALUES (?)"
                    );
            pst.setInt(1,userID);
            pst.executeUpdate();
        }

        if(ok){
            pst = db.newPreparedStatement(
                    "INSERT INTO UserMoneyStats (userID) VALUES (?)"
                    );
            pst.setInt(1,userID);
            pst.executeUpdate();
        }
        return userID;
    }

    /**
     * Attempt to login a user
     * @param userName
     * @param password
     * @return true if OK, false if the userID/password don't match
     * @throws java.sql.SQLException
     */
    public synchronized int login(String userName, String password) throws SQLException {
        int userID = RMIInterface.LOGIN_FAILED;
        PreparedStatement pst;
        ResultSet rs;

        //Check username and password
        pst = db.newPreparedStatement("SELECT userID FROM Users WHERE userName = (?) AND pwd = (?)");
        pst.setString(1, userName);
        pst.setString(2, password);
        rs = pst.executeQuery();
        if(rs.next()){
            userID = rs.getInt(1);
        }

        //Update the dateLastPlayed field in UserInfo
        if((userID != RMIInterface.LOGIN_FAILED)){
            pst = db.newPreparedStatement("UPDATE UserInfo SET dateLastPlayed = NOW() WHERE userID = (?)");
            pst.setInt(1, userID);
            pst.executeUpdate();
        }

        return userID;
    }

    /**
     * Delete a user account
     * TODO: delete also in the other tables
     * @param userID
     * @return true if the account has been successfully deleted, false otherwise
     * @throws java.sql.SQLException
     */
    public synchronized boolean deleteAccount(int userID) throws SQLException {
        //Multi-table DELETE (check http://dev.mysql.com/doc/refman/5.0/en/delete.html for syntax)
        PreparedStatement pst = db.newPreparedStatement("DELETE Users, UserInfo FROM Users INNER JOIN UserInfo " +
                "WHERE Users.userID = (?) AND userName = (?) AND pwd = (?) AND UserInfo.userID = Users.userID");
        pst.setInt(1, userID);
        return (pst.executeUpdate() > 0);
    }

    /**
     * Get a user's informations
     * @param userID
     * @return the user's informations
     * @throws java.sql.SQLException
     */
    public AccountInformation getInfos(int userID) throws SQLException {
        AccountInformation ai = null;
        PreparedStatement pst = db.newPreparedStatement("SELECT * FROM Users, UserInfo WHERE Users.userID = (?) AND UserInfo.userID = Users.userID");
        pst.setInt(1, userID);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            ai = new AccountInformation(rs.getString("userName"), rs.getString("pwd"), rs.getString("fName"),
                    rs.getString("lName"), rs.getString("email"), rs.getDate("dateLastPlayed"), rs.getDate("dateJoined"));

        }
        return ai;
    }

    /**
     * Gets the users card stats
     * @param userID
     * @return
     * @throws java.sql.SQLException
     */
    public AccountCardStats getCardStats(int userID) throws SQLException {
        AccountCardStats acs = null;
        PreparedStatement pst = db.newPreparedStatement("SELECT * FROM UserCardStats WHERE userID = (?)");
        pst.setInt(1, userID);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            acs = new AccountCardStats(rs.getInt("numOfBlackjacks"), rs.getInt("numOfHits"),rs.getInt("numOfStands"),
                    rs.getInt("numOfDoubles"), rs.getInt("numOfWins"), rs.getInt("numOfLoss"), rs.getInt("numOfPushes"));
        }
        return acs;
    }

    /**
     * Get the top players 
     * @return concatenated String of all users sorted by current earnings
     * @throws java.sql.SQLException
     */
    public synchronized String getTopPlayers() throws SQLException {
        String s = "";
        ResultSet rs = db.executeQuery("SELECT fName, lName, userEarnings FROM UserMoneyStats " +
                                        "NATURAL JOIN UserInfo ORDER BY userEarnings DESC");
		int i = 1;
        while(rs.next()){
            s += i++ + ": " + rs.getString("fName") + " " + rs.getString("lName") +
                     " - $" + rs.getString("userEarnings") + "\n";
        }
        return s;
    }

    /**
     * Updates the database for user based on their action
     * @param userID
     * @param character
     * @throws java.sql.SQLException
     */
    public synchronized void updateUserCardStats(int userID, char character) throws SQLException{
        PreparedStatement pst = null;
        ResultSet rs = null;
        int temp = 0;
        String getStatement = "";
        String updateStatement = "";
        switch(character){
            case 'b':
                pst = db.newPreparedStatement("SELECT numOfBlackjacks FROM UserCardStats " +
                        "WHERE userID = (?)");
                pst.setInt(1,userID);
                rs = pst.executeQuery();
                getStatement = "numOfBlackjacks";
                updateStatement = "UPDATE UserCardStats SET numOfBlackjacks = (?) WHERE userID = (?)";
                break;
            case 'h':
                pst = db.newPreparedStatement("SELECT numOfHits FROM UserCardStats " +
                        "WHERE userID = (?)");
                pst.setInt(1,userID);
                rs = pst.executeQuery();
                getStatement = "numOfHits";
                updateStatement = "UPDATE UserCardStats SET numOfHits = (?) WHERE userID = (?)";
                break;
            case 's':
                pst = db.newPreparedStatement("SELECT numOfStands FROM UserCardStats " +
                        "WHERE userID = (?)");
                pst.setInt(1,userID);
                rs = pst.executeQuery();
                getStatement = "numOfStands";
                updateStatement = "UPDATE UserCardStats SET numOfStands = (?) WHERE userID = (?)";
                break;
            case 'd':
                pst = db.newPreparedStatement("SELECT numOfDoubles FROM UserCardStats " +
                        "WHERE userID = (?)");
                pst.setInt(1,userID);
                rs = pst.executeQuery();
                getStatement = "numOfDoubles";
                updateStatement = "UPDATE UserCardStats SET numOfDoubles = (?) WHERE userID = (?)";
                break;
            case 'w':
                pst = db.newPreparedStatement("SELECT numOfWins FROM UserCardStats " +
                        "WHERE userID = (?)");
                pst.setInt(1,userID);
                rs = pst.executeQuery();
                getStatement = "numOfWins";
                updateStatement = "UPDATE UserCardStats SET numOfWins = (?) WHERE userID = (?)";
                break;
            case 'l':
                pst = db.newPreparedStatement("SELECT numOfLoss FROM UserCardStats " +
                        "WHERE userID = (?)");
                pst.setInt(1,userID);
                rs = pst.executeQuery();
                getStatement = "numOfLoss";
                updateStatement = "UPDATE UserCardStats SET numOfLoss = (?) WHERE userID = (?)";
                break;
            case 'p':
                pst = db.newPreparedStatement("SELECT numOfPushes FROM UserCardStats " +
                        "WHERE userID = (?)");
                pst.setInt(1,userID);
                rs = pst.executeQuery();
                getStatement = "numOfPushes";
                updateStatement = "UPDATE UserCardStats SET numOfPushes = (?) WHERE userID = (?)";
                break;
            default:
                break;
        }
        if(rs.next()){
            temp = rs.getInt(getStatement);
            temp++;
            pst = db.newPreparedStatement(updateStatement);
            pst.setInt(1, temp);
            pst.setInt(2, userID);
            pst.executeUpdate();
        }
    }

    /**
     * Updates a user's total earnings
     * @param userID
     * @param moneyEarned
     * @throws java.sql.SQLException
     */
    public synchronized void updateUserEarnings(int userID, double moneyEarned) throws SQLException {
        double temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT userEarnings FROM UserMoneyStats" +
                " WHERE userID = (?)");
        pst.setInt(1,userID);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            temp = rs.getDouble("userEarnings");
            temp += moneyEarned;
            pst = db.newPreparedStatement("UPDATE UserMoneyStats SET userEarnings = (?) WHERE userID = (?)");
            pst.setDouble(1, temp);
            pst.setInt(2, userID);
            pst.executeUpdate();
        }

    }

    /**
     * Get a user's bank amount
     * @param userID
     * @return user's money
     * @throws SQLException
     */
    public synchronized double getBank(int userID) throws SQLException{
        double temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT bank FROM FinancialData WHERE FinancialData.userID = (?)");
        pst.setInt(1, userID);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            temp = rs.getDouble("bank");
        }
        return temp;
    }

    /**
     * Updates the user's amount of money
     * @param userID 
     * @param money 
     * @return the updated bank
     * @throws SQLException
     */
    public synchronized double updateBank(int userID, double money) throws SQLException {
        double temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT bank FROM FinancialData WHERE FinancialData.userID = (?)");
        pst.setInt(1, userID);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            temp = rs.getDouble("bank");
            temp += money;
            pst = db.newPreparedStatement("UPDATE FinancialData SET bank = (?) WHERE userID = (?)");
            pst.setDouble(1, temp);
            pst.setInt(2, userID);
            pst.executeUpdate();
        }
        return temp;
    }
    
    /**
     * Write the user's infos to the database
     * @param userID
     * @param ai the user's infos
     * @throws java.sql.SQLException
     */
    public void writeInfos(int userID, AccountInformation ai) throws SQLException {
        PreparedStatement pst = db.newPreparedStatement("UPDATE Users, UserInfo "+
                "SET pwd = ?, userName = ?, fName = ?, lName = ?, email = ?, dateLastPlayed = ?, dateJoined  = ? " +
                "WHERE Users.userID = ? AND UserInfo.userID = Users.userID");
        pst.setString(1, ai.getPassword());
        pst.setString(2, ai.getUserName());
        pst.setString(3, ai.getFirstName());
        pst.setString(4, ai.getLastName());
        pst.setString(5, ai.getEmail());
        pst.setDate(6, ai.getDateLastPlayed());
        pst.setDate(7, ai.getDateJoined());
        pst.setInt(8, userID);
        pst.executeUpdate();
    }

    //
    //  Server Stats
    //

	/**
	 * Get the last server ID for statistics
	 * @return last server ID
	 * @throws java.sql.SQLException
	 */
	protected int getLastServerID() throws SQLException {
		int res = -1;
		PreparedStatement pst = db.newPreparedStatement("SELECT MAX(serverID) FROM ServerStats");
        ResultSet rs = pst.executeQuery();
		if(rs.next()){
			res = rs.getInt(1);
		}
		return res;
	}

    /**
     * Updates the server log when a new server is started
     * @throws java.sql.SQLException
     */
    protected void updateServerInfo() throws SQLException {
        ServerStatistics ss = getCurrServerStats();
        PreparedStatement pst = db.newPreparedStatement(
                "INSERT INTO ServerStats VALUES (?,?,?,?,?,?,?)"
                );
        pst.setInt(1, ss.getNumNewUsers());
        pst.setDouble(2, ss.getTotalAmtBet());
        pst.setInt(3, ss.getDealerWins());
        pst.setInt(4, ss.getUserWins());
        pst.setDouble(5, ss.getDealerEarnings());
        pst.setInt(6, ss.getTotalBlackjacks());
        pst.setDate(7, ss.getLastServerReboot());
        pst.executeUpdate();
    }


    /**
     * Updates with new server startup time
     * @throws SQLException
     */
    public void updateLastServerReboot() throws SQLException
    {
        PreparedStatement pst = db.newPreparedStatement("UPDATE ServerStats SET lastServerReboot = NOW() WHERE  serverID = ?");
        pst.setInt(1, serverID);
        pst.executeUpdate();
    }

    /**
     * Updates server with every bet made in game
     * @param money
     * @throws SQLException
     */
    public void updateServerTotalBet(double money) throws SQLException
    {
		PreparedStatement pst = db.newPreparedStatement("UPDATE ServerStats SET totalAmtBet = totalAmtBet + ? WHERE  serverID = ?");
		pst.setDouble(1, money);
		pst.setInt(2, serverID);
		pst.executeUpdate();
    }
    
    /**
     * Updates server with dealer earnings
     * @param money
     * @throws SQLException
     */
    public void updateDealerEarnings(double money) throws SQLException
    {
		PreparedStatement pst = db.newPreparedStatement("UPDATE ServerStats SET dealerEarnings = dealerEarnings + ? WHERE serverID = ?");
		pst.setDouble(1, money);
		pst.setInt(2, serverID);
		pst.executeUpdate();
    }

    /**
     * Updates server with number of new users
     * @param user
     * @throws SQLException
     */
    public void incrementNumOfNewUsers() throws SQLException
    {
		PreparedStatement pst = db.newPreparedStatement("UPDATE ServerStats SET numNewUsers = numNewUsers + 1 WHERE serverID = ?");
		pst.setInt(1, serverID);
		pst.executeUpdate();
    }

        /**
     * Updates server with total blackjacks dealt
     * @param blackjacks
     * @throws SQLException
     */
    public void incrementTotalBlackjacks() throws SQLException
    {
        PreparedStatement pst = db.newPreparedStatement("UPDATE ServerStats SET totalBlackjacks = totalBlackjacks + 1 WHERE serverID = ?");
		pst.setInt(1, serverID);
		pst.executeUpdate();
    }

    /**
     * Updates server with number of user wins
     * @param userWins
     * @throws SQLException
     */
    public void incrementUserWins() throws SQLException
    {
        PreparedStatement pst = db.newPreparedStatement("UPDATE ServerStats SET userWins = userWins + 1 WHERE serverID = ?");
		pst.setInt(1, serverID);
		pst.executeUpdate();
    }

    /**
     * Updates server with number of dealer wins
     * @param dealerWins
     * @throws SQLException
     */
    public void incrementDealerWins() throws SQLException
    {
        PreparedStatement pst = db.newPreparedStatement("UPDATE ServerStats SET dealerWins = dealerWins + 1 WHERE serverID = ?");
		pst.setInt(1, serverID);
		pst.executeUpdate();
    }

    /**
     * Get the current stats
     * @return latest server stats
     * @throws SQLException
     */
    public ServerStatistics getCurrServerStats() throws SQLException
    {
		ServerStatistics ss = null;
        ResultSet rs = db.executeQuery("SELECT numNewUsers, totalAmtBet, " +
                                              "dealerWins, userWins, dealerEarnings, " +
                                              "totalBlackjacks, lastServerReboot " +
                                              "FROM ServerStats WHERE (SELECT serverID = MAX(serverID) FROM ServerStats)");
        if(rs.next()) {
			ss = new ServerStatistics(	rs.getInt("numNewUsers"),
										rs.getDouble("totalAmtBet"),
										rs.getInt("dealerWins"),
										rs.getInt("userWins"),
										rs.getDouble("dealerEarnings"),
										rs.getInt("totalBlackjacks"),
										rs.getDate("lastServerReboot"));
        }
        return ss;
    }

	/**
     * Get the server stats corresponding to a given entry
     * @return server stats
     * @throws SQLException
     */
    public ServerStatistics getServerStats(int serverID) throws SQLException
    {
		ServerStatistics ss = null;
        PreparedStatement pst = db.newPreparedStatement("SELECT numNewUsers, totalAmtBet, " +
														  "dealerWins, userWins, dealerEarnings, " +
														  "totalBlackjacks, lastServerReboot " +
														  "FROM ServerStats WHERE serverID = ?");
		pst.setInt(1, serverID);
		ResultSet rs = pst.executeQuery();
        if(rs.next()) {
			ss = new ServerStatistics(	rs.getInt("numNewUsers"),
										rs.getDouble("totalAmtBet"),
										rs.getInt("dealerWins"),
										rs.getInt("userWins"),
										rs.getDouble("dealerEarnings"),
										rs.getInt("totalBlackjacks"),
										rs.getDate("lastServerReboot"));
        }
        return ss;
    }

	/**
     * Get all the server stats entries
     * @return server stats entries
     * @throws SQLException
     */
    public ArrayList<Integer> getServerStatsEntries() throws SQLException
    {
		ResultSet rs = db.executeQuery("SELECT serverID FROM ServerStats ORDER BY serverID DESC");
		ArrayList<Integer> res = new ArrayList<Integer>();
        if(rs.next()) {
			res.add(rs.getInt("serverID"));
        }
        return res;
    }

}
