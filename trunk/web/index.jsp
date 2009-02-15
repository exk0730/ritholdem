<%@page contentType="text/html" pageEncoding="UTF-8"
import="servlet.*, game.*, java.util.*, java.io.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%!
//Connects to the server
protected ServerAdminWebController server = new ServerAdminWebController();

/**
 * Print the current users logged in
 */
protected void printCurrentUsers(PrintWriter out){
	ArrayList<Integer> users = server.getCurrentUsers();
	if(users == null){
		out.println("<p><strong>Error getting the list of current logged users:</strong></p>");
		out.println(server.getException().getMessage());
		return;
	}
	if(users.isEmpty()){
		out.println("<p>No users currently logged in.</p>");
	} else {
		out.println("<p>Current users logged: </p>");
		out.println("<ul>");
		for (Integer userID : users) {
			AccountInformation infos = server.getInfos(userID);
			out.println("<li>"+ infos.getUserName() +
					" - ID: " + userID+" - Name: "+ infos.getFirstName() + " " + infos.getLastName() +
					" - Bank: $" + server.getBank(userID) +
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


%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blackjack Server Administration Panel</title>
		<link type="text/css" rel="stylesheet" href ="style.css" />
    </head>
    <body>
        <h1>Blackjack Server Administration Panel</h1>

		<% if(server.isConnected()){ %>

		<p>Users : <%= server.getCurrentUsers() %></p>
		

		<% } else { /* if !server.isConnected() */ %>
			<p>Unable to connect to server.</p>
			<p><%= server.getException().getMessage() %></p>
		<% } %>
    </body>
</html>
