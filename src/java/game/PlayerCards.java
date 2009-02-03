package game;
import java.io.Serializable;

/**
 * A player's hand
 * @author Eric Kisner
 */
public class PlayerCards extends Cards implements Serializable
{
    private static final long serialVersionUID = 797161030686405479L;
	private Deck deck;
    /**
     * Constructor
     * @param deck
     */
    public PlayerCards(Deck deck)
	{
		this.deck = deck;
	}
	
    /**
     * Returns the next hand for this player
     */
    public void nextHand() throws IndexOutOfBoundsException
	{
		this.reset();
		addCard(deck.getNextCard());
		addCard(deck.getNextCard());
	}
}