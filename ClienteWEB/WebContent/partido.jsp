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
	
	$("#carta1").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c1+".jpg')")
	$("#carta2").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c2+".jpg')")
	$("#carta3").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c3+".jpg')")

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
			    	 $("#carta22").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+detalleMap.get('carta')+".jpg')")
			        //Obtener del JSON si es mi turno o no, si no lo es, buscar novedades otra vez
			}
		})
	}
	
})
</script>
<title>Partido</title>
</head>
<body>

<div class="game-wrapper">
			<div class="game-panel">
				<div class="game-header">
					<h1>Truco argento</h1>
				</div>
				<table id="game-score">
					<thead>
						<tr>
							<th contenteditable="true" class="player-one-name human-name">Pareja1</th>
							<th class="player-two-name">Pareja2</th>
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

					  <a href="#" id= "carta31" class="carta-V"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					  <a href="#" id= "carta32" class="carta-V"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					  <a href="#" id= "carta33" class="carta-V"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>  
					</div>
					 <div class="flex-containerSepara"><div></div></div>

					<div>
					<div class="flex-containerIni">

						  <div class="flex-containerIni">
						   <div></div>
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
							<div></div>
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

					 <div class="flex-containerSepara"><div></div></div>
					<div class="flex-containerIni">
					 <a href="#" id= "carta1" class="carta"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					  <a href="#" id= "carta2" class="carta"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a>
					 <a href="#" id= "carta3" class="carta"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta.jpg');"
								></a> 
					</div>
					<div class="flex-containerSepara">
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
					</div>
				</div>
			</div>
			<div class="game-footer">
			</div>
		</div>

</body>
</html>