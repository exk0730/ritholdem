/**
 * Database connection layer
 * @author Tyler Schindel, Emilien Girault
 * @date 1/12/09
 */


import java.sql.*;
/* //TODO delete if unused
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
*/

public class DBConnection
{

    /**
     * Static configuration variables
     */
    private static String DRIVER = "com.mysql.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost/blackjack";
    private static String UID = "blackjack";
    private static String PASS = "blackjack";

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
        return connect.prepareStatement(query);
    }



/*
//To remove if not used
private String getDateTime(){
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
}
*/

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
        }
	}
}