/**
 * Database connection layer
 * @author Tyler Schindel, Emilien Girault
 * @date 1/12/09
 */


import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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
   * The unique instance of the class (Singleton Design Pattern)
   */
  private static DBConnection instance = null;

  /**
   * Connection object
   */
  Connection connect;

  /**
   * Return the unique instance of the class (Singleton Design Pattern)
   * @return DBConnection instance
   */
  public static DBConnection instance() throws SQLException {
    if(instance == null){
      instance = new DBConnection();
    }
    return instance;
  }

  /**
   * Private constructor (use instance() instead)
   */
	private DBConnection() throws SQLException {
    connect();
	}

  /**
   * Connect to the database
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
   */
  public void close() throws SQLException{
    connect.close();
  }

	public void executeNonQuery(String query) throws SQLException {
    Statement st = connect.createStatement();
    st.execute(query);
	}

	public ResultSet executeQuery(String query) throws SQLException {
    ResultSet rs = null;
    Statement st = connect.createStatement();
    rs = st.executeQuery(query);
		return rs;
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
      DBConnection db = instance();
      System.out.println("Connection opened successfully");

      //Close
      db.close();
      System.out.println("Connection closed successfully");

    } catch(SQLException e) {
      System.out.println(e.getMessage());
    }
	}
}