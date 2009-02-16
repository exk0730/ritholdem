package game;

/**
 * Checks game logic
 * @author Eric Kisner
 */
public class CheckLogic
{
	private Hand playerHand;
    private Hand dealerHand;
	
    /**
     * Constructor
     * @param player
     * @param dealer
     */
    public CheckLogic(Hand player, Hand dealer)
	{
		playerHand = player;
		dealerHand = dealer;
	}

    /**
     * Add a card to player's hand
     * @param card
     */
    public void updatePlayer(Card card){
        playerHand.addCard(card);
    }

    /**
     * Add a card to dealer's hand
     * @param card
     */
    public void updateDealer(Card card){
        dealerHand.addCard(card);
    }

    /**
     * See if dealer or player busted
     * @param player
     * @return
     */
    public boolean checkBust(boolean player){
        if(player){
            return (getCombinedPlayerHand() > 21);
        }
        else{
            return (getCombinedDealerHand() > 21);
        }
    }
	
    /**
     * See if player pushes
     * @return
     */
    public boolean checkPush()
	{
        boolean push = false;
        if(getCombinedPlayerHand() == getCombinedDealerHand()){
            if(checkBlackJack() && (getCombinedDealerHand() == 21 && dealerHand.getSize() == 2)){
                push = true;
            }
            else if(!checkBlackJack() && (getCombinedDealerHand() == 21 && dealerHand.getSize() == 2)){
                push = false;
            }
            else if(checkBlackJack() && (getCombinedDealerHand() == 21 && dealerHand.getSize() != 2)){
                push = false;
            }
            else{
                push = true;
            }
        }
		return push;
	}
	
    /**
     * See if player gets a blackjack
     * TODO check dealer blackjack
     * @return
     */
    public boolean checkBlackJack()
	{
		return(getCombinedPlayerHand() == 21 && playerHand.getSize() == 2);
	}
	
    /**
     * See if player loses
     * @return
     */
    public boolean checkLoss()
	{
		return(getCombinedPlayerHand() < getCombinedDealerHand() && !checkBust(false));
	}
	
    /**
     * See if player wins
     * @return
     */
    public boolean checkWin()
	{
		return( (getCombinedPlayerHand() > getCombinedDealerHand() && (!checkBlackJack())) || (checkBust(false)) );
	}
	
    /**
     * Gets total of player hand
     * @return
     */
    public int getCombinedPlayerHand()
	{
		boolean aceExists = false;
		int temp = 0;
        int count = 0;
		for(int i = 0; i < playerHand.getSize(); i++)
		{
			if(playerHand.getCardAt(i).getNumber(false) == 1)
			{
				aceExists = true;
                count++;
                if(count >= 2){
                    temp++;
                }
			}
			else
				temp += playerHand.getCardAt(i).getNumber(false);
		}
		if(aceExists)
		{
			temp += checkAce(temp);
		}
		return temp;
	}
	
    /**
     * Gets total of dealer hand
     * @return
     */
    public int getCombinedDealerHand()
	{
		boolean aceExists = false;
		int temp = 0;
        int count = 0;
		for(int i = 0; i < dealerHand.getSize(); i++)
		{
			if(dealerHand.getCardAt(i).getNumber(false) == 1)
			{
				aceExists = true;
                count++;
                if(count >= 2){
                    temp++;
                }
			}
			else
				temp += dealerHand.getCardAt(i).getNumber(false);
		}
		if(aceExists)
		{
			temp += checkAce(temp);
		}
		return temp;
	}
	
    /**
     * Sees if ace is 11 or 1 in a hand
     * @param num
     * @return
     */
    public int checkAce(int num)
	{
		if(num + 11 <= 21)
		{
			return 11;
		}
		else return 1;
	}
	
    /**
     * Returns number-based win
     * @return
     */
    public int returnTypeOfWin()
	{
		int temp = 0;
		
		if(checkWin() && !checkBlackJack())
		{
			temp = 1;
		}
		if(checkBlackJack())
		{
			temp = 2;
		}
		if(checkLoss())
		{
			temp = -1;
		}
        if(checkPush()){
            temp = 0;
        }
		return temp;
	}

    /**
    @Override
    public String toString(){
        String s = "combinedHand: " + getCombinedPlayerHand() + " dealerHand: " + getCombinedDealerHand();

		s += "\nblackjack: " + checkBlackJack() + "\npush: " + checkPush() +
								 "\nwin: " + checkWin() + "\nloss: " + checkLoss();

		s+= "\n\nWin Type: " + returnTypeOfWin() + "\nPlayerHand:::::::::";

        for(int i = 0; i < playerHand.getSize(); i++){
            s+= "\t" + playerHand.getCardAt(i);
        }
        s+= "\n\nDealerHand:::::::::";
        for(int i = 0; i < dealerHand.getSize(); i++){
            s+= "\t" + dealerHand.getCardAt(i);
        }
        s+= "\n\n\n\n----------------DECK------------------";
        try{
            Deck deck = Deck.instance();
        for(int i = 0; i < deck.getSize(); i++){
            s+= "\n" + deck.getCardAt(i);
        }
            s+= "\nSelectedIndex: " + deck.getIndex() + "\tCard at selected Index: " + deck.getCardAt(deck.getIndex()) +
                    "\n";
        }catch(Exception e){}
        return s;
    }*/
}