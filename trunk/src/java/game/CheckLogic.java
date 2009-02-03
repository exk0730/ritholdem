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

    public boolean checkBust(boolean player){
        if(player){
            return (getCombinedPlayerHand() > 21);
        }
        else{
            return (getCombinedDealerHand() > 21);
        }
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
		return( (getCombinedPlayerHand() > getCombinedDealerHand() && (!checkBlackJack()) ) || (checkBust(false)));
	}
	
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
	
	public static void main(String [] args)
	{
		Card card1 = new Card(3,1);
		Card card2 = new Card(1,1);
		Card card3 = new Card(2,10);
		Card card4 = new Card(2,1);
        Card card5 = new Card(4,1);
        Card card6 = new Card(1,8);
		Deck deck = new Deck();

        PlayerCards player = new PlayerCards(deck);
        DealerCards dealer = new DealerCards(deck);
        CheckLogic cl = new CheckLogic(player,dealer);
        cl.updatePlayer(card1);
        cl.updatePlayer(card2);
        cl.updateDealer(card3);
        cl.updateDealer(card4);
        cl.updatePlayer(card5);
        cl.updatePlayer(card6);

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

        System.out.println("combinedHand: " + cl.getCombinedPlayerHand());
				
		System.out.println("blackjack: " + cl.checkBlackJack() + "\npush: " + cl.checkPush() +
								 "\nwin: " + cl.checkWin() + "\nloss: " + cl.checkLoss());
		
		System.out.println("\n\nWin Type: " + cl.returnTypeOfWin());
	}
}