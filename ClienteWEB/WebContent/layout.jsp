<%@page import="servlets.LoginServlet"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
				<center><h2>Usuario Logueado</h2></center>
				<% String user = LoginServlet.getUsuarioLogueado(); %>
				<h4> Usuario logueado: <%= user %></h4>
</body>
</html>