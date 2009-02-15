package game;
import java.io.Serializable;

/**
 * A player's hand
 * @author Eric Kisner
 */
public class Hand extends Cards implements Serializable
{
	private Deck deck;
    /**
     * Constructor
     * @param deck
     */
    public Hand()
	{
        try{
            deck = Deck.instance();
        }
        catch(Exception e){
            
        }
	}

    /**
     * Returns the next hand for this player
     */
    public void nextHand()
	{
		this.reset();
		addCard(deck.getNextCard());
		addCard(deck.getNextCard());
	}
}