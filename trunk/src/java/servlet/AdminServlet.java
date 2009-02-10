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
		try {
			server.removeUser(userID);
		} catch (RemoteException ex) {
			exception = ex;
		}
		return exception == null;
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
	 * Get the server uptime
	 * @return server uptime in minutes
	 */
	String getUptime() throws RemoteException{
		//TODO
		return (server.getCurrentTime() - server.getStartTime())/(60*1000) + " minutes.";
	}

	/**
	 * Add money to an account
	 * @param userID
	 * @param amount
	 */
	void addMoney(int userID, int amount){
		//TODO
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
		out.println("Current users logged: " + users + "<br />");
		if(users == null){
			out.println("<h2>ooops!</h2>");
			out.println(exception.getMessage());
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
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AdminServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
            */
			out.println("<h1>Blackjack Server Administration Pannel</h1>");
            out.println("<h2>Server Uptime: " + getUptime() + "</h2>");
            out.println("<h2>Banning User #1: " + ban(1) + "</h2>");
			if(server == null){
				out.println("<h2>ooops!</h2>");
				out.println(exception.getMessage());
			}

			printCurrentUsers(out);

			//out.println("Kicking...<br />");
			//kick(5);

			//printCurrentUsers(out);

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
