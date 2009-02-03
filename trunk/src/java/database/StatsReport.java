package database;

import java.sql.*;
/**
 * StatsReport will send a top player list using JMS
 * @author Eric Kisner
 */
public class StatsReport extends Thread {

    private Data data;

    public StatsReport() throws SQLException{
        data = Data.instance();
    }

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

            for(int i = 0; i < result.length; i++){
                System.out.println("Position " + (i+1) + ": \t" + result[i]);
            }
            try{
                sleep(5000);
            }
            catch(InterruptedException ie){}
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
