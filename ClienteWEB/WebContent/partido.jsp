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
	
<script type="text/javascript">
	function clicked(id) { 
		var elemento = document.getElementById(id);
		elemento.className = "mesa";
	}
	
</script>
<title>Partido</title>

</head>
<body  bgcolor="#00aa00">
	<%			
		PartidoDTO p = (PartidoDTO) request.getAttribute("partido");
		ParejaDTO par1 = p.getParejaDTOs().get(0);
		ParejaDTO par2 = p.getParejaDTOs().get(0);
		JugadorDTO jug1 = par1.getJugadorDTO1();
		JugadorDTO jug2 = par2.getJugadorDTO1();
		JugadorDTO jug3 = par1.getJugadorDTO2();
		JugadorDTO jug4 = par2.getJugadorDTO2();
		
		
	%>
	
	<div class="cartasJugador">
			<div class="carta" id="1" onclick = "clicked('1')">
				<div class="inner-wrap">
					<div class="mano" 
					style= "left: 252.5px; top: 426px; z-index: 423; width: 100px; height: 100px;">
					<img src="${pageContext.request.contextPath}/resources/cartas/<%=par1.getCartasJug1().get(0).getNumero()+""+par1.getCartasJug1().get(0).getPalo()%>.png" alt="" />
					</div>
					<div class="mesa"></div>
				</div>
			</div>
			<div class="carta2" id="2" onclick = "clicked('2')">
				<div class="inner-wrap">
					<div class="mano" style= "left: 360.5px; top: 426px; z-index: 423; width: 100px; height: 100px;">
					<img src="${pageContext.request.contextPath}/resources/cartas/<%=par1.getCartasJug1().get(1).getNumero()+""+par1.getCartasJug1().get(1).getPalo()%>.png" alt="" />
					</div>
					<div class="mesa"></div>
				</div>
			</div>
			<div class="carta3" id="3" onclick = "clicked('3')"> <!-- Poner en un array para mostrar en orden de tirada -->
				<div class="inner-wrap">
					<div class="mano" style= "left: 470.5px; top: 426px; z-index: 423; width: 100px; height: 100px;">
					<img src="${pageContext.request.contextPath}/resources/cartas/<%=par1.getCartasJug1().get(2).getNumero()+""+par1.getCartasJug1().get(2).getPalo()%>.png" alt="" />
					</div>
					<div class="mesa"></div>
				</div>
			</div>
	</div>
	
	


</body>
</html>