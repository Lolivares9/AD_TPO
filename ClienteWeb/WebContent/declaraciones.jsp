<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Declaraciones JSP</title>

<%! private int contador = 0; %>
<%! private String mostrarAccesos(int a) {
		if (a == 1) return "primer acceso";
		else return a + " accesos";
	}
%>

</head>
<body>
<center><h2>Sintaxis clásica</h2></center>

<h4> accesos a la página: <%= ++contador %></h4>
<h4> <%= mostrarAccesos(contador) %></h4>

</body>
</html>