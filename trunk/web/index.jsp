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
		out.println("<table>");
		//Print Header
		out.println("<tr>");
		out.println("<th>ID</th><th>Login</th><th>Name</th><th>Bank</th>");
		out.println("<th>Hits</th><th>Stands</th><th>Doubles</th><th>Wins</th><th>Loss</th><th>Blackjacks</th>");
		out.println("<th>Kick</th><th>Add money</th>");
		out.println("</tr>");
		for (Integer userID : users) {
			AccountInformation infos = server.getInfos(userID);
			AccountCardStats cardStats = server.getCardStats(userID);
			out.println("<tr>");
			out.println("<td>" + userID + "</td>");
			out.println("<td>" + infos.getUserName() + "</td>");
			out.println("<td>" + infos.getFirstName() + " " + infos.getLastName() + "</td>");
			out.println("<td>$" + server.getBank(userID) + "</td>");
			
			out.println("<td>" + cardStats.getNumOfHits() + "</td>");
			out.println("<td>" + cardStats.getNumOfStands() + "</td>");
			out.println("<td>" + cardStats.getNumOfDoubles() + "</td>");
			out.println("<td>" + cardStats.getNumOfWins() + "</td>");
			out.println("<td>" + cardStats.getNumOfLoss() + "</td>");
			out.println("<td>" + cardStats.getNumOfBlackjacks() + "</td>");
			
			out.println("<td><a href = \"?userToKick="+userID+"\">Kick</a></td>");
			out.println("<td><form method = \"get\" action = \"\">" +
					"<input type = \"text\" name = \"amount\" /><input type = \"hidden\" name = \"userToCredit\" value = \""+userID+"\" />" +
					"<input type = \"submit\" value = \"OK\" /></form></td>");
			out.println("</tr>");
		}
		out.println("</table>");
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

		<% printCurrentUsers(new PrintWriter(out)); %>
		

		<% } else { /* if !server.isConnected() */ %>
			<p>Unable to connect to server.</p>
		<% } %>
    </body>
</html>
