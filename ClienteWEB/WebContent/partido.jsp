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
	var user = "${usuario}";
	var idPartido = "${idPartido}";
	var detalle = JSON.parse('${detalle}');
	var detalleMap = new Map();
	for (let key of Object.keys(detalle)) {
	    var value = detalle[key];
	    detalleMap.set(key, value);
	}
	
	var c1ID = detalleMap.get('IdCarta1');
	var c2ID = detalleMap.get('IdCarta2');
	var c3ID = detalleMap.get('IdCarta3');
	
	var c1 = detalleMap.get('carta1');
	var c2 = detalleMap.get('carta2');
	var c3 = detalleMap.get('carta3');
	
	$("#carta1").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c1+".png')")
	$("#carta2").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c2+".png')")
	$("#carta3").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c3+".png')")

	$("#carta1").attr("id",detalleMap.get('carta1'));
	$("#carta2").attr("id",detalleMap.get('carta2'));
	$("#carta3").attr("id",detalleMap.get('carta3'));
	
	var posCarta = 50;
	$("#"+c1).click(function() {
		moverCarta(this);
		$("#"+c1).attr("class","carta-V");
		//$("#"+c1).unbind("click");
		guardarJugada(c1ID);
		buscarNovedades(idPartido);
	})
	
	$("#"+c2).click(function() {
		moverCarta(this);
		$("#"+c2).unbind("click");
		guardarJugada(c2ID);
		buscarNovedades(idPartido);
	})
	
	$("#"+c3).click(function() {
		moverCarta(this);
		$("#"+c3).unbind("click");
		guardarJugada(c3ID);
		buscarNovedades(idPartido);
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
	
	function guardarJugada(idC){
		//Verificar que no sea el ultimo turno
		var infoJugada = {
			partido : idPartido,
			apodo : user,
			idCarta : idC
		}
		
		$.ajax({
			type: "POST",
			url: "ActionsServlet?action=GuardarJugada",
			dataType: "json",
			data : infoJugada
		})
	}
	
	//para buscar las novedades tengo que mandar el id de baza que se creo al iniciar el partido	
	function buscarNovedades(idPartido){
		var infoJugada = {
				partido : idPartido
			}
		$.ajax({
			type: "POST",
			url: "ActionsServlet?action=GetNovedad",
			dataType: "json",
			data : infoJugada, //Mandar numero de turno/jugada? para saber que se busque el siguiente en la base directamente
			success: function(data){
				 	console.log(data);
			    	var detalleMap = new Map();
			    	for (let key of Object.keys(data)) {
			    	    var value = data[key];
			    	    detalleMap.set(key, value);
			    	}
			    	 console.log(detalleMap);
			    	 $("#carta22").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+detalleMap.get('carta')+".png')")
			        //Obtener del JSON si es mi turno o no, si no lo es, buscar novedades otra vez
			}
		})
	}
	
})
</script>
<title>Partido</title>
</head>
<body  bgcolor="#000000">

<div>		
<div class="flex-containerIni">

  <a href="#" id= "carta31" class="carta-V"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a>
  <a href="#" id= "carta32" class="carta-V"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a>
  <a href="#" id= "carta33" class="carta-V"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a>  
</div>
 <div class="flex-containerSepara"><div></div></div>

<div>
<div class="flex-containerIni">

      <div class="flex-containerIni">
       <div></div>
          <a href="#" id= "carta31" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a>
  <a href="#" id= "carta32" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a>
  <a href="#" id= "carta33" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a>  
      </div>
           
  <div></div>
  <div></div>
  <div></div>
  <div>
    <div class="flex-containerIni">
        <div></div>
          <a href="#" id= "carta21" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a>
  <a href="#" id= "carta22" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a>
  <a href="#" id= "carta23" class="carta-H"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.png');"
			></a> 
     </div>
</div>

</div>
</div>

 <div class="flex-containerSepara"><div></div></div>
<div class="flex-containerIni">
 <a href="#" id= "carta1" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a>
  <a href="#" id= "carta2" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a>
 <a href="#" id= "carta3" class="carta"
			style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.png');"
			></a> 
</div>
</div>

</body>
</html>

<!-- Version Vieja... Probando estilos-->
<!--html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/styles.css"></link>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	var user = "${usuario}";
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
		pasarTurno($('#carta1').attr("id"));
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
	
	function pasarTurno(idC){
		//Verificar que no sea el ultimo turno
		var infoJugada = {
				apodo : user,
				idCarta : idC
		}
		
		$.ajax({
			type: "POST",
			url: "ActionsServlet?action=GetJugada",
			dataType: "json",
			data : infoJugada
		})
	}
})
</script>
<title>Partido</title>
</head>
<body  bgcolor="#000000">		
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
</html-->