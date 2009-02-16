<%@page contentType="text/html" pageEncoding="UTF-8" import="servlet.*, server.*, game.*, java.util.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%!
//Connects to the server
protected ServerAdminWebController server = new ServerAdminWebController();
%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Blackjack Server Administration Panel</title>
		<link type="text/css" rel="stylesheet" href ="style.css" />
    </head>
    <body>
        <h1>Blackjack Server Administration Panel</h1>

		<p id ="menu"><a class ="current" href ="current_users.jsp">Current Users</a> <a href ="server_stats.jsp">Server Statistics</a></p>

		<%
		if(server.isConnected()){

			/* Handle requests */
			String userToKick = request.getParameter("userToKick");
			if(userToKick != null){
				if(server.kick(new Integer(userToKick).intValue())){
					out.println("<p>The user has been kicked successfully!</p>");
				} else {
					out.println("<p>Impossible to kick the given user, please check that he/she is connected!</p>");
				}
			}
			String userToCredit = request.getParameter("userToCredit");
			String amount = request.getParameter("amount");
			//Ifparams are valid
			if(amount != null && !amount.equals("") && userToCredit != null && !userToCredit.equals("")) {
				int a = new Integer(amount);
				if(a > 0){
					if(server.addMoney(new Integer(userToCredit).intValue(), a)){
						out.println("<p>The user has been credited successfully!</p>");
					} else {
						out.println("<p>Impossible to credit the given user!</p>");
					}
				} else {
					out.println("<p>The value must be positive.</p>");
				}
			}

			/* Print current users with their stats */
			ArrayList<Integer> users = server.getCurrentUsers();
			if(users == null){ %>
				<p><strong>Error getting the list of current logged users:</strong></p>
				<%= server.getException().getMessage() %>
				<% return;
			}
			if(users.isEmpty()){ %>
				<p>No users currently logged in.</p>

			<% } else { %>
				<p>List of current users logged in:</p>
				<table id = "current_users">
					<tr>
						<th>ID</th><th>Login</th><th>Name</th><th>Bank</th>
						<th>Hits</th><th>Stands</th><th>Doubles</th><th>Wins</th><th>Loss</th><th>Blackjacks</th>
						<th>Kick</th><th>Add money</th>
					</tr>
					<%
					for (Integer userID : users) {
						AccountInformation infos = server.getInfos(userID);
						AccountCardStats cardStats = server.getCardStats(userID);
						%>
						<tr>
						<td><%= userID %></td>
						<td><%= infos.getUserName() %></td>
						<td><%= infos.getFirstName() + " " + infos.getLastName() %></td>
						<td>$<%= server.getBank(userID) %></td>

						<td><%= cardStats.getNumOfHits() %></td>
						<td><%= cardStats.getNumOfStands() %></td>
						<td><%= cardStats.getNumOfDoubles() %></td>
						<td><%= cardStats.getNumOfWins() %></td>
						<td><%= cardStats.getNumOfLoss() %></td>
						<td><%= cardStats.getNumOfBlackjacks() %></td>

						<td><a href = "?userToKick=<%= userID %>">Kick</a></td>
						<td>
							<form method = "get" action = "">
								<input type = "text" name = "amount" />
								<input type = "hidden" name = "userToCredit" value = "<%= userID %>" />
								<input type = "submit" value = "OK" />
							</form>
						</td>
						</tr>
					<% } %>
				</table>

			<% } %>

			<p><a href = "current_users.jsp">Click here to refresh</a></p>

		<% } else { /* if !server.isConnected() */ %>
			<p>Unable to connect to server.</p>
		<% } %>



    </body>
</html>
