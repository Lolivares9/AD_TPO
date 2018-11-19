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
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/styles.css"></link>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>
$(document).ready(function(){
	var idPartido = "${idPartido}";
	var detalle = JSON.parse('${detalle}');
	var detalleMap = new Map();
	for (let key of Object.keys(detalle)) {
	    var value = detalle[key];
	    detalleMap.set(key, value);
	}
	
	//Contadores
	var cantTurnosJugados = 0;
	var turno = 1;
	var baza= 1; //4 jugadores tiran 1 carta
	var mano = 1; //jugada de 2 o 3 bazas
	var zindex= 1;
	//
	var pos = detalleMap.get('posJugador1');
	
	var c1ID = detalleMap.get('IdCarta1');
	var c2ID = detalleMap.get('IdCarta2');
	var c3ID = detalleMap.get('IdCarta3');
	
	var idArray = []; //Array para saber que cartas no se jugaron
	idArray.push(c1ID);
	idArray.push(c2ID);
	idArray.push(c3ID);
	
	var cartasPos = [];
	cartasPos.push(c1ID);
	cartasPos.push(c2ID);
	cartasPos.push(c3ID);
	
	var c1 = detalleMap.get('carta1');
	var c2 = detalleMap.get('carta2');
	var c3 = detalleMap.get('carta3');
	
	$("#carta1").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c1+".jpg')");
	$("#carta2").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c2+".jpg')");
	$("#carta3").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c3+".jpg')");

	$("#carta1").attr("id",c1ID);
	$("#carta2").attr("id",c2ID);
	$("#carta3").attr("id",c3ID);
	
	$("#"+c1ID).on("click", clicked);
	$("#"+c2ID).on("click", clicked);
	$("#"+c3ID).on("click", clicked);
	
	$("#par1").text("Pareja1: "+ detalleMap.get('apodoJugador1') + " (" + detalleMap.get('catJugador1') + ") y " + detalleMap.get('apodoJugador3') + " (" + detalleMap.get('catJugador3') + ")");
	$("#par2").text("Pareja2: "+ detalleMap.get('apodoJugador2') + " (" + detalleMap.get('catJugador2') + ") y " + detalleMap.get('apodoJugador4') + " (" + detalleMap.get('catJugador4') + ")");
	$("#jug1").text(detalleMap.get('apodoJugador1'));
	$("#jug2").text(detalleMap.get('apodoJugador2'));
	$("#jug3").text(detalleMap.get('apodoJugador3'));
	$("#jug4").text(detalleMap.get('apodoJugador4'));
	
	var user = detalleMap.get('apodoJugador1');
	var posCarta = 50;

	function verificarTurno(){
		if(cantTurnosJugados === (pos-1)){
			//Habilitar botones de envite
			habilitarCartas();
			pos++;
			pos++;
			pos++;
			pos++;
			console.log("Es mi Turno");
		}else{
			deshabilitarCartas();
			console.log("NO es mi Turno");
			buscarNovedades(idPartido);
		}
	}
	
	//Lo llamo al incio para ver si soy el primero en jugar
	verificarTurno();	
	
	function clicked() {
		deshabilitarCartas();
		var index = $.inArray($(this).attr("id"), cartasPos);
		idArray.splice($.inArray($(this).attr("id"), idArray), 1);
		moverCarta(this, index);
		guardarJugada($(this).attr("id"));
	}
	
	function deshabilitarCartas(){
		jQuery.each(idArray, function(index, value){
			$("#"+value).off("click", clicked);
		});
	}
	
	function habilitarCartas(){
		jQuery.each(idArray, function(index, value){
			$("#"+value).on("click", clicked);
		});
	}

	function moverCarta(id, index){
		console.log(index);
		if(index === 0){
			$(id).css("transform", "translate(" + (posCarta+50) + "px, -140px )");
		}else if(index === 1){
			$(id).css("transform", "translate(" + (posCarta-50) + "px, -145px )");
		}else{
			$(id).css("transform", "translate(" + (posCarta-125) + "px, -150px )");
		}
		$(id).css('zIndex', zindex++);
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
		}).always(function(){
			//if(turno es el ultimo)
			//hacer metodo para cambiar de baza, mostrar puntajes, etc
			console.log("busco siguiente turno");
			buscarNovedades(idPartido);
		});
	}
	
	//para buscar las novedades tengo que mandar el id de baza que se creo al iniciar el partido	
	function buscarNovedades(idPartido){
		setTimeout(function () {
			var infoJugada = {
					partido : idPartido
				}
			$.ajax({
				type: "POST",
				url: "ActionsServlet?action=GetNovedad",
				dataType: "json",
				data : infoJugada //Mandar numero de turno/jugada? para saber que se busque el siguiente en la base directamente
				/*success: function(data){
				    	var detalleMap = new Map();
				    	for (let key of Object.keys(data)) {
				    	    var value = data[key];
				    	    detalleMap.set(key, value);
				    	}
				    	 console.log(detalleMap);
				    	 $("#carta22").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+detalleMap.get('carta')+".jpg')")
				    	 var miTurno = detalleMap.get('esMiTurno');
				    	 if(miTurno === true){
				    		 console.log("Me toca jugar");
				    	 }else{
				    		 console.log("NO Me toca jugar");
				    	 }
				        //Obtener del JSON si es mi turno o no, si no lo es, buscar novedades otra vez
				},    
				error: function() { 
					cantTurnosJugados++;
					verificarTurno();
			    } */ 
			}).always(function(){
				//if(turno es el ultimo)
				//hacer metodo para cambiar de baza, mostrar puntajes, etc
				cantTurnosJugados++;
				verificarTurno();
			});
		}, 3000);
	}
	
})
</script>
<title>Partido</title>
</head>
<body style="background-color: rgb(21,72,0);">

