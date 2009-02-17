package rmi;
import java.io.*;
import java.sql.Date;

/**
 * Information for a user
 * @author Emilien Girault
 */
public class AccountInformation implements Serializable
{
	private String userName, pwd, fName, lName, email;/*, creditCard;*/
    private Date dateLastPlayed, dateJoined;


    /**
     * Constructor
     * @param login
     * @param pass
     * @param firstName
     * @param lastName
     * @param mail
     * @param dLastPlayed
     * @param dJoined
     */
	public AccountInformation(String login, String pass, String firstName, String lastName, String mail, Date dLastPlayed, Date dJoined)
	{
		userName = login;
        pwd = pass;
		fName = firstName;
		lName = lastName;
        email = mail;
        dateLastPlayed = dLastPlayed;
        dateJoined = dJoined;
	}

    //
    // GETTERS
    //


    public String getUserName(){
		return userName;
	}

    public String getPassword(){
		return pwd;
	}

    public String getFirstName(){
		return fName;
	}

    public String getLastName(){
		return lName;
	}

    public String getEmail(){
		return email;
	}

    public Date getDateLastPlayed(){
		return dateLastPlayed;
	}

    public Date getDateJoined(){
		return dateJoined;
	}


    //
    // SETTERS
    //


    public void setUserName(String u){
		userName = u;
	}

    public void setPassword(String p){
		pwd = p;
	}

    public void setFirstName(String f){
		fName = f;
	}

    public void setLastName(String l){
		lName = l;
	}

    public void setEmail(String e){
		email = e;
	}

    public void setDateLastPlayed(Date d){
		dateLastPlayed = d;
	}

    public void setDateJoined(Date d){
		dateJoined = d;
	}

	
		
    @Override
	public String toString()
	{
		String temp = "Name: "+ fName + " " + lName + "; Login: " + userName + "; email: " + email +
                "; Created:" + dateJoined + "; Last game: " + dateLastPlayed;
		return temp;
	}
}