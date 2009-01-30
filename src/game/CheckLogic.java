package game;

public class CheckLogic
{
	private PlayerCards playerHand;
    private DealerCards dealerHand;
	
	public CheckLogic(PlayerCards player, DealerCards dealer)
	{
		playerHand = player;
		dealerHand = dealer;
	}

    public void updatePlayer(Card card){
        playerHand.addCard(card);
    }

    public void updateDealer(Card card){
        dealerHand.addCard(card);
    }
	
	public boolean checkPush()
	{
		return(getCombinedPlayerHand() == getCombinedDealerHand());
	}
	
	public boolean checkBlackJack()
	{
		return(getCombinedPlayerHand() == 21 && playerHand.getSize() == 2);
	}
	
	public boolean checkLoss()
	{
		return(getCombinedPlayerHand() < getCombinedDealerHand());
	}
	
	public boolean checkWin()
	{
		return(getCombinedPlayerHand() > getCombinedDealerHand() && (!checkBlackJack()));
	}
	
	public int getCombinedPlayerHand()
	{
		boolean aceExists = false;
		int temp = 0;
		for(int i = 0; i < playerHand.getSize(); i++)
		{
			if(playerHand.getCardAt(i).getNumber(false) == 1)
			{
				aceExists = true;
				temp += 0;
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
	
	public int getCombinedDealerHand()
	{
		boolean aceExists = false;
		int temp = 0;
		for(int i = 0; i < dealerHand.getSize(); i++)
		{
			if(dealerHand.getCardAt(i).getNumber(false) == 1)
			{
				aceExists = true;
				temp += 0;
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
	
	public int checkAce(int num)
	{
		if(num + 11 <= 21)
		{
			return 11;
		}
		else return 1;
	}
	
	public int returnTypeOfWin()
	{
		int temp = 0;
		
		if(checkWin())
		{
			temp = 1;
		}
		else if(checkBlackJack())
		{
			temp = 2;
		}
		else if(checkLoss())
		{
			temp = -1;
		}
		return temp;
	}
}
	
/**	public static void main(String [] args)
	{
		/**Card card1 = new Card(1,9);
		Card card2 = new Card(1,1);
		Card card3 = new Card(2,10);
		Card card4 = new Card(2,1);
		
		ArrayList<Card> player = new ArrayList<Card>();
		//player.add(card1); player.add(card2);
		ArrayList<Card> dealer = new ArrayList<Card>();
		//dealer.add(card3); dealer.add(card4);
		CheckLogic cl = new CheckLogic(player,dealer);
		
		Deck deck = new Deck();
		player.add(deck.getNextCard());
		player.add(deck.getNextCard());
		dealer.add(deck.getNextCard());
		dealer.add(deck.getNextCard());
		
		for(int i = 0; i < player.size(); i++)
		{
			System.out.println(player.get(i).toString());
		}
		for(int i = 0; i < dealer.size(); i++)
		{
			System.out.println(dealer.get(i).toString());
		}
				
		System.out.println("blackjack: " + cl.checkBlackJack() + "\npush: " + cl.checkPush() +
								 "\nwin: " + cl.checkWin() + "\nloss: " + cl.checkLoss());
		
		System.out.println("\n\nWin Type: " + cl.returnTypeOfWin());
	}
}*/