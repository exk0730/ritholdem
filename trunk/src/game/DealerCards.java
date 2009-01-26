package game;
import game.Cards;
import game.Deck;
import java.io.Serializable;

public class DealerCards extends Cards implements Serializable
{	
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