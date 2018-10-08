<%@page import="servlets.LoginServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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

<body class="bg-secondary">

	<section id="main"
		class="container-fluid d-flex justify-content-center align-items-center col-12 col-md-8">
		<div class="login-box bg-dark mt-4 text-light">
			<h1 class="brand-text text-center">Truco Online</h1>
			<form name="login" action="LoginServlet" method="post">
				<div class="input-group mb-3 mt-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="basic-addon1">Usuario</span>
					</div>
					<input type="text" class="form-control" placeholder="Ingrese usuario..."
						name="usuario" aria-label="Usuario"
						aria-describedby="basic-addon1">
				</div>

				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="basic-addon2">Contraseña</span>
					</div>
					<input type="password" class="form-control" placeholder="Ingrese contraseña..."
						name="password" aria-label="Contraseña" aria-describedby="basic-addon2">
				</div>
				<div class="container d-flex justify-content-center">
					<input type="submit" value="Log in" class="btn btn-secondary w-100" />
				</div>
			</form>
		</div>
	</section>
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
<style>
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-3.3.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/bootstrap.bundle.min.js"></script>
</html>