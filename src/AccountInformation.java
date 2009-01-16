/**
 * Account information - Represent the informations of a user account
 * @author Eric Kisner, Emilien Girault
 * @date 1/12/09
 */

import java.io.*;
import java.util.Date;

public class AccountInformation implements Serializable
{
	private String userName, fName, lName, email;/*, creditCard;*/
    private Date dateLastPlayed, dateJoined;

    /*
	private double totalCash, totalTimePlaying, totalTimeConnected, longestWinningStreak,
						mostMoneyWon, totalHandsPlayed, amountOfWins, amountOfLosses;
     * */
	
	public AccountInformation(String login, String firstName, String lastName, String mail, Date dLastPlayed, Date dJoined)
	{
		userName = login;
		fName = firstName;
		lName = lastName;
        email = mail;
        dateLastPlayed = dLastPlayed;
        dateJoined = dJoined;
        /*
		creditCard = cCard;
		totalCash = cash;
		totalTimePlaying = 0;
		totalTimeConnected = 0;
		longestWinningStreak = 0;
		mostMoneyWon = 0;
		totalHandsPlayed = 0;
		amountOfWins = 0;
		amountOfLosses = 0;
         * */
	}
	
	public String getUserName()
	{
		return userName;
	}
	
	/*
	public double getTotalCash()
	{
		return totalCash;
	}
     * */
	
		
    @Override
	public String toString()
	{
		String temp = "Name: "+ fName + " " + lName + "; Login: " + userName + "; email: " + email +
                "; Created:" + dateJoined + "; Last game: " + dateLastPlayed;
		return temp;
	}
}
