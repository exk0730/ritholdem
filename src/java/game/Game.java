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

    /**
     *
     * @param uID
     */
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
    /**
     * Updates checklogix to reflect new hands
     */
    public void updateCheckLogic(){
        checkLogic = new CheckLogic(playerHand, dealerHand);
    }

    /**
     *Initializes a new game
     */
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
     * Game method to return the next card from the deck for the player
     * @return
     */
    public Card hit() {
        Card temp = deck.getNextCard();
        checkLogic.updatePlayer(temp);
        return temp;
    }

    /**
     * Game method to return the next card from the deck for the dealer
     * @return
     */
    public Card dealerHit() {
        Card temp = deck.getNextCard();
        checkLogic.updateDealer(temp);
        return temp;
    }

    /**
     * Deals a new hand for the dealer
     * @return
     */
    public Hand dealPlayer() {
        playerHand.nextHand();
        return playerHand;
    }

    /**
     * Deals a new hand for the player
     * @return
     */
    public Hand dealDealer() {
        dealerHand.nextHand();
        return dealerHand;
    }

    /**
     * Checks if player or dealer bust
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
     * Returns true if dealer has a hand greater than or equal to 16
     * @return
     */
    public boolean dealerStandsAtSixteen(){
        return(checkLogic.getCombinedDealerHand() >= 16);
    }

    /**
     * Returns the type of win for player
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
