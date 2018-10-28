<%@page import="servlets.LoginServlet"%>
<%@page import="servlets.ActionsServlet"%>
<%@page import="dto.JugadorDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Menú Principal</title>

		<script type="text/javascript" >
			function LibreIndiv(){
				alert('Gracias por hacer click aca');
			}
		</script>

</head>
<body>
				<center><h2>Usuario Logueado</h2></center>
				<% JugadorDTO user = LoginServlet.getUsuarioLogueado(); %>
				<h4> Usuario logueado: <%= user.getApodo() %></h4>
				
				<!-- input type="button" onclick="ActionsServlet?action=LibreIndiv" value="Inicia partida libre individual"-->
				<a href="ActionsServlet?action=LibreIndiv" target="_self">Inicia partida libre individual</a> 

</body>
</html>