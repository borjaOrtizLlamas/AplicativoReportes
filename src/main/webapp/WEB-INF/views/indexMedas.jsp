<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MENU-INCIDENCIAS</title>

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
			<a class="navbar-brand" href="#">Peticiones <b>MEDAS</b> lote 2</a>
		</div>

		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">

			<ul class="nav navbar-nav navbar-right">
				<li><a>Usuario: ${nombreUsuario}</a></li>
				<li><a href="aplicativo">Enviar Incidencia </a></li>
				<li><a href="anadirAplicacion">Añadir aplicacion a un usuario </a></li>
				
				<li>
					<form action="j_spring_security_logout" method="post"
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



	<!-- lista soporte -->

	<sec:authorize access="hasRole('ROLE_SOPORTE')">
		<div class="row">
			<form method="GET" action="/main/indexMedas">

				<div class="col-lg-1 col-md-1 col-sm-1"></div>
				<div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
					<select name="estado" class="form-control">
						<option value="">todas</option>
						<option value="0">pendiente de revision</option>
						<option value="1">Sin enviar</option>
						<option value="2">Por completar</option>
						<option value="3">En proceso</option>
						<option value="4">Pre-Cambio</option>
						<option value="5">cerrada</option>
					</select>
				</div>
				<div class="col-lg-2 col-md-2 col-sm-2 col-xs-12">
					<input type="submit" class="btn btn-default center-block"
						value="BUSCAR POR ESTADO" />
				</div>
			</form>

			<div class="col-lg-1 col-md-1 col-sm-1 col-xs-12"></div>
			<form method="GET" action="/main/indexMedas">
				<div class="col-lg-3 col-md-3  col-sm-3 col-xs-12">
					<input type="text" class="form-control" name="codigoUnicenter" />
				</div>
				<div class="col-lg-2 col-md-2  col-sm-2 col-xs-12">
					<input type="submit" class="btn btn-default center-block"
						value="BUSCAR POR REQ" />
				</div>
				<div class="col-lg-1 col-md-1 col-sm-1"></div>

			</form>
		</div>
		</div>
		<br><br>
		<div class="clearfix"></div>
		<div class="table-responsive">
			<table class="table table-striped table-bordered">
				<tr>
					<th>fecha creacion</th>
					<th>REQ</th>
					<th>nombre de usuario</th>
					<th>nombre aplicacion</th>
					<th>entorno aplicacion</th>
					<th>fecha entrega</th>
					<th>estado</th>
					<th></th>

				</tr>
				<c:forEach items="${incidenciasSoporte}" var="incidencia">
					<tr>

						<td>${incidencia.fechaCreacionIncidencia}</td>
						<td>${incidencia.codigoUnicenter}</td>
						<td>${incidencia.usuario.nombre}</td>
						<td>${incidencia.aplicacion}</td>
						<td>${incidencia.entorno}</td>
						<td>${incidencia.fecha}</td>
						<td>${incidencia.estadoEnum.nombre}</td>
						<td><a href="listaIncidencias/${incidencia.id}/">editar</a>| <a href="listaIncidencias/cambioEstado/${incidencia.id}/">editar estado</a> | <a
							href="listaIncidencias/cambioEstado/cerrada/${incidencia.id}"> <span
								class="glyphicon glyphicon-remove" aria-hidden="true"></span></a></td>
					</tr>
				</c:forEach>
			</table>

		</div>
	</sec:authorize>




 
	<sec:authorize access="hasRole('ROLE_USER')">

		<div class="table-responsive">
			<table class="table table-striped table-bordered">
				<tr>
					<th>fecha creacion</th>
					<th>nombre aplicacion</th>
					<th>entorno aplicacion</th>
					<th>fecha entrega</th>
					<td></td>

				</tr>

				<c:forEach items="${incidenciasUsuario}" var="incidencia">
					<tr>
						<td>${incidencia.fechaCreacionIncidencia}</td>
						<td>${incidencia.aplicacion}</td>
						<td>${incidencia.entorno}</td>
						<td>${incidencia.fecha}</td>
						<td><a href="/main/listaUsuario/${incidencia.id}/">editar</a></td>
					</tr>
				</c:forEach>
			</table>
		</div>

	</sec:authorize>






	<c:if test="${not empty insertada}">
	<div class="jumbotron text-center text-success">
		<h4>${insertada}</h4>
		</div>
	</c:if>
	
	<c:if test="${not empty editada}">
		<div class="jumbotron text-center text-success">
		${editada}
		</div>
	</c:if>


</body>
</html>