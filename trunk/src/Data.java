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
        db = DBConnection.instance();
    }


    //
    // /!\ All methods and accessors MUST be synchronized /!\
    //

    /**
    * Register a user
    * TODO
    */
    public synchronized void register(String s) throws SQLException {
        String query = "INSERT users (userName, password, fName, lName) VALUES (***********)";
        db.executeNonQuery(query);
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
