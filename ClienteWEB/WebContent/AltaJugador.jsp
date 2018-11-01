<%@page import="servlets.AltaJugadorServlet"%>
<%@page import="dto.JugadorDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="js/jquery.validate.js"></script>
<script type="text/javascript">

	$(document).ready(function() {
		$('#formregister').validate({
		rules:{
			nombre:{
				required:true,
				minlength:6,
				maxlength:50
			},
			apodo:{
				required:true,
				minlength:6,
				maxlength:50
			},
			mail:{
				required:true,
				email:true
			},
			pass:{
				required:true,
				minlength:6,
				maxlength:50
			},
	 	 }
		});
	});

</script>
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>TRABAJO PRACTICO</title>
<link rel="shortcut icon" href="favicon.ico?" type="image/x-icon" />
<link rel="icon" type="image/png" sizes="32x32" href="favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="favicon-16x16.png">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"></link>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/styles.css"></link>
</head>

<body>
		<form method="post" action="AltaJugadorServlet" id="formregister">
			<h2 class="brand-text text-center"><center>Truco Online - Alta Jugador</center></h2>
			<br>
			<center>
				<table cellspacing="2" cellpadding="3">
					<tr>
						<td>Nombre</td>
						<td><input type="text" name="nombre"></td>
					</tr> 
					<tr>
						<td>Apodo</td>
						<td><input type="text" name="apodo"></td>
					</tr> 
					<tr>
						<td>Email</td>
						<td><input type="text" name="mail"></td>
					</tr> 
					<tr>
						<td>Contraseña</td>
						<td><input type="text" name="pass"></td>
					</tr> 
					<tr>
						<td>&nbsp;</td>
						<td><input type="submit" value="guardar"></td>
					</tr>
					</center> 
				</table>
			</form>
					<%
    		 if(null!=request.getAttribute("errorMessage"))
    		{
      		 out.println("<script>alert('Apodo y/o mail existente');</script>");
      		 }
			%>
</body>
<style>
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</html>