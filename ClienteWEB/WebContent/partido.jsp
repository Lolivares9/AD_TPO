<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import= "dto.CartaDTO"%>
 <%@ page import= "dto.PartidoDTO"%>
  <%@ page import= "dto.ParejaDTO"%>
    <%@ page import= "dto.JugadorDTO"%>
 <%@ page import= "java.util.ArrayList"%>
 <%@ page import= "java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/styles.css"></link>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	var mano = 1;
	var baza = 1;
	var detalle = JSON.parse('${detalle}');
	var detalleMap = new Map();
	for (let key of Object.keys(detalle)) {
	    var value = detalle[key];
	    detalleMap.set(key, value);
	}
	$("#carta1").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+detalleMap.get('carta1')+".png')")
	$("#carta2").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+detalleMap.get('carta2')+".png')")
	$("#carta3").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+detalleMap.get('carta3')+".png')")
		
	var posCarta = 50;
	$("#carta1").click(function() {
		moverCarta(this);
		$('#carta1').unbind("click");
		pasarTurno();
	})
	
	$("#carta2").click(function() {
		moverCarta(this);
		$('#carta2').unbind("click");
	})
	
	$("#carta3").click(function() {
		moverCarta(this);
		$('#carta3').unbind("click");
	})
	
	function moverCarta(id){
		$(id).css("-webkit-transform", "translate(" + posCarta + "px, -170px )")
		.css("-ms-transform", "translate(" + posCarta + "px, -170px )")
		.css("-o-transform", "translate(" + posCarta + "px, -170px )")
		.css("-webkit-transform", "translate(" + posCarta + "px, -170px )")
		.css("-moz-transform", "translate(" + posCarta + "px, -170px )")
		.css("transform", "translate(" + posCarta + "px, -170px )");
		posCarta = posCarta - 50;
	}
	
	function pasarTurno(){
		//Verificar que no sea el ultimo turno
		var infoJugada = {
				idJugador : 1
		}
		
		$.ajax({
			type: "POST",
			url: "ActionsServlet/getJugada",
			dataType: "json",
			data : infoJugada
		})
	}
})
</script>
<title>Partido</title>
</head>
<body  bgcolor="#000000">

		<!-- %			
		PartidoDTO p = (PartidoDTO) request.getAttribute("partido");
		p.getIdPartido();
		document.getElementById(idPartido).value();
		ParejaDTO par1 = p.getParejaDTOs().get(0);
		ParejaDTO par2 = p.getParejaDTOs().get(0);
		JugadorDTO jug1 = par1.getJugadorDTO1();
		JugadorDTO jug2 = par2.getJugadorDTO1();
		JugadorDTO jug3 = par1.getJugadorDTO2();
		JugadorDTO jug4 = par2.getJugadorDTO2();		
		%-->
		
		<input id="idPartido" type="hidden"></input>
		<input id="idJugador" type="hidden"></input>
		<input id="idTurno" type="hidden"></input>
		
	
	
	<div id="jugador3" class="player">
		<ul class="cartasJugador">
			<li><a href="#" id= "carta31" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a></li>
			<li><a href="#" id= "carta32" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a></li>
			<li><a href="#" id= "carta33" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a></li>
		</ul>
	</div>
	
	<div id="jugador4" class="player">
		<ul class="cartasJugador-H">
			<li><a href="#" id= "carta41" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a></li>
			<li><a href="#" id= "carta42" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a></li>
			<li><a href="#" id= "carta43" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a></li>
		</ul>
	</div>
		
		
	<div id="jugador1" class="player">
		<ul class="cartasJugador">
			<li><a href="#" id= "carta1" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a></li>
			<li><a href="#" id= "carta2" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a></li>
			<li><a href="#" id= "carta3" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a></li>
		</ul>
	</div>	


</body>
</html>