<%@page contentType="text/html" pageEncoding="UTF-8"
import="servlet.*, server.*, game.*, java.util.*, java.io.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%!
//Connects to the server
protected ServerAdminWebController server;

protected PrintWriter o;

/**
 * Initialize
 */
void initialize(JspWriter out){
	o = new PrintWriter(out);
	server = new ServerAdminWebController();
}

/**
 * Print the current users logged in
 */
protected void printCurrentUsers(){
	ArrayList<Integer> users = server.getCurrentUsers();
	if(users == null){
		o.println("<p><strong>Error getting the list of current logged users:</strong></p>");
		o.println(server.getException().getMessage());
		return;
	}
	if(users.isEmpty()){
		o.println("<p>No users currently logged in.</p>");
	} else {
		o.println("<p>Current users logged: </p>");
		o.println("<table>");
		//Print Header
		o.println("<tr>");
		o.println("<th>ID</th><th>Login</th><th>Name</th><th>Bank</th>");
		o.println("<th>Hits</th><th>Stands</th><th>Doubles</th><th>Wins</th><th>Loss</th><th>Blackjacks</th>");
		o.println("<th>Kick</th><th>Add money</th>");
		o.println("</tr>");
		for (Integer userID : users) {
			AccountInformation infos = server.getInfos(userID);
			AccountCardStats cardStats = server.getCardStats(userID);
			o.println("<tr>");
			o.println("<td>" + userID + "</td>");
			o.println("<td>" + infos.getUserName() + "</td>");
			o.println("<td>" + infos.getFirstName() + " " + infos.getLastName() + "</td>");
			o.println("<td>$" + server.getBank(userID) + "</td>");
			
			o.println("<td>" + cardStats.getNumOfHits() + "</td>");
			o.println("<td>" + cardStats.getNumOfStands() + "</td>");
			o.println("<td>" + cardStats.getNumOfDoubles() + "</td>");
			o.println("<td>" + cardStats.getNumOfWins() + "</td>");
			o.println("<td>" + cardStats.getNumOfLoss() + "</td>");
			o.println("<td>" + cardStats.getNumOfBlackjacks() + "</td>");
			
			o.println("<td><a href = \"?userToKick="+userID+"\">Kick</a></td>");
			o.println("<td><form method = \"get\" action = \"\">" +
					"<input type = \"text\" name = \"amount\" /><input type = \"hidden\" name = \"userToCredit\" value = \""+userID+"\" />" +
					"<input type = \"submit\" value = \"OK\" /></form></td>");
			o.println("</tr>");
		}
		o.println("</table>");
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

		<% 
		initialize(out);

		if(server.isConnected()){
			printCurrentUsers();
			%>

			<p><a href = "index.jsp">Click here to refresh</a></p>


		<% } else { /* if !server.isConnected() */ %>
			<p>Unable to connect to server.</p>
		<% } %>
    </body>
</html>
