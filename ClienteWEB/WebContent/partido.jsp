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
	var idBaza = 1;// = JSON.parse('${idBaza}'); Tiene que venir al crear partido
	var detalleMap = new Map();
	for (let key of Object.keys(detalle)) {
	    var value = detalle[key];
	    detalleMap.set(key, value);
	}
	var _log = document.getElementById('log');
	
	//Contadores
	var cantTurnosJugados = 0;
	var turnosBaza = 0;
	var numBaza= 1; //4 jugadores tiran 1 carta
	var mano = 1; //jugada de 2 o 3 bazas
	var zindex= 1;
	//
	var pos = parseInt(detalleMap.get('posJugador1'));
	
	var c1ID = detalleMap.get('IdCarta1');
	var c2ID = detalleMap.get('IdCarta2');
	var c3ID = detalleMap.get('IdCarta3');
	
	var apodoJug1 = detalleMap.get('apodoJugador1');
	var apodoJug2 = detalleMap.get('apodoJugador2');
	var apodoJug3 = detalleMap.get('apodoJugador3');
	var apodoJug4 = detalleMap.get('apodoJugador4');
	
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
	
	/* Pongo las imagenes de las cartas del jugador */
	$("#carta1").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c1+".jpg')");
	$("#carta2").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c2+".jpg')");
	$("#carta3").css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+c3+".jpg')");

	$("#carta1").attr("id",c1ID);
	$("#carta2").attr("id",c2ID);
	$("#carta3").attr("id",c3ID);
	
	/* Seteo los id de las cartas de cada jugador */
	$("#carta21").attr("id",apodoJug2+"c1");
	$("#carta22").attr("id",apodoJug2+"c2");
	$("#carta23").attr("id",apodoJug2+"c3");
	
	$("#"+apodoJug2+"c1").data('pos', 'D');
	$("#"+apodoJug2+"c2").data('pos', 'D');
	$("#"+apodoJug2+"c3").data('pos', 'D');
	
	$("#carta31").attr("id",apodoJug3+"c1");
	$("#carta32").attr("id",apodoJug3+"c2");
	$("#carta33").attr("id",apodoJug3+"c3");
	
	$("#"+apodoJug3+"c1").data('pos', 'A');
	$("#"+apodoJug3+"c2").data('pos', 'A');
	$("#"+apodoJug3+"c3").data('pos', 'A');
	
	$("#carta41").attr("id",apodoJug4+"c1");
	$("#carta42").attr("id",apodoJug4+"c2");
	$("#carta43").attr("id",apodoJug4+"c3");
	
	$("#"+apodoJug4+"c1").data('pos', 'I');
	$("#"+apodoJug4+"c2").data('pos', 'I');
	$("#"+apodoJug4+"c3").data('pos', 'I');
	
	/* Habilito el handler para el click*/
	$("#"+c1ID).on("click", clicked);
	$("#"+c2ID).on("click", clicked);
	$("#"+c3ID).on("click", clicked);
	
	/* Nombres de los jugadores */
	var numPareja = detalleMap.get('parejaJugador1');
	if(numPareja === "1"){
		$("#par1").text("Pareja1: "+ detalleMap.get('apodoJugador1') + " (" + detalleMap.get('catJugador1') + ") y " + detalleMap.get('apodoJugador3') + " (" + detalleMap.get('catJugador3') + ")");
		$("#par2").text("Pareja2: "+ detalleMap.get('apodoJugador2') + " (" + detalleMap.get('catJugador2') + ") y " + detalleMap.get('apodoJugador4') + " (" + detalleMap.get('catJugador4') + ")");
	}else{
		$("#par1").text("Pareja1: "+ detalleMap.get('apodoJugador2') + " (" + detalleMap.get('catJugador2') + ") y " + detalleMap.get('apodoJugador4') + " (" + detalleMap.get('catJugador4') + ")");
		$("#par2").text("Pareja2: "+ detalleMap.get('apodoJugador1') + " (" + detalleMap.get('catJugador1') + ") y " + detalleMap.get('apodoJugador3') + " (" + detalleMap.get('catJugador3') + ")");
	}

	$("#jug1").text(apodoJug1);
	$("#jug2").text(apodoJug2);
	$("#jug3").text(apodoJug3);
	$("#jug4").text(apodoJug4);

	
	var user = detalleMap.get('apodoJugador1');

	function verificarTurno(){
		/*if(turnosBaza === 4){
			finalizoBaza();
		}*/
		if(cantTurnosJugados === (pos-1)){
			//Habilitar botones de envite
			habilitarCartas();
			pos += 4;
			_log.innerHTML =  _log.innerHTML  + "<b>" +  "Es mi Turno" + "</b> " + '<br /> ';
			console.log("Es mi Turno");
		}else{
			deshabilitarCartas();
			console.log("NO es mi Turno");
			_log.innerHTML =  _log.innerHTML  + "<b>" +  "NO es mi Turno" + "</b> "+ '<br /> ' ;
			getSiguienteTurno();
		}
	}
	
	function actualizarDatos(data){
		console.log("actualizo datos"+data.get("flag"))
		var flag = data.get("flag");
		if(flag === "Baza"){
			pos = parseInt(detalleMap.get('posJugador1')); //Actualizo mi numero de turno
			//Tengo que actualizar id de baza
			cantTurnosJugados = 0;
		}else if (flag === "Mano"){
			//Buscar datos de Mano nueva
		}else if (flag === "Partida"){
			//Buscar datos de partida nueva
			//Decir quien gano antes
		}//Otro else para ver si termino partido???
		//Traer la siguiente baza con el orden actualizado segun el que tiro la carta más alta
		turnosBaza = 0;
		/**************************************/
		idBaza++; // SOLO PARA PROBAR, BORRAR DESPUES DE QUE LLEGUE EL ID AL CREAR PARTIDO
		/**************************************/
		console.log("sumo 1 a la baza");
		verificarTurno();
	}
	
	function finalizoBaza(){
		numBaza++;//Paso a otra baza, puedo traer la baza que termino y mostrar resultados
		buscarPartidoActualizado();
	}
	
	//Lo llamo al incio para ver si soy el primero en jugar
	verificarTurno();	
	
	function clicked() {
		var index = $.inArray($(this).attr("id"), idArray);
		if(index === -1){
			return;
		}
		moverCarta(this);
		deshabilitarCartas();
		idArray.splice($.inArray($(this).attr("id"), idArray), 1);
		cantTurnosJugados++;
		turnosBaza++;
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

	function moverCarta(carta){
		var index = $.inArray($(carta).attr("id"), cartasPos);
		if(index === 0){
			$(carta).css("transform", "translate(65px, -140px )");
		}else if(index === 1){
			$(carta).css("transform", "translate(0px, -145px )");
		}else{
			$(carta).css("transform", "translate(-65px, -150px )");
		}
		$(carta).css('zIndex', zindex++);
	}
	
	function mostrarCartaJugador(jugador, cartaJugada){
		
		 $("#"+jugador+"c"+numBaza).css('zIndex', zindex++);
		var pos = $("#"+jugador+"c"+numBaza).data('pos');
		if(pos === "D"){
			 if(numBaza === 1){
	    	 	$("#"+jugador+"c"+numBaza).css("transform", "translate(-130px, 70px) rotate(90deg) rotateY(180deg) rotateX(180deg)");
			 }else if(numBaza === 2){
				 $("#"+jugador+"c"+numBaza).css("transform", "translate(-140px, -10px) rotate(90deg)");
			 }else{
				 $("#"+jugador+"c"+numBaza).css("transform", "translate(-150px, -90px) rotate(90deg)");
			 }
			 $("#"+jugador+"c"+numBaza).css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+cartaJugada+"H.jpg')");
		}else if(pos === "I"){
			 $("#"+jugador+"c"+numBaza).css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+cartaJugada+"H.jpg')");
			 if(numBaza === 1){
		    	 	$("#"+jugador+"c"+numBaza).css("transform", "translate(130px, 70px) rotate(-90deg)");
				 }else if(numBaza === 2){
					 $("#"+jugador+"c"+numBaza).css("transform", "translate(140px, -10px) rotate(-90deg)");
				 }else{
					 $("#"+jugador+"c"+numBaza).css("transform", "translate(150px, -90px) rotate(-90deg)");
				 }
		}else if(pos === "A"){
			 $("#"+jugador+"c"+numBaza).css("background", "url('${pageContext.request.contextPath}/resources/cartas/"+cartaJugada+".jpg')");
			 if(numBaza === 1){
		    	 	$("#"+jugador+"c"+numBaza).css("transform", "translate(65px, 140px)");
				 }else if(numBaza === 2){
					 $("#"+jugador+"c"+numBaza).css("transform", "translate(0px, 145px)");
				 }else{
					 $("#"+jugador+"c"+numBaza).css("transform", "translate(-65px, 150px)");
				 }
		}

	}
	
	function buscarPartidoActualizado(){
		var infoJugada = {
			usuario : detalleMap.get('apodoJugador1'),
			modalidad : "Libre_individual"
		}
		$.ajax({
			type: "POST",
			url: "ActionsServlet?action=BuscarActualizacion",
			dataType: "json",
			data : infoJugada,
			success: function(data){
				console.log(data);
				return data;    	
		},    
		error: function() { 
			console.log(data);
			return data;
	    } 
		}).always(function(data){
			console.log(data);
	    	var detalleMap = new Map();
	    	for (let key of Object.keys(data)) {
	    	    var value = data[key];
	    	    detalleMap.set(key, value);
	    	}
			actualizarDatos(detalleMap);
		});
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
			getSiguienteTurno();
		});
	}
	
	//para buscar las novedades tengo que mandar el id de baza que se creo al iniciar el partido	
	function getSiguienteTurno(){
		setTimeout(function () {
			var infoJugada = {
					baza : idBaza,
					numTurnos : turnosBaza
				}
			$.ajax({
				type: "POST",
				url: "ActionsServlet?action=GetNovedad",
				dataType: "json",
				data : infoJugada,
				success: function(data){
						//Validar que la mano no haya terminado (una de las parejas ya gano dos bazas)
				    	var detalleMap = new Map();
				    	for (let key of Object.keys(data)) {
				    	    var value = data[key];
				    	    detalleMap.set(key, value);
				    	}
				    	 console.log(detalleMap);
				    	 var jugador = detalleMap.get('apodo');
				    	 var cartaJugada = detalleMap.get('carta');
				    	 cantTurnosJugados++;
				    	 turnosBaza++;
				    	 mostrarCartaJugador(jugador, cartaJugada);
				 		if(turnosBaza === 4){
							finalizoBaza();
						}else{
				    	 	verificarTurno();
						}
				},    
				error: function() { 
					//if(turno es el ultimo)
					//hacer metodo para cambiar de baza, mostrar puntajes, etc
					verificarTurno();
			    } 
			})
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
				<table id="puntajes">
					<thead>
						<tr>
							<th id="JugPareja1" style="color:white;">Pareja1</th>
							<th id="JugPareja2" style="color:white;">Pareja2</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td id="ptsPareja1" style="color:white;">0</td>
							<td id="ptsPareja2" style="color:white;">0</td>
						</tr>
					</tbody>
				</table>
				<div class="game-action">
					<fieldset id="cantos" class="cantos-panel">
						<legend  style="color:white;">Cantos</legend>
						<input type="button" value="Envido" id="Envido" class="canto btn btn-primary" data-envido="E" style="display: inline-block;">
						<input type="button" value="Real Envido" id="RealEnvido" class="canto btn btn-success" data-envido="R" style="display: inline-block;">
						<input type="button" value="Falta Envido" id="FaltaEnvido" class="canto btn btn-inverse" data-envido="F" style="display: inline-block;">
						<input type="button" value="Me voy al mazo" id="IrAlMazo" class="btn">
						<input type="button" value="Truco" id="Truco" class="cantot btn btn-primary" data-truco="T" style="display: inline-block;">
						<input type="button" value="Quiero re Truco" id="reTruco" class="cantot btn btn-success" data-truco="RT" style="display: none;">
						<input type="button" value="Quiero vale 4" id="vale4" class="cantot btn btn-inverse" data-truco="V" style="display: none;">
						<input type="button" value="Quiero" id="Quiero" class="boton btn" style="display: none;">
						<input type="button" value="No Quiero" id="NoQuiero" class="boton btn" style="display: none;">
					</fieldset>
				</div>
				<h2 class="log-titulo">Jugadas</h2>
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
							  <a href="#" id= "carta41" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a>
					  <a href="#" id= "carta42" class="carta-H"
								style= "background: url('${pageContext.request.contextPath}/resources/cartas/Carta-H.jpg');"
								></a>
					  <a href="#" id= "carta43" class="carta-H"
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