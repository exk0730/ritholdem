package servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.rmi.Naming;
import java.rmi.RemoteException;
import server.UnknownUserException;
import game.*;

/**
 *
 * @author trance
 */
public class AdminServlet extends HttpServlet {

	protected RMIInterface server;
	protected Exception exception;

	public AdminServlet() throws Exception{
		// initialize the server (RMI)
		try {
            server = (RMIInterface) Naming.lookup(RMIInterface.SERVER_NAME);
        } catch(Exception e) {
            server = null;
			exception = e;
        }
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
	boolean kick(int userID){
		boolean res = false;
		try {
			res = server.kickUser(userID);
		} catch (RemoteException ex) {
			exception = ex;
		}
		return res && exception == null;
	}
	
	/**
	* Ban a user from the server
	* @param userID
	* @return true if no error, false otherwise
	*/
	boolean ban(int userID){
        kick(userID);
        try
        {
            server.deleteAccount(userID);
        }
        catch(RemoteException ex)
        {
            exception = ex;
        }

        catch(UnknownUserException uue){
            exception = uue;
        }
        return exception == null;
	}

	/**
	 * Get the current users logged into the server
	 * @return list of logged user IDs, null if there is an error
	 */
	ArrayList<Integer> getCurrentUsers(){
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
	protected AccountInformation getInfos(int userID) {
		AccountInformation res = null;
		try {
			res = server.getInfos(userID);
		} catch (Exception ex) {
			exception = ex;
		}
		return res;
	}


	/**
	 * Get the server uptime
	 * @return server uptime in minutes
	 */
	int getUptime(){
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
	boolean addMoney(int userID, int amount){
		double res = -1;
		try {
			res = server.updateBank(userID, amount);
		}catch(Exception ex){
			exception = ex;
		}
		return res != -1;
	}


	//
	// TEST methods
	//
	/**
	 * Print the current users
	 * @param out writer
	 */
	void printCurrentUsers(PrintWriter out){
		ArrayList<Integer> users = getCurrentUsers();
		if(users == null){
			out.println("<p><strong>Error getting the list of current logged users:</strong></p>");
			out.println(exception.getMessage());
			return;
		}
		if(users.isEmpty()){
			out.println("<p>No users currently logged in.</p>");
		} else {
			out.println("<p>Current users logged: </p>");
			out.println("<ul>");
			for (Integer userID : users) {
				AccountInformation infos = getInfos(userID);
				out.println("<li>"+infos.getUserName() + 
						" - ID: " + userID+" - Name: "+ infos.getFirstName() + " " + infos.getLastName() +
						" - <a href = \"mailto:"+infos.getEmail()+"\">Send an e-mail</a> "+
						" - <a href = \"?userToKick="+userID+"\">Kick this user</a>" +
						" - <form method = \"get\" action = \"\">" +
						"<input type = \"text\" name = \"amount\" /><input type = \"hidden\" name = \"userToCredit\" value = \""+userID+"\" />" +
						"<input type = \"submit\" value = \"Add money\" /></form>" +
						"</li>");
			}
			out.println("</ul>");
		}
	}
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
		
		if(server == null){
			out.println("<h2>Error: </h2>");
			out.println(exception.getMessage());
			return;
		}

        try {
			
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Blackjack Server Administration Pannel</title>");
			out.println("<style type = \"text/css\">");
			out.println("form {display: inline;}");
			out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            
			out.println("<h1>Blackjack Server Administration Pannel</h1>");
            out.println("<h2>Server Uptime: " + getUptime() + " min</h2>");
            //out.println("<h2>Banning User #1: " + ban(1) + "</h2>");

			/* Handle requests */
			String userToKick = request.getParameter("userToKick");
			if(userToKick != null){
				if(kick(new Integer(userToKick).intValue())){
					out.println("<p>The user has been kicked successfully!</p>");
				} else {
					out.println("<p>Impossible to kick the given user, please check that he/she is connected!</p>");
				}
			}
			String userToCredit = request.getParameter("userToCredit");
			String amount = request.getParameter("amount");
			if(userToCredit != null && amount != null){
				if(addMoney(new Integer(userToCredit).intValue(), new Integer(amount).intValue())){
					out.println("<p>The user has been credited successfully!</p>");
				} else {
					out.println("<p>Impossible to credit the given user!</p>");
				}
			}

			//Print the current users
			printCurrentUsers(out);

			out.println("<p><a href = \"/Blackjack/AdminServlet\">Refresh this page</a></p>");

			out.println("</body>");
            out.println("</html>");

		} catch(Exception e){
			out.println("<h2>An exception was encountered: </h2>");
			out.println(e.getMessage());
        } finally { 
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}