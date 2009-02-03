package server;

import java.sql.*;
import database.*;
/**
 * StatsReport will send a top player list using JMS
 * @author Eric Kisner
 */
public class StatsReport extends Thread {

    private Data data;

    /**
     * StatsReport constructor
     * @throws java.sql.SQLException
     */
    public StatsReport() throws SQLException{
        data = Data.instance();
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

            //TODO add JMS portion here
            for(int i = 0; i < result.length; i++){
                System.out.println("Position " + (i+1) + ": \t" + result[i]);
            }
            try{
                sleep(60000);
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