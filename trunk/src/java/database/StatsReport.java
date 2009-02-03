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
            try{
                s = data.getTopPlayers();
            }
            catch(SQLException sqle){
                System.err.println("Error in receiving top player list: " + sqle.getMessage());
                System.exit(1);
            }

            String[] result = s.split("_");
            for(int i = 0; i < result.length; i++){
                System.out.println("newline:" + result[i]);
            }
            try{
                sleep(500);
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
