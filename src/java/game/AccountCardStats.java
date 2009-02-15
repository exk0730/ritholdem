package game;

import java.io.Serializable;
/**
 *
 * @author Eric Kisner
 */
public class AccountCardStats implements Serializable{

    private int numOfBlackjacks, numOfDoubles, numOfWins, numOfLoss, numOfStands, numOfHits, numOfPushes;

    public AccountCardStats(int nob, int noh, int nos, int nod, int now, int nol, int nop){
        numOfBlackjacks = nob;
        numOfHits = noh;
        numOfStands = nos;
        numOfDoubles = nod;
        numOfWins = now;
        numOfLoss = nol;
        numOfPushes = nop;
    }

    public int getNumOfBlackjacks(){
        return numOfBlackjacks;
    }

    public int getNumOfHits(){
        return numOfHits;
    }

    public int getNumOfStands(){
        return numOfStands;
    }

    public int getNumOfDoubles(){
        return numOfDoubles;
    }

    public int getNumOfWins(){
        return numOfWins;
    }

    public int getNumOfLoss(){
        return numOfLoss;
    }

    public int getNumOfPushes(){
        return numOfPushes;
    }
}
