/**
 * Server Administration Module
 * Used by the JSP to perform actions on the server
 * @author Emilien Girault
 * @date 2/15/09
 */

package servlet;
import game.AccountCardStats;
import game.AccountInformation;
import game.RMIInterface;
import java.rmi.*;
import java.util.ArrayList;
import server.*;


public class ServerAdminWebController {

	protected RMIInterface server;
	protected Exception exception;

	/**
	 * Constructor
	 * @throws java.lang.Exception
	 */
	public ServerAdminWebController(){
		// initialize the server (RMI)
		try {
            server = (RMIInterface) Naming.lookup(RMIInterface.SERVER_NAME);
        } catch(Exception e) {
            server = null;
			exception = e;
        }
	}

	/**
	 * Return true if connected to the server, false otherwise
	 * @return true if connected to the server, false otherwise
	 */
	public boolean isConnected(){
		return server != null;
	}

	/**
	 * Get the last encountered exception
	 * @return the last encountered exception
	 */
	public Exception getException(){
		return exception;
	}


	/**
	 * Kick a user from the server
	 * @param userID
	 * @return true if no error, false otherwise
	 */
	public boolean kick(int userID){
		try {
			server.kickUser(userID);
		} catch (Exception e) {
			exception = e;
		}
		return exception == null;
	}

	/**
	* Ban a user from the server
	* @param userID
	* @return true if no error, false otherwise
	*/
	public boolean ban(int userID){
        kick(userID);
        try {
            server.deleteAccount(userID);
        }
        catch(RemoteException ex) {
            exception = ex;
        }
        catch(UnknownUserException uue) {
            exception = uue;
        }
        return exception == null;
	}

	/**
	 * Get the current users logged into the server
	 * @return list of logged user IDs, null if there is an error
	 */
	public ArrayList<Integer> getCurrentUsers(){
		ArrayList<Integer> res = null;
		try {
			res = server.getUsers();
		} catch (RemoteException ex) {
			exception = ex;
		}
		return res;
	}

	/**
	 * Get information from a user ID
	 * @param userID
	 * @return
	 */
	public AccountInformation getInfos(int userID) {
		AccountInformation res = null;
		try {
			res = server.getInfos(userID);
		} catch (Exception ex) {
			exception = ex;
		}
		return res;
	}

	/**
	 * Get card stats for a user ID
	 * @param userID
	 * @return
	 */
	public AccountCardStats getCardStats(int userID) {
		AccountCardStats res = null;
		try {
			res = server.getCardStats(userID);
		} catch (Exception ex) {
			exception = ex;
		}
		return res;
	}


	/**
	 * Get the server uptime
	 * @return server uptime in minutes
	 */
	public int getUptime(){
		int uptime = -1;
		try{
			uptime = (int) ((server.getCurrentTime() - server.getStartTime()) / (60 * 1000));
		} catch (RemoteException ex){
			exception = ex;
		}
		return uptime;
	}

	/**
	 * Add money to an account
	 * @param userID
	 * @param amount
	 */
	public boolean addMoney(int userID, int amount){
		double res = -1;
		try {
			res = server.updateBank(userID, amount);
		}catch(Exception ex){
			exception = ex;
		}
		return res != -1;
	}

	/**
	 * Get the current bank of a user
	 * @param userID
	 * @param amount
	 */
	public double getBank(int userID){
		double res = -1;
		try {
			res = server.getBank(userID);
		}catch(Exception ex){
			exception = ex;
		}
		return res;
	}
}
