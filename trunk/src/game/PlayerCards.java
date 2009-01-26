package game;
import java.io.Serializable;
import game.Cards;
import game.Deck;

public class PlayerCards extends Cards implements Serializable
{
	private Deck deck;
	public PlayerCards(Deck deck)
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