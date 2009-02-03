/**
 * Database connection layer
 * @author Emilien Girault
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
     * @param creditCard user's credit card
     * @param initCash start amount of money
     * @return true if he has been successfully registered, false if the login already exists
     * @throws java.sql.SQLException
     */
    public synchronized boolean register(String userName, String password, String fName, String lName, String email, String creditCard, double initCash) throws SQLException {
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
                    "INSERT INTO FinancialData (userID, bank, creditCardNum, allowEmergencyFunding" +
                                                ", emergencyFundAmt) VALUES (?, ?, ?, ?, ?)"
                    );
            pst.setInt(1, userID);
            pst.setDouble(2, initCash);
            pst.setString(3, creditCard);
            pst.setInt(4, 0);
            pst.setDouble(5, 0.00);
            pst.executeUpdate();
        }
        return ok;
    }

    /**
     * Attempt to login a user
     * @param userID
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
     * @param userName
     * @param password
     * @return true if the account has been successfully deleted, false otherwise
     * @throws java.sql.SQLException
     */
    public synchronized boolean deleteAccount(int userID, String userName, String password) throws SQLException {
        //Multi-table DELETE (check http://dev.mysql.com/doc/refman/5.0/en/delete.html for syntax)
        PreparedStatement pst = db.newPreparedStatement("DELETE Users, UserInfo FROM Users INNER JOIN UserInfo " +
                "WHERE Users.userID = (?) AND userName = (?) AND pwd = (?) AND UserInfo.userID = Users.userID");
        pst.setInt(1, userID);
        pst.setString(2, userName);
        pst.setString(3, password);
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

    public String getTopPlayers() throws SQLException {
        String s = "";
        ResultSet rs = db.executeQuery("SELECT fName, lName, userEarnings FROM UserMoneyStats " +
                                        "NATURAL JOIN UserInfo ORDER BY userEarnings DESC");
        while(rs.next()){
            s += "First Name: " + rs.getString("fName") + ", Last Name: " + rs.getString("lName") +
                     ", Current Earnings: " + rs.getString("userEarnings") + "_";
        }
        return s;
    }

    /**
     * Get a user's bank amount
     * @param int userID
     * @return user's money
     * @throws SQLException
     */
    public double getBank(int userID) throws SQLException{
        double temp = -1;
        if(userExists(userID))
        {
            PreparedStatement pst = db.newPreparedStatement("SELECT bank FROM FinancialData WHERE FinancialData.userID = (?)");
            pst.setInt(1, userID);
            ResultSet rs = pst.executeQuery();
            if(rs.next()){
                temp = rs.getDouble("bank");
            }
        }
        return temp;
    }

    /**
     * Updates the user's amount of money
     * @param int userID
     * @param double money
     * @return the updated bank
     */
    public double updateBank(int userID, double money) throws SQLException {
        double temp = -1;
        if(userExists(userID))
        {
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
        }
        return temp;
    }

    /**
     * Checks if user exists
     * @param userID
     * @return false if this userID does not exist in FinancialData
     * @throws java.sql.SQLException
     */
    public boolean userExists(int userID) throws SQLException{
        boolean ok = false;
        PreparedStatement pst = db.newPreparedStatement("SELECT userID FROM FinancialData WHERE FinancialData.userID = (?)");
        pst.setInt(1, userID);
        ResultSet rs = pst.executeQuery();
        if(rs.next()){
            ok = true;
        }
        return ok;
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

}
