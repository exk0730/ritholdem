import java.io.*;

/**
*Mock handling of user information
*Database classes should do these methods
*/
public class AccountInformation implements Serializable
{
	private String fName, lName, creditCard, loginID, password;
	private double totalCash, totalTimePlaying, totalTimeConnected, longestWinningStreak,
						mostMoneyWon, totalHandsPlayed, amountOfWins, amountOfLosses;
	
	public AccountInformation(String firstName, String lastName, String cCard,
									String log, String passwd, double cash)
	{
		fName = firstName;
		lName = lastName;
		creditCard = cCard;
		loginID = log;
		password = passwd;
		totalCash = cash;
		totalTimePlaying = 0;
		totalTimeConnected = 0;
		longestWinningStreak = 0;
		mostMoneyWon = 0;
		totalHandsPlayed = 0;
		amountOfWins = 0;
		amountOfLosses = 0;
	}
	
	public String getLogin()
	{
		return loginID;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public double getTotalCash()
	{
		return totalCash;
	}
	
	/**
	*Decreases or increases a player's amount of money
	*/
	public void setCash(double dbl, boolean win, boolean blackJack)
	{
		if(win)
		{
			totalCash += dbl;
		}
		else if(blackJack)
		{
			totalCash += dbl * 1.5;
		}
		else
		{
			totalCash -= dbl;
		}
	}
	
	public String toString()
	{
		String temp = lName + ", " + fName + " with CreditCardNumber: " + creditCard + " has " + totalCash + " total money.";
		return temp;
	}
}