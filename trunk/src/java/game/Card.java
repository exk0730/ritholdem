package game;
import javax.swing.*;
import java.io.*;

/**
 * Represents a card object
 * @author Eric Kisner
 */
public class Card implements Serializable
{
	private int suit , number;
	private final int SPADES = 4;
	private final int HEARTS = 3;
	private final int DIAMONDS = 2;
	private final int CLUBS = 1;
	private final String SPADES_STRING = "spade";
	private final String HEARTS_STRING = "heart";
	private final String DIAMONDS_STRING = "diamond";
	private final String CLUBS_STRING = "club";
	private final String JPG = "jpg";
	
    /**
     * Constructor
     * @param suit
     * @param number
     */
    public Card(int suit, int number)
	{
		this.suit = suit;
		this.number = number;
	}
	
    /**
     * Gets this card's suit
     * @return
     */
    public int getSuit()
	{
		return suit;
	}
	
    /**
     * Gets this card's number or the point-value of this card (if above 10)
     * @param labelRequest
     * @return
     */
    public int getNumber(boolean labelRequest)
	{
		if( (number > 10) && (labelRequest == false) )
		{
			return 10;
		}
		else return number;
	}
	
    /**
     * Returns this card's string representation
     * @return
     */
    public String getStringNumber()
	{
		switch (getNumber(true))
		{
			case 1: return "ace";
			case 2: return "two";
			case 3: return "three";
			case 4: return "four";
			case 5: return "five";
			case 6: return "six";
			case 7: return "seven";
			case 8: return "eight";
			case 9: return "nine";
			case 10: return "ten";
			case 11: return "jack";
			case 12: return "queen";
			case 13: return "king";
			default: return null;
		}
	}
	
    /**
     * Returns this card's picture
     * @return
     */
    public JLabel getCardImage()
	{
		
		String location = "game/cards/";
		switch (getSuit())
		{
			case CLUBS:
				location += CLUBS_STRING + "/" + CLUBS_STRING;
				break;
			case DIAMONDS:
				location += DIAMONDS_STRING + "/" + DIAMONDS_STRING;
				break;
			case HEARTS:
				location += HEARTS_STRING + "/" + HEARTS_STRING;
				break;
			case SPADES:
				location += SPADES_STRING + "/" + SPADES_STRING;
				break;
		}
		
		location += "_" + getStringNumber() + "." + JPG;
		
		//if the card is 0,0 we want the card_back image
		if (getSuit() == 0 && getNumber(true) == 0)
		{
			location = "game/cards/card_back." + JPG;
		}
		return new JLabel(new ImageIcon(getClass().getClassLoader().getResource(location)));
    }

    @Override
	public String toString()
	{
		String temp = suit + "_" + number;
		return temp;
	}
}