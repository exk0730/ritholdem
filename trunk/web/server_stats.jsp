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

		<p id ="menu"><a href ="current_users.jsp">Current Users</a> <a class ="current" href ="server_stats.jsp">Server Statistics</a></p>

		<%
		if(server.isConnected()){

			ServerStatistics ss;
			%>			
			<p>Server log:</p>
			<table id = "server_stats">
				<tr>
					<th>Entry</th>
					<th>New users</th>
					<th>Total amount of bets</th>
					<th>Dealer's earnings</th>
					<th>Number of dealer's wins</th>
					<th>Number of users' wins</th>
					<th>Number of blackjacks</th>
					<th>Last reboot</th>
				</tr>

				<%
				ArrayList<Integer> entries = server.getServerStatsEntries();
				for(Integer serverID : entries){
					ss = server.getServerStats(serverID);
					%>
					<tr>
						<td><%= serverID %></td>
						<td><%= ss.getNumNewUsers() %></td>
						<td><%= ss.getTotalAmtBet() %></td>
						<td><%= ss.getDealerEarnings() %></td>
						<td><%= ss.getDealerWins() %></td>
						<td><%= ss.getUserWins() %></td>
						<td><%= ss.getTotalBlackjacks() %></td>
						<td><%= ss.getLastServerReboot() %></td>
					</tr>
				<%} %>
			</table>

			<p><a href = "server_stats.jsp">Click here to refresh</a></p>

		<% 
			} else { /* if !server.isConnected() */ %>
			<p>Unable to connect to server.</p>
		<% } %>
    </body>
</html>
