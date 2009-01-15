/**
 * Server main class
 * @author Emilien Girault
 * @date 1/12/09
 */

import java.sql.*;

public class Server {

    /**
    * Database
    */
    Data data;

    /**
    * Unique instance (Singleton Design Pattern)
    */
    private static Server instance = null;

    /**
    * Private constructor (use instance() instead)
    */
    private Server() throws SQLException {
        data = Data.instance();
    }

    /**
    * Return the unique instance of the class (Singleton Design Pattern)
    * @return Server instance
    */
    public static Server instance() throws SQLException {
        if(instance == null){
          instance = new Server();
        }
        return instance;
    }

    public static void main(String args[]){
        try {
            Server s = instance();
        } catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}
