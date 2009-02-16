/**
 * Database connection layer
 * @author Emilien Girault, Tyler Schindel
 * @date 1/12/09
 */
package database;
import game.*;
import java.sql.*;


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
                    "INSERT INTO FinancialData (userID, bank, creditCardNum VALUES (?, ?, ?)"
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

    public synchronized void updateUserEarnings(int userID, double moneyEarned) throws SQLException {
        double temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT userEarnings, bank FROM UserMoneyStats" +
                " NATURAL JOIN FinancialData WHERE usedID = (?)");
        pst.setInt(1,userID);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            double bank = rs.getDouble("bank");
            if( (bank + moneyEarned) > bank){
                temp = rs.getDouble("userEarnings");
                temp += moneyEarned;
                pst = db.newPreparedStatement("UPDATE UserMoneyStats SET userEarnings = (?) WHERE userID = (?)");
                pst.setDouble(1, temp);
                pst.setInt(2, userID);
                pst.executeUpdate();
            }
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
     * Updates with new server startup time
     * @throws SQLException
     */
    public void updateLastServerReboot() throws SQLException
    {
        java.util.Date date = new java.util.Date();
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.lastServerReboot FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        pst.setDate(1,(Date)date);
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            pst = db.newPreparedStatement("UPDATE ServerStats SET lastserverreboot = (?)");
            pst.executeUpdate();
        }
    }

    /**
     * Updates server with total amount bet in game
     * @param money
     * @throws SQLException
     */
    public void updateServerTotalBet(double money) throws SQLException
    {
        double temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.totalAmtBet FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            temp = rs.getDouble("totalAmtBet");
            temp += money;
            pst = db.newPreparedStatement("UPDATE ServerStats SET totalAmtBet = (?) WHERE (SELECT MAX(serverID))");
            pst.setDouble(1, temp);
            pst.executeUpdate();
        }
    }
    
    /**
     * Updates server with dealer earnings
     * @param money
     * @throws SQLException
     */
    public void updateDealerEarnings(double money) throws SQLException
    {
        double temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.dealerEarnings FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            temp = rs.getDouble("dealerEarnings");
            temp += money;
            pst = db.newPreparedStatement("UPDATE ServerStats SET dearlerEarnings = (?) WHERE (SELECT MAX(serverID))");
            pst.setDouble(1, temp);
            pst.executeUpdate();
        }
    }

    /**
     * Updates server with number of new users
     * @param user
     * @throws SQLException
     */
    public void updateNumOfNewUsers(int user) throws SQLException
    {
        int temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.numNewUsers FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            temp = rs.getInt("numNewUsers");
            temp += user;
            pst = db.newPreparedStatement("UPDATE ServerStats SET numNewUsers = (?) WHERE (SELECT MAX(serverID))");
            pst.setInt(1, temp);
            pst.executeUpdate();
        }
    }
     /**
     * Updates server with number of users played
     * @param usersPlayed
     * @throws SQLException
     */
    public void updateNumOfUsersPlayed(int usersPlayed) throws SQLException
    {
        int temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.numUsersPlayed FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            temp = rs.getInt("numUsersPlayed");
            temp += usersPlayed;
            pst = db.newPreparedStatement("UPDATE ServerStats SET numUsersPlayed = (?) WHERE (SELECT MAX(serverID))");
            pst.setInt(1, temp);
            pst.executeUpdate();
        }
    }

        /**
     * Updates server with total blackjacks dealt
     * @param blackjacks
     * @throws SQLException
     */
    public void updateTotalBlackJacks(int blackjacks) throws SQLException
    {
        int temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.totalBlackjacks FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            temp = rs.getInt("totalBlackjacks");
            temp += blackjacks;
            pst = db.newPreparedStatement("UPDATE ServerStats SET totalBlackjacks = (?) WHERE (SELECT MAX(serverID))");
            pst.setInt(1, temp);
            pst.executeUpdate();
        }
    }

    /**
     * Updates server with number of user wins
     * @param userWins
     * @throws SQLException
     */
    public void updateUserWins(int userWins) throws SQLException
    {
        int temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.userWins FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            temp = rs.getInt("totalBlackjacks");
            temp += userWins;
            pst = db.newPreparedStatement("UPDATE ServerStats SET userWins = (?) WHERE (SELECT MAX(serverID))");
            pst.setInt(1, temp);
            pst.executeUpdate();
        }
    }

    /**
     * Updates server with number of dealer wins
     * @param dealerWins
     * @throws SQLException
     */
    public void updateDealerWins(int dealerWins) throws SQLException
    {
        int temp = -1;
        PreparedStatement pst = db.newPreparedStatement("SELECT ServerStats.dealerWins FROM ServerStats WHERE (SELECT MAX(serverID) FROM ServerStats)");
        ResultSet rs = pst.executeQuery();

        if(rs.next())
        {
            temp = rs.getInt("dealerWins");
            temp += dealerWins;
            pst = db.newPreparedStatement("UPDATE ServerStats SET dealerWins = (?) WHERE (SELECT MAX(serverID))");
            pst.setInt(1, temp);
            pst.executeUpdate();
        }
    }

    /**
     * Updates server with number of dealer wins
     * @return latest server stats
     * @throws SQLException
     */
    public String getCurrServerStats() throws SQLException
    {
        String str = "";
        ResultSet rs = db.executeQuery("SELECT numNewUsers AS 'Number of New Users', " +
                                              "numUsersPlayed AS 'Number of Users Played' " +
                                              "totalAmtBet AS 'Total Amount Bet'" +
                                              "dealerWins AS 'Dealer Wins'" +
                                              "userWins AS 'User Wins'" +
                                              "dealerEarnings AS 'Dealer Earnings'" +
                                              "totalBlackjacks AS 'Total Blackjacks'" +
                                              "lastServerReboot AS 'Last Server Reboot'" +
                                              "FROM ServerStats WHERE (SELECT MAX(serverID))");
        if(rs.next())
        {
            str = rs.getInt(1) + " " + rs.getInt(2) + " " + rs.getDouble(3) + " " +
                  rs.getDouble(4) + " " + rs.getDouble(5) + " " + rs.getDouble(6) + " " +
                  rs.getInt(7) + " " + rs.getDate(8);
        }
        return str;
    }
}
