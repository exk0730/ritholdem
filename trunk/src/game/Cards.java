package game;

import java.util.*;
import java.io.*;

/**
*Abstract class which will represent any ArrayList of cards
*/
public abstract class Cards implements Serializable
{
	protected ArrayList<Card> cards;
	protected int selectedIndex;
	
	/**
	*Default Constructor
	*Creates a new array of cards
	*/
	public Cards()
    {
		reset();
	}
	
	public int getSize()
	{
		return cards.size();
	}
	
	/**
	*Gets a card at a specified index
	*@param n The position where the card should be
	*@return Card object
	*/
	public Card getCardAt(int n)
   {
		return cards.get(n);
	}
	
	/**
	*Returns last card
	*@return The last card object in this arrayList
	*/
	public Card getLastCard()
	{
		Card c = null;
		try 
		{
			c = cards.get( cards.size() - 1 );
		} 
		catch (ArrayIndexOutOfBoundsException aioobe) { }
		
		return c;
	}
	
	/**
	*Returns the position of the current Card in this collection
	*@return int The selectedIndex
	*/
	public int getIndex()
   {
		return selectedIndex;
	}
	
	/**
	*Manual way to increment count by one
	*/
	public void increment() 
	{
		selectedIndex++;
	}
	
	public void addCard(Card c)
	{
		cards.add(c);
	}
	
	/**
	*Remove a card at a position, used for discarding
	*@param n The position of the card in this collection
	*/
	public void removeCard(int n) {
		cards.remove(n);
	}
	
	/**
	*Overloaded method to remove a card object from the collection
	*@param c The Card object we wish to remove
	*/
	public synchronized void removeCard(Card c)
	{
		for(int i = 0; i < cards.size(); i++)
		{
			Card card = cards.get(i);
			if (card.equals(c))
			{
				cards.remove(card);
			}
		}
	}

	/**
	*Get the next card
	*@return The next Card object in this collection
	*/
	public Card getNextCard()
	{
		Card c = null;
		try 
		{
			c = cards.get(selectedIndex);
			increment();
		}
		catch (IndexOutOfBoundsException ioobe)
		{
			// maybe create an error card?
			ioobe.printStackTrace();
			System.out.println("error");
		}
		return c;
   }
	
	/**
	*Resets the card collection
	*/
	public void reset() {
		cards = new ArrayList<Card>();
		selectedIndex = 0;
	}
	
	/**
	*Overrides toString method in Object class
	*@return String representation of the cards in this collection
	*/
	public String toString()
	{
		String temp = "";
		for(int i = 0; i < cards.size(); i++)
		{
			temp += cards.get(i).toString() + "\t";
		}
		return temp;
	}
}