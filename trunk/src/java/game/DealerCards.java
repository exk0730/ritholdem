package game;
import java.io.Serializable;

/**
 * Dealer's hand
 * @author Eric Kisner
 */
public class DealerCards extends Cards implements Serializable
{
   	private static final long serialVersionUID = -4706068125136271514L;
	private Deck deck;
    /**
     * Constructor
     * @param deck
     */
    public DealerCards(Deck deck)
	{
		this.deck = deck;
	}
	
    /**
     * Gets the next hand for the dealer
     */
    public void nextHand() throws IndexOutOfBoundsException
	{
		this.reset();
		addCard(deck.getNextCard());
		addCard(deck.getNextCard());
	}
}