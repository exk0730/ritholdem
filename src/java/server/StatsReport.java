package server;

import database.*;
import java.sql.SQLException;
import java.util.Date;
import jms.JMSPublisher;
/**
 * StatsReport will send a top player list using JMS
 * @author Eric Kisner
 */
public class StatsReport extends Thread {

	/**
	 * Delay for sending messages in miliseconds
	 */
	protected static int DELAY = 30 * 1000;

	/**
	 * The database
	 */
    private Data data;

	/**
	 * JMS Publisher for sending messages
	 */
	private JMSPublisher publisher;

	/**
     * Unique instance (Singleton Design Pattern)
     */
	private static StatsReport instance;

    /**
     * Private constructor (use instance() instead)
     * @throws java.sql.SQLException
     */
    private StatsReport() throws SQLException{
        data = Data.instance();
		publisher = new JMSPublisher();
    }

	/**
     * Return the unique instance of the class (Singleton Design Pattern)
     * @return StatsReport instance
     * @throws Exception
     */
    public static StatsReport instance() throws SQLException {
        if(instance == null){
          instance = new StatsReport();
        }
        return instance;
    }

    /**
     * The run method in the StatsReport thread will receive a top player's list
     *  and send to clients
     */
    @Override
    public void run(){
        while(true){
			String topPlayers = "Top players so far ("+new Date()+"):\n\n";
			try{
				topPlayers += data.getTopPlayers();
			}
			catch(SQLException sqle){
				System.err.println("Error in receiving top player list: " + sqle.getMessage());
				System.exit(1);
			}

			//Send the message
			publisher.publish(topPlayers);

			try{
				sleep(DELAY);
			}
			catch(InterruptedException ie){
				System.err.println("Error trying to sleep: " + ie.getMessage());
			}
        }
    }


    public static void main(String [] args){
        StatsReport sr = null;
        try{
            sr = new StatsReport();
        }
        catch(SQLException sqle){}
        sr.start();
    }
}