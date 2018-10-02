<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Asignaciones JSP</title>
</head>
<body>
<center><h2>Sintaxis clásica</h2></center>
<% int a =  (int) (Math.random()*6)+1; %>
<% int b =  (int) (Math.random()*6)+1; %>
<h4> número a: <%= a %></h4>
<h4> número b: <%= b %></h4>
<h4> suma: <%= a + b %></h4>

</body>
</html>