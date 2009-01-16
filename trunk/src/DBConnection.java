/**
 * Database connection layer
 * @author Tyler Schindel, Emilien Girault
 * @date 1/12/09
 */
import java.sql.*;


public class DBConnection
{

    /**
     * Static configuration variables
     */
    public static String HOST = "localhost";
    public static String DATABASE = "blackjack";
    public static String UID = "blackjack";
    public static String PASS = "blackjack";
    public static String DRIVER = "com.mysql.jdbc.Driver";
    public static String URL = "jdbc:mysql://"+HOST+"/"+DATABASE;

    /**
     * Connection object
     */
    Connection connect;

    /**
     * Constructor
     */
    public DBConnection() throws SQLException {
        connect();
    }

    /**
     * Connect to the database
     * @throws java.sql.SQLException
     */
    private void connect() throws SQLException {
        try {
            Class.forName(DRIVER).newInstance();
        } catch(Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        connect = DriverManager.getConnection(URL, UID, PASS);
    }

    /**
     * Close the connection
     * @throws java.sql.SQLException
     */
    public void close() throws SQLException{
        connect.close();
    }

    /**
     * Execute a query that doesn't return a result
     * @param query the query
     * @throws java.sql.SQLException
     */
    public void executeNonQuery(String query) throws SQLException {
        Statement st = connect.createStatement();
        st.execute(query);
    }

    /**
     * Execute a query that returns a result
     * @param query the query
     * @return the resultset
     * @throws java.sql.SQLException
     */
    public ResultSet executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        Statement st = connect.createStatement();
        rs = st.executeQuery(query);
        return rs;
    }

    /**
     * Create a new prepared statement (to prevent SQL injections)
     * @param s
     * @throws java.sql.SQLException
     */
    public PreparedStatement newPreparedStatement(String query) throws SQLException {
        return connect.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
    }


	// For testing purposes
	public static void main(String args[]){
            try {
                //Connect
                DBConnection db = new DBConnection();
                System.out.println("Connection opened successfully");

                //Close
                db.close();
                System.out.println("Connection closed successfully");

            } catch(SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
	}
}