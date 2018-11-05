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
	var r = confirm("No hay suficientes jugadores disponibles, desea esperar?")
	if (r == true) {
		buscarPartidos();
	} else {
	    txt = "You pressed Cancel!";
	}
	function buscarPartidos() {
	    setTimeout(function () {
	    	
			var input = document.createElement("input");
			input.setAttribute("type", "hidden");
			input.setAttribute("name", "Usuario");
			input.setAttribute("value", "faculth");
			
			var input2 = document.createElement("input");
			input2.setAttribute("type", "hidden");
			input2.setAttribute("name", "Categoria");
			input2.setAttribute("value", "Novato");
	
			document.getElementById("formul").appendChild(input);
			document.getElementById("formul").appendChild(input2);
			document.forms['formul'].submit()
	        buscarPartidos();
	    }, 1000);
	}
})
</script>
</head>
<body>
	<p>Lobby de partidos</p>
	
	<form id="formul" name="LibreIndiv" action="ActionsServlet?action=LibreIndiv" method="post">
      			<input type="button" id="LibreIndiv" value="Iniciar partida libre individual" >
     </form> 
	<div class="loading-overlay">
		<div class="loading-overlay-bg"></div>
		<div class="spinner">
			<div class="rect1"></div>
			<div class="rect2"></div>
			<div class="rect3"></div>
			<div class="rect4"></div>
			<div class="rect5"></div>
		</div>
	</div>
</body>

</html>
