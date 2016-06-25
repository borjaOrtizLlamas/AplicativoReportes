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
<h2 class="text-center">crear usuarios</h2>

	<form:form method="POST" commandName="modelo"
		action="/main/crearUsuario">
		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<form:label path="usuario" >usuario</form:label>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<form:input path="usuario" class="form-control" />
			</div>
		</div>

		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<form:label path="telefono" >telefono</form:label>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<form:input path="telefono" class="form-control" />
			</div>
		</div>

		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<form:label path="extension" >extension
				</form:label>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<form:input path="extension" class="form-control" />
			</div>
		</div>


		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<form:label path="movil" >movil
		</form:label>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">

				<form:input path="movil" class="form-control" />
			</div>
		</div>




		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<form:label path="nombre" >nombre
		</form:label>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<form:input path="nombre" class="form-control" />
			</div>
		</div>

		<div class="row">
			<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
				<form:label path="email" >email
		</form:label>
			</div>
			<div class="col-lg-10 col-md-10 col-sm-10 col-xs-10">
				<form:input path="email" class="form-control" />
			</div>
		</div>
		<div class="row">

			<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
				<input class="btn btn-lg btn-success center-block" name="submit"
					type="submit" id="login" value="insertar">
			</div>
		</div>
	</form:form>
</body>
</html>