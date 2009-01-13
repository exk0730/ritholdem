/*
**@author Tyler Schindel
** Connecting Blackjack Class
** 1/12/09
*/

import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DBConnection
{

	Connection connect;


	static String DRIVER = "com.mysql.jdbc.Driver";
	static String URL = "jdbc:mysql://localhost/blackjack";
	static String UID = "blackjack";
	static String PASS = "blackjack";

  static DBConnection instance = null;

  /**
   * Singleton Design Pattern
   * @return DBConnection instance
   */
  public static DBConnection instance(){
    if(instance == null){
      instance = new DBConnection();
    }
    return instance;
  }

  /**
   * Private constructor (use instance() instead)
   */
	private DBConnection()
	{
    connect();
	}

  /**
   * Connect to the database
   */
  private void connect(){
    try{
      Class.forName(DRIVER).newInstance();
      connect = DriverManager.getConnection(URL, UID, PASS);
    } catch(Exception e){
      System.out.println("Connection Error: " + e.getMessage());
    }
  }

	public void executeNonQuery(String query){
    try {
      Statement st = connect.createStatement();
      st.execute(query);
    } catch(SQLException e){
      System.out.println(e.getMessage());
    }
	}

	public ResultSet executeQuery(String query){
    ResultSet rs = null;
    try {
      Statement st = connect.createStatement();
      rs = st.executeQuery(query);
    } catch(SQLException e){
      System.out.println(e.getMessage());
    }
		return rs;
	}

	//
	// Methods
	//
	public void register(String s){
		String query = "INSERT users (userName, password, fName, lName) VALUES (***********)";
    executeNonQuery(query);
	}

	//
	// Accessors
	//
	public Object getMaxUserID(){
		String query = "SELECT MAX(UserID) FROM Users";
		executeQuery(query);
    return new Object();
	}


	public void close(){
		try	{
			connect.close();
		} catch(Exception e){
			System.out.println("Close Connections Error: " + e.getMessage());
		}
	}

	private String getDateTime(){
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    return dateFormat.format(date);
  }

	// For testing purposes
	public static void main(String args[]){
		DBConnection db = instance();
		db.close();
	}
}