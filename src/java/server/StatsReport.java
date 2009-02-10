package server;

import java.sql.*;
import database.*;
import jms.JMSPublisher;
/**
 * StatsReport will send a top player list using JMS
 * @author Eric Kisner
 */
public class StatsReport extends Thread {

	/**
	 * Delay for sending messages in miliseconds
	 */
	protected static int DELAY = 5 * 60 * 1000;

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
            String s = "";
            String[] result = null;
            try{
                s = data.getTopPlayers();
                result = s.split("_");
            }
            catch(SQLException sqle){
                System.err.println("Error in receiving top player list: " + sqle.getMessage());
                System.exit(1);
            }

            //Send the messages
            for(int i = 0; i < result.length; i++){
				//Debug
                System.out.println("[DEBUG] Position " + (i+1) + ": \t" + result[i]);
				//Send the message
				publisher.publish("Position " + (i+1) + ": \t" + result[i]);
            }
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