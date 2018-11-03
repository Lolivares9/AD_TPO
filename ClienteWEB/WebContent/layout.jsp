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

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	var user = "${usuario}";
	var categoria = "${categoria}";
	  var sendInfo = {
				 Usuario: user,
				 Categoria: categoria
		       };
	$("#LibreIndiv").click(function() {
		var input = document.createElement("input");
		input.setAttribute("type", "hidden");
		input.setAttribute("name", "Usuario");
		input.setAttribute("value", user);
		
		var input2 = document.createElement("input");
		input2.setAttribute("type", "hidden");
		input2.setAttribute("name", "Categoria");
		input2.setAttribute("value", categoria);

		document.getElementById("LibreIndiv").appendChild(input);
		document.getElementById("LibreIndiv").appendChild(input2);
		document.forms['LibreIndiv'].submit()
	});
})


</script>

</head>
<body>
		<center><h2>Usuario Logueado</h2></center>
		<% String apodo = (String) request.getAttribute("usuario"); %>
		<% String categoria = (String) request.getAttribute("categoria"); %>
		<h4> Usuario logueado: <%= apodo %></h4>
		<h4> Categoria: <%= categoria %></h4>
		
		  <form id="LibreIndiv" name="LibreIndiv" action="ActionsServlet?action=LibreIndiv" method="post">
      			<input type="button" id="LibreIndiv" value="Iniciar partida libre individual" >
     	</form> 
		
		<!--a href="ActionsServlet?action=LibreIndiv" target="_self">Inicia partida libre individual</a--> 

</body>
</html>