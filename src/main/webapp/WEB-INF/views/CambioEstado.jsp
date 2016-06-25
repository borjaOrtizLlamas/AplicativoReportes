<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<link href="<c:url value="/resources/css/estiloIncidencia.css" />"
	rel="stylesheet" type="text/css" />


<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet" type="text/css" />
<script src="<c:url value="/resources/js/typeahead.js" />"></script>

<script src="<c:url value="/resources/js/bootstrap.js" />"></script>

<link
	href="<c:url value="/resources/css/estiloIncidenciasBootstrap.min.css" />"
	rel="stylesheet" type="text/css" />

<link href="<c:url value="/resources/css/fuente.min.css" />"
	rel="stylesheet" type="text/css" />

</head>
<body>

	<nav class="navbar navbar-default">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
				aria-expanded="false">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="/main/">Peticiones <b>MEDAS</b> lote 2</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a>Usuario: ${nombreUsuario}</a></li>
				<li><a href="/main/aplicativo">Enviar Incidencia </a></li>
				<li><a href="/main/anadirAplicacion">Añadir aplicacion a un usuario </a></li>
				
				<li>
					<form action="/main/j_spring_security_logout" method="post"
						id="logoutForm">
						<input class="btn btn-default btn-sm" name="submit" type="submit"
							value="SALIR/CERRAR SESIÓN" /> <input type="hidden"
							name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
				</li>
			</ul>
		</div>
	</div>
	</nav>


	<form:form method="POST" commandName="modelo" action="../">

		<form:input path="id" readonly="true" cssStyle="visibility: hidden;" />
		<br>
		<div class=" jumbotron">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
				<select name="estado" class="form-control center-block">
					<option value="0">pendiente de revision</option>
					<option value="1">Sin enviar</option>
					<option value="2">Por completar</option>
					<option value="3">En proceso</option>
					<option value="4">Pre-Cambio</option>
					<option value="5">cerrada</option>
					<option value="666">borrar esta incidencia de bbdd</option>
					
				</select>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6">
				<input type="submit" value="editar" class=" btn btn-default" />
			</div>
			<div class="clearfix"></div>
		</div>

	</form:form>
</body>
</html>