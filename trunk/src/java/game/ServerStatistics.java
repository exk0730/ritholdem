package game;

import java.io.Serializable;
import java.util.Date;
/**
 * Information for the Server
 * @author Tyler Schindel
 */

public class ServerStatistics implements Serializable
{
    private int numNewUsers, numUsersPlayed, dealerWins, userWins, totalBlackjacks;
    private double totalAmtBet, dealerEarnings;
    private Date lastServerReboot;

    public ServerStatistics(int nnu, int nup, double tab, int dw,
                            int uw, double de , int tb, Date lsr)
    {
        numNewUsers = nnu;
        numUsersPlayed = nup;
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

    public int getNumUsersPlayed(){
        return numUsersPlayed;
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

    public Date getLastServerReboot(){
        return lastServerReboot;
    }
}
