package game;
import java.io.Serializable;

public class DealerCards extends Cards implements Serializable
{
   	private static final long serialVersionUID = -4706068125136271514L;
	private Deck deck;
	public DealerCards(Deck deck)
	{
		this.deck = deck;
	}
	
	public void nextHand()
	{
		this.reset();
		addCard(deck.getNextCard());
		addCard(deck.getNextCard());
	}
}