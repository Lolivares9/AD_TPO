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
		  $.ajax({
			  type: 'POST',
		      url: 'ActionsServlet?action=LibreIndiv',
		      contentType: 'application/json; charset=utf-8',
		      dataType: 'json',
		      data: JSON.stringify(sendInfo),
	          success: function(response ,response2) {
	        	  $("html").html(response2);
                   },
     	          error: function(response,response2) {
     	        	 $("html").html(response2);
                   }
		    });
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
			
		<input type="button" id="LibreIndiv" value="Iniciar partida libre individual">
		<!--a href="ActionsServlet?action=LibreIndiv" target="_self">Inicia partida libre individual</a--> 

</body>
</html>