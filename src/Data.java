/**
 * Database connection layer
 * @author Emilien Girault
 * @date 1/12/09
 */

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
     * @return true if he has been successfully registered, false if the login already exists
     * @throws java.sql.SQLException
     */
    public synchronized boolean register(String userName, String password, String fName, String lName, String email) throws SQLException {

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
    * Get the maximum user ID
    * TODO
    */
    public synchronized Object getMaxUserID() throws SQLException {
        
        String query = "SELECT MAX(UserID) FROM Users";
        return db.executeQuery(query);
    }


}