<div class="game-wrapper">
			<div class="game-panel">
				<div class="game-header">
					<h1>Truco</h1>
					<p id="par1">Pareja 1:</p>
					<p id="par2">Pareja 2:</p>
				</div>
				<table id="game-score">
					<thead>
						<tr>
							<th class="player-one-name human-name" style="color:white;">Pareja1</th>
							<th class="player-two-name" style="color:white;">Pareja2</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="player-one-points">0</td>
							<td class="player-two-points">0</td>
						</tr>
					</tbody>
				</table>
				<div class="game-action">
					<fieldset id="cantos" class="cantos-panel">
						<legend>Cantos</legend>
						<input type="button" value="Envido" id="Envido" class="canto btn btn-primary" data-envido="E" style="display: inline-block;">
						<input type="button" value="Real Envido" id="RealEnvido" class="canto btn btn-success" data-envido="R" style="display: inline-block;">
						<input type="button" value="Falta Envido" id="FaltaEnvido" class="canto btn btn-inverse" data-envido="F" style="display: inline-block;">
						<input type="button" value="Me voy al mazo" id="IrAlMazo" class="btn">
						<input type="button" value="Truco" id="Truco" class="cantot btn btn-primary" data-truco="T" style="display: inline-block;">
						<input type="button" value="Quiero re Truco" id="reTruco" class="cantot btn btn-success" data-truco="RT" style="display: none;">
						<input type="button" value="Quiero vale 4" id="vale4" class="cantot btn btn-inverse" data-truco="V" style="display: none;">
						<div class="label-cantos label-cantos--SN" style="display: none;">Mi respuesta ...</div>
						<input type="button" value="Quiero" id="Quiero" class="boton btn" style="display: none;">
						<input type="button" value="No Quiero" id="NoQuiero" class="boton btn" style="display: none;">
					</fieldset>
				</div>
				<h2 class="log-title">Gameplay</h2>
				<div id="log"></div>
			</div>
			<div class="game-main" style="position: relative;">
				<div>		
					<div class="flex-containerIni">
						<p id="jug3">Jugador3</p>
					  <a href="#" id= "carta31" class="carta-V"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					  <a href="#" id= "carta32" class="carta-V"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					  <a href="#" id= "carta33" class="carta-V"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>  
								<div class="div1"></div>
					</div>
					 <div class="flex-containerSepara"><div></div></div>

					<div>
					<div class="flex-containerIni">

						  <div class="flex-containerIni">
						   <p id="jug4">Jugador4</p>
							  <a href="#" id= "carta31" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a>
					  <a href="#" id= "carta32" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a>
					  <a href="#" id= "carta33" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a>  
						  </div>
							   
					  <div></div>
					  <div></div>
					  <div></div>
					  <div>
						<div class="flex-containerIni">
							<p id="jug2">Jugador2</p>
							  <a href="#" id= "carta21" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a>
					  <a href="#" id= "carta22" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a>
					  <a href="#" id= "carta23" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a> 
						 </div>
					</div>

					</div>
					</div>

					 <div class="flex-containerSepara"><div></div><div></div></div>
					 <div class="flex-containerSepara"><div></div><div></div></div>
					<div class="flex-containerIni">
					<p id="jug1">Jugador1</p>
					 <a href="#" id= "carta1" class="carta"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					  <a href="#" id= "carta2" class="carta"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					 <a href="#" id= "carta3" class="carta"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a> 
								<div class="div1"></div>
					</div>
					<div class="flex-containerSepara">
						<!-- 
						<div>
							<input type="button" id="LibreIndiv" value="Quiero">
							<input type="button" id="LibreIndiv" value="No Quiero">
						</div>
						
						<div>
							<input type="button" id="LibreIndiv" value="Envido">
							<input type="button" id="LibreIndiv" value="Falta Envido">
							<input type="button" id="LibreIndiv" value="Real Envido">
						</div>
						
						<div>
							<input type="button" id="LibreIndiv" value=" Truco ">
							<input type="button" id="LibreIndiv" value="Re Truco">
							<input type="button" id="LibreIndiv" value="Vale Cuatro">
						</div>
						-->
					</div>
				</div>
			</div>
			<div class="game-footer">
			</div>
		</div>

</body>
</html>