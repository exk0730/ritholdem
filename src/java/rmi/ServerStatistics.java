/**
 * Information for the Server
 * @author Tyler Schindel
 */

package rmi;

import java.io.Serializable;
import java.sql.Timestamp;


public class ServerStatistics implements Serializable
{
    private int numNewUsers, dealerWins, userWins, totalBlackjacks;
    private double totalAmtBet, dealerEarnings;
    private Timestamp lastServerReboot;

    /**
     *
     * @param nnu
     * @param tab
     * @param dw
     * @param uw
     * @param de
     * @param tb
     * @param lsr
     */
    public ServerStatistics(int nnu, double tab, int dw,
                            int uw, double de , int tb, java.sql.Timestamp lsr)
    {
        numNewUsers = nnu;
        totalAmtBet = tab;
        dealerWins = dw;
        userWins = uw;
        dealerEarnings = de;
        totalBlackjacks = tb;
        lastServerReboot = lsr;
    }

    public int getNumNewUsers(){
        return numNewUsers;
    }

    public double getTotalAmtBet(){
        return totalAmtBet;
    }

    public int getDealerWins(){
        return dealerWins;
    }

    public int getUserWins(){
        return userWins;
    }

    public double getDealerEarnings(){
        return dealerEarnings;
    }

    public int getTotalBlackjacks(){
        return totalBlackjacks;
    }

    public Timestamp getLastServerReboot(){
        return lastServerReboot;
    }
}
