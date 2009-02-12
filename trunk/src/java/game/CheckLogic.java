package game;

/**
 * Checks game logic
 * @author Eric Kisner
 */
public class CheckLogic
{
	private PlayerCards playerHand;
    private DealerCards dealerHand;
	
    /**
     * Constructor
     * @param player
     * @param dealer
     */
    public CheckLogic(PlayerCards player, DealerCards dealer)
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
		return(getCombinedPlayerHand() < getCombinedDealerHand());
	}
	
    /**
     * See if player wins
     * @return
     */
    public boolean checkWin()
	{
		return( (getCombinedPlayerHand() > getCombinedDealerHand() && (!checkBlackJack()) ) || (checkBust(false)));
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
	
    public static void main(String [] args)
	{
		Card card1 = new Card(3,10);
		Card card2 = new Card(1,1);
		Card card3 = new Card(2,9);
		Card card4 = new Card(2,1);
        Card card5 = new Card(4,1);
        Card card6 = new Card(1,8);
		Deck deck = new Deck();

        PlayerCards player = new PlayerCards(deck);
        DealerCards dealer = new DealerCards(deck);
        CheckLogic cl = new CheckLogic(player,dealer);
        cl.updatePlayer(card1);//3,1
        cl.updatePlayer(card2);//1,1
        cl.updateDealer(card3);/////2,9
        cl.updateDealer(card4);/////2,1
        cl.updateDealer(card5);//4,1
        //cl.updatePlayer(card6);//1,8

        System.out.println("------PLAYER HAND-------");
		for(int i = 0; i < player.getSize(); i++)
		{
			System.out.println(player.getCardAt(i).toString());
		}
        System.out.println("\n------DEALER HAND-----");
		for(int i = 0; i < dealer.getSize(); i++)
		{
			System.out.println(dealer.getCardAt(i).toString());
		}

        System.out.println("combinedHand: " + cl.getCombinedPlayerHand() + " dealerHand: " + cl.getCombinedDealerHand());
				
		System.out.println("blackjack: " + cl.checkBlackJack() + "\npush: " + cl.checkPush() +
								 "\nwin: " + cl.checkWin() + "\nloss: " + cl.checkLoss());
		
		System.out.println("\n\nWin Type: " + cl.returnTypeOfWin());
	}
}