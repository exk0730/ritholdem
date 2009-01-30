package game;
import java.io.Serializable;

public class PlayerCards extends Cards implements Serializable
{
    private static final long serialVersionUID = 797161030686405479L;
	private Deck deck;
	public PlayerCards(Deck deck)
	{
		this.deck = deck;
	}
	
	public void nextHand()
	{
        System.out.println("getting nextHand");
		this.reset();
		addCard(deck.getNextCard());
		addCard(deck.getNextCard());
	}
}