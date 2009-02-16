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
    private static Deck instance;

    public static Deck instance() throws Exception {
        if(instance == null){
            instance = new Deck();
        }
        return instance;
    }
	/**
	*Default constructor
	*/
   private Deck()
	{
		createDeck();
		//shuffle();
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

   @Override
   /**
    * Overrides getNextCard in Cards class
    * @return
    */
   public Card getNextCard(){
       Card c = null;
       try{
           c = getCardAt(getIndex());
       }
       catch(IndexOutOfBoundsException ioobe){
           instance = new Deck();
           c = getCardAt(getIndex());
       }
       increment();
       return c;
   }
	
	/**
	*Shuffle the deck
	*/
	public void shuffle()
   {
		Collections.shuffle(cards);
   }
}