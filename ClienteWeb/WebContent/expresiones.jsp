<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Expresiones JSP</title>
</head>
<body>
<center><h2>Sintaxis clásica</h2></center>
<h4> número aleatorio: <%= (int) (Math.random()*100) %></h4>
<h4> hoy: <%= new java.util.Date() %></h4>
<h4> port: <%= request.getServerPort() %></h4>
<h4> server: <%= application.getServerInfo() %></h4>
<h4> local: <%= request.getLocalAddr() %></h4>
<h4> sessionId: <%= session.getId() %></h4>
</body>
</html>