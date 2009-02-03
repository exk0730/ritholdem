package servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.rmi.Naming;
import java.rmi.RemoteException;
import game.*;
import java.rmi.RMISecurityManager;

/**
 *
 * @author trance
 */
public class AdminServlet extends HttpServlet {

	protected RMIInterface server;
	protected Exception exception;

	public AdminServlet() throws Exception{
		/**
		 * TODO initialize the server (RMI)
		 * for now, it crashed due to an exception... to be resolved!
		 */
		//try {

		//System.setSecurityManager(new RMISecurityManager());
            server = (RMIInterface) Naming.lookup(RMIInterface.SERVER_NAME);
        //} catch(Exception e) {
            //server = null;
			//exception = e;
        //}
	}



	void kick(int userID){
		//TODO
	}

	void ban(int userID){
		//TODO
	}

	void getCurrentUsers(){
		//TODO
	}

	String getUptime(){
		//TODO
		return "";
	}

	void addMoney(int userID, int amount){
		//TODO
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
			out.println("<h1>plop!</h1>");
			if(server == null){
				out.println("<h2>ooops!</h2>");
				out.println(exception.getMessage());
			}
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
