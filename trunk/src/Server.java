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
    public Data data; //TODO change into private

    /**
     * Unique instance (Singleton Design Pattern)
     */
    private static Server instance = null;

    /**
     * Private constructor (use instance() instead)
     * @throws java.sql.SQLException
     */
    private Server() throws SQLException {
        data = Data.instance();
    }

    /**
     * Return the unique instance of the class (Singleton Design Pattern)
     * @return Server instance
     * @throws java.sql.SQLException
     */
    public static Server instance() throws SQLException {
        if(instance == null){
          instance = new Server();
        }
        return instance;
    }

    /**
     * The main program
     * @param args arguments
     */
    public static void main(String args[]){
        try {
            Server s = instance();
            //System.out.println("name = " + s.data.getMaxUserID());
            System.out.println("register = " + s.data.register("eric", "coucou", "moi", "lui"));

        } catch(SQLException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

}
