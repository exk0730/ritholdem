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

			ServerStatistics ss = server.getServerStats();
			%>

			<p></p>

			<p><a href = "server_stats.jsp">Click here to refresh</a></p>

		<% } else { /* if !server.isConnected() */ %>
			<p>Unable to connect to server.</p>
		<% } %>
    </body>
</html>
