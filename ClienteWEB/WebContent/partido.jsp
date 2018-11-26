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
	var _log = document.getElementById('log');
	var i = 0
	while(i < 100){
		i++;
		_log.innerHTML =  _log.innerHTML  + "<b>" +  "Es tu Turno" + "</b> " + '<br /> ';
	}
})
</script>
<title>Partido</title>
</head>
<body style="background-color: rgb(21,72,0);">

<div class="game-wrapper">
			<div class="panel-Juego">
				<div class="game-header">
					<h1>Truco</h1>
					<p id="par1">Pareja 1:</p>
					<p id="par2">Pareja 2:</p>
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
				<h2 class="log-titulo">Jugadas</h2>
					<div id="log"></div>
				</div>
				</div>
				<div class="game-action">
					<fieldset id="cantos" class="cantos-panel">
						<legend  style="color:white;">Cantos</legend>
							<input type="button" value="Envido" id="Envido" class="canto btn btn-primary" data="Envido" style="display: inline-block;">
							<input type="button" value="Real Envido" id="RealEnvido" class="canto btn btn-success" data="Real_Envido" style="display: inline-block;">
							<input type="button" value="Falta Envido" id="FaltaEnvido" class="canto btn btn-inverse" data="Falta_Envido" style="display: inline-block;">
							<!-- input type="button" value="Me voy al mazo" id="IrAlMazo" data="Mazo" class="btn"-->
							<input type="button" value="Truco" id="Truco" class="cantot btn btn-primary" data="Truco" style="display: inline-block;">
							<input type="button" value="Quiero re Truco" id="ReTruco" class="cantot btn btn-success" data="Re_Truco" style="display: inline-block;">
							<input type="button" value="Quiero vale 4" id="Vale4" class="cantot btn btn-inverse" data="Vale_Cuatro" style="display: inline-block;">
							<input type="button" value="Quiero" id="Quiero" class="boton btn" data="Querido" style="display: inline-block;">
							<input type="button" value="No Quiero" id="NoQuiero" class="boton btn" data="NoQuerido" style="display: inline-block;">
					</fieldset>
				</div>
			<div class="game-main" style="position: relative;">
				<div>		
					<div class="flex-containerIni" id="CartasJ3">
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
					 <!-- div class="flex-containerSepara"><div></div></div-->

					<div>
					<div class="flex-containerIni">

						  <div class="flex-containerIni" id="CartasJ4">
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
						<div class="flex-containerIni" id="CartasJ2">
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
					 <!-- div class="flex-containerSepara"><div></div><div></div></div-->
					<div class="flex-containerIni" id="CartasJ1">
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
					</div>
				</div>
			</div>
			<div class="game-footer">
			</div>
		</div>
		<form id="formulMenu" name="Menu" action="ActionsServlet" method="post" style="display: none;">
      			<input type="button" id="Menu" value="Menu" >
     	</form> 
</body>
</html>