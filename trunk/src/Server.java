/**
 * Server main class
 * @author Emilien Girault
 * @date 1/12/09
 */

import java.rmi.*;
import java.sql.*;

public class Server extends java.rmi.server.UnicastRemoteObject implements RMIInterface {

    /**
     * The server RMI name
     */
    public static String SERVER_NAME = "BlackJackServer";

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
    private Server() throws Exception {
        data = Data.instance();
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


    @Override
    public Card hit(String login) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void stand(String login) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Card dble(String login, double bet) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void highStakes(String login) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean register(String loginID, String password, String fName, String lName, String email, String creditCard, double startingCash) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void login(String loginID, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double getMoney(String loginID) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String loginID, String password) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AccountInformation query(String login) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

        /**
     * The main program
     * @param args arguments
     */
    public static void main(String args[]){
        try {
            Server s = instance();
            //System.out.println("name = " + s.data.getMaxUserID());
            System.out.println("register = " + s.data.register("emilien", "mypass", "Emilien", "Girault", "bob@sponge.com"));
            //Naming.rebind(SERVER_NAME, s);


            /**
             *
             * TODO
             *
             * Run rmic 
             * Run rmiregistery
             * (howto in Netbeans ??)
             *
             *
             *
             *
             *
             */

        } catch(Exception e){
            System.out.println("Server error: "+e.getMessage());
            e.printStackTrace();
        }
    }

}
