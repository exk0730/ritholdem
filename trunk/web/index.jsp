<%@page contentType="text/html" pageEncoding="UTF-8" import="servlet.ServerAdminWebController" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%!
ServerAdminWebController server = new ServerAdminWebController();
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

		<% } else { //if !server.isConnected() %>
			<p>Unable to connect to server.</p>
		<% } %>
    </body>
</html>
