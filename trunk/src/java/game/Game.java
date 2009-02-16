package game;

import java.io.Serializable;
/**
 *
 * @author Eric Kisner
 */
public class Game implements Serializable {

    private Hand playerHand, dealerHand;
    private int userID;
    private CheckLogic checkLogic;
    private Deck deck;

    public Game(int uID){
        initGame();
        userID = uID;
    }

    //
    //Setters
    //
    
    public void setCheckLogic(CheckLogic checkLogic) {
        this.checkLogic = checkLogic;
    }

    public void setDealerHand(Hand dealerHand) {
        this.dealerHand = dealerHand;
    }

    public void setPlayerHand(Hand playerHand) {
        this.playerHand = playerHand;
    }

    //
    //Getters
    //

    public CheckLogic getCheckLogic() {
        return checkLogic;
    }

    public Hand getDealerHand() {
        return dealerHand;
    }

    public Hand getPlayerHand() {
        return playerHand;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getUserID() {
        return userID;
    }

    //
    //Update
    //
    public void updateCheckLogic(){
        checkLogic = new CheckLogic(playerHand, dealerHand);
    }

    public void initGame(){
        deck = new Deck();
        playerHand = new Hand(deck);
        dealerHand = new Hand(deck);
        checkLogic = new CheckLogic(playerHand, dealerHand);
    }

    //
    //Game Logic
    //

    /**
     *
     * @return
     */
    public Card hit() {
        Card temp = deck.getNextCard();
        checkLogic.updatePlayer(temp);
        return temp;
    }

    /**
     *
     * @return
     */
    public Card dealerHit() {
        Card temp = deck.getNextCard();
        checkLogic.updateDealer(temp);
        return temp;
    }

    /**
     *
     * @return
     */
    public Hand dealPlayer() {
        playerHand.nextHand();
        return playerHand;
    }

    /**
     *
     * @return
     */
    public Hand dealDealer() {
        dealerHand.nextHand();
        return dealerHand;
    }

    /**
     *
     * @param playerOrDealer
     * @return
     */
    public boolean bust(boolean playerOrDealer){
        boolean bust = false;
        if(playerOrDealer){
            bust = checkLogic.checkBust(true);
        }
        else{
            bust = checkLogic.checkBust(false);
        }
        return bust;
    }

    /**
     *
     * @return
     */
    public boolean dealerStandsAtSixteen(){
        return(checkLogic.getCombinedDealerHand() >= 16);
    }

    /**
     * 
     * @param bet
     * @return
     */
    public String returnWin(double bet){
        String s = "";
        switch(checkLogic.returnTypeOfWin())
        {
            case -1:
                bet *= -1;
                s = "You lose.";
                break;
            case 1:
                s = "You win!";
                break;
            case 2:
                bet = bet * 1.5;
                s = "Winner, winner, chicken dinner!";
                break;
            default:
                bet = 0;
                s = "Push.";
                break;
        }
        s = s + "_" + bet;
        //System.out.println("Just checked win - String s= " + s + " checklogic is as follows: " +
        //        checkLogic.toString());
        return s;
    }
}
