<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<button id="myButton" >descargar</button>

<script type="text/javascript">
    document.getElementById("myButton").onclick = function () {
        location.href = "./descarga/${id}";
    };
</script>


<br><br><br><br><br>


	<font size="3" face="arial">
		<h1>para</h1> <br> <br> ${correoEnvio}<br> <br> <br>
		<h1>en copia</h1> <br> <br> ${enCopia} <br> <br>
		<h3> asunto</h3> <br> <br> ${asunto} <br> <br>
		
		_______________
		 <br><br><br>
		<b> Buenos días</b> <br> <br> ${descripccion}<br> <c:if
			test="${not empty parada}">
			<c:choose>
				<c:when test="${parada eq 'si'}">
					<br>
					<font color="blue">REQUIERE PARADA</font>
				</c:when>
				<c:otherwise>
					<br>
					<font color="blue">NO REQUIERE PARADA</font>
				</c:otherwise>
			</c:choose>
		</c:if> <br> <b>DOCUMENTACION</b>
		<ul>
			<li>${documentacion}</li>
		</ul> <br> <b>MOTIVO DE LA SOLICITUD</b>
		<ul>
			<li>${motivo}</li>
		</ul><br>	
		
		
		<b>Entornos afectados</b>
		<ul>
			<li><font color="blue">${aplicacion} ${entornos}</font></li>
		</ul> <br> <b>FECHA PROPUESTA</b>
		<ul>
			<li><font color="blue">${fecha}</font></li>
		</ul>
		<br>
		
		<b>ORDEN DE TRABAJO,RFC</b>
		<ul>
			<li>se adjunta RFC en petición</li>
	<li>se crea orden en unicenter<font color="blue">
					${unicenter}</font></li>
		</ul>
	
	<c:if	test="${not empty funcionalCliente}">
		<b>Autorizacion Funcional</b>
		<ul>
			<li>${funcionalCliente}</li>
		</ul>
	
	</c:if>
		<b>RELASE NOTES</b>
		<ul>
			<li>libera orden <font color="blue">${relaseNotes} </font></li>
			
		</ul> <b>CONTACTO</b>

		<ul>
			<li>${nombre}</li>
			<ul>
				<li>telefono: ${telefono} extension:(${extension})</li>
				<li>Email: ${email}</li>
			</ul>
			<li>soporte.cibeles@salud.madrid.org</li>
		</ul>

	</font>
	
	
	
	
	
 

</body>
</html>