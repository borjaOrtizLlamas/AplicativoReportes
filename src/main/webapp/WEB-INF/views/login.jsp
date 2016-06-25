<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page session="true"%>
<html>
<head>
<title>Inicio de sesion</title>

<title>FORMULARIO - PETICIONES</title>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="<c:url value="/resources/css/bootstrapLogin.css" />"
	rel="stylesheet" type="text/css" />
	
<script src="<c:url value="/resources/js/prefixfree.min.js" />"></script>


</head>
<body id="fondoMovible" onload='document.loginForm.username.focus();' >

	<div class="container" id="login-box" align="center">
	
		<div class="row vertical-offset-100">
			<div class="col-md-4 col-md-offset-4">
				<div class="panel panel-default">
					<div class="panel-heading">    
						<div class="row-fluid user-row">
							<img src="http://s11.postimg.org/7kzgji28v/logo_sm_2_mr_1.png" class="img-responsive" alt="Conxole Admin"/>
						
						</div>
					</div>
					
		<div class="panel-body">
		<h2> APLICATIVO MEDAS - GESTOR DE INCIDENCIAS</h2>
		
		
		<form accept-charset="UTF-8" role="form" class="form-signin" name='loginForm'
		 action="<c:url value='/j_spring_security_check' />" method='POST'>
					
					
			
			

		

		 <fieldset>

		 <label class="panel-login">
		
			<c:if test="${not empty usuarioNoLogeado}">
			<div class="error">${usuarioNoLogeado}</div>
			</c:if>
			<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
			</c:if>
		
		</label>

				 <input class="form-control" placeholder='Usuario' id='usuario' name ='usuario' type='text'>
		
				 <input style="visibility:hidden"  placeholder='Contraseña' id='contrasena' type='password' name='contrasena' value="1">
			
				<br></br>
		
				<input class="btn btn-lg btn-success btn-block" name="submit" type="submit" id="login" value="ACCEDER »">
				
				 <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</fieldset>
		
		</form>
		
				
					</div>
								<a href="crearUsuario" >crear usuario</a>
				
					
				</div>
				
			</div>
		</div>
	</div>

</body>

</html>