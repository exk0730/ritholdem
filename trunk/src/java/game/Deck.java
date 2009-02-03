package game;
import java.io.Serializable;
import java.util.*;

/**
*The deck class will create a standard 52-Card deck
*A collection of Cards
*/
public class Deck extends Cards implements Serializable
{
	private final int MAX_SUIT = 4;
	private final int MAX_CARD = 13;
   
	/**
	*Default constructor
	*/
   public Deck() 
	{
		createDeck();
		shuffle();
   }
   
	/**
	*Creates a 52 card deck
	*/
   public void createDeck()
   {
      for(int i = 0; i < MAX_SUIT; i++)
      {
         for(int j = 0; j < MAX_CARD; j++)
         {
            addCard(new Card(i+1, j+1));
         }
      }
   }
	
	/**
	*Shuffle the deck
	*/
	public void shuffle()
   {
		Collections.shuffle(cards);
   }
}