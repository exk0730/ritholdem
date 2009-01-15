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
    private Server() throws SQLException, RemoteException {
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
    public boolean register(String loginID, String password, String fName,
                            String lName, String email, String creditCard, double startingCash) throws RemoteException {
        boolean ok = false;
        try {
            /**
             * TODO: handle the last 2 params
             */
            ok = data.register(loginID, password, fName, lName, email);
        } catch(SQLException e) {
            ok = false;
        }
        return ok;
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
     * /!\ Be sure to run rmiregistry in the class path before running this program
     * @param args arguments
     */
    public static void main(String args[]){
        Server server = null;
        try {
            server = instance();
        } catch(Exception e) {
            System.out.println("The server encountered an error while trying to tonnect to the database.");
            System.out.println("The error is: " + e.getMessage());
            System.exit(1);
        }
        try {
            //System.out.println("name = " + server.data.getMaxUserID());
            //System.out.println("register = " + server.register("emilien", "mypass", "Emilien", "Girault", "bob@sponge.com", "", ""));
            Naming.rebind(SERVER_NAME, server);

        } catch(Exception e){
            System.out.println("Server error: Impossible to bind object to registry.");
            System.out.println("Did you launch 'rmiregistry' in the 'classes' folder of the project?");
            System.exit(1);
        }
    }

}
