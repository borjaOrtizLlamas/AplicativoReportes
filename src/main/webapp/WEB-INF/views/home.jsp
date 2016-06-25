
EDITAR USUARIO - JSP
Victor Lopez Diaz
Hoy 17:20Borja Ortiz Llamas

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="true"%>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<head>

<meta charset="utf-8">
<title>FORMULARIO - PETICIONES</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


<link rel="stylesheet"
    href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>

<link href="<c:url value="/resources/css/bootstrap.css" />"
    rel="stylesheet" type="text/css" />

<script src="<c:url value="/resources/js/bootstrap.js" />"></script>

<script src="<c:url value="/resources/js/datepicker.js" />"
    charset="UTF-8"></script>

<script src="<c:url value="/resources/js/scriptHome.min.js" />"
    charset="UTF-8"></script>
    
    <script src="<c:url value="/resources/js/typeahead.bundle.js" />"
    charset="UTF-8"></script>
    
<link href="<c:url value="/resources/css/tagmanager.css" />"
    rel="stylesheet" type="text/css" />
    
<script src="<c:url value="/resources/js/tagmanager.js" />"
    charset="UTF-8"></script>

<script src="<c:url value="/resources/js/correos.js" />"
    charset="UTF-8"></script>



</head>
<body onload="creacionSpans()">


<div class="container-fluid">



<h1>Formulario Peticiones MEDAS</h1>

<hr class="barraTitulo">



<!-- </br></br> -->


            <div class="col-sm-6 col-lg-10">
             <img src="/main/resources/imagenes/connectis.jpg" class="img-responsive img-rounded">

          </div>
           <div class="col-sm-6 col-lg-2" id="bordeImagenes">
             <img src="/main/resources/imagenes/salud.png" class="img-responsive img-rounded">

          </div>
          
          
       

</br></br></br>


<form:form method="POST" commandName="Incidencia"
                        action="../actualizar?${_csrf.parameterName}=${_csrf.token}"
                        enctype="multipart/form-data" modelattribute="Peticion" class="form-horizontal" role="form">





<div class="col-sm-6 col-lg-4 celda columna1">
        <div class="form-group" >
          <form:label  path="entorno" class="col-md-2 control-label titulos">
          Entorno:
          </form:label>
         
           <div class="col-md-8">
            <form:radiobuttons  path="entorno" items="${entorno}" />
          </div>
        </div>
</div>

<div class="col-sm-6 col-lg-3 celda columna1">
        <div class="form-group">
          <form:label  path="aplicacion" class="col-md-4 control-label titulos">
          Aplicación:
          </form:label>
         
           <div class="col-md-8">
            <form:select  path="aplicacion" items="${aplicaciones}" />
          </div>
        </div>
</div>

<div class="col-sm-6 col-lg-2 celda columna1" id="altura1">
        
          <form:label  path="bbdd" class="titulos">
          BBDD:</form:label>
          <form:checkbox path="bbdd" />
          
         
          
           <form:label path="aplicacionIN" class="titulos">
           Aplicación</form:label>
        <form:checkbox path="aplicacionIN" />
         
    
</div>



<div class="col-sm-6 col-lg-3 celda columna1">
        <div class="form-group">
          <form:label  path="listaPorDefecto" class="col-md-4 control-label titulos">
          Autorizador:
          </form:label>
         
           <div class="col-md-8">
            <form:select  path="listaPorDefecto" id="listaPorDefecto" items="${autorizadoras}" onclick="apareceAutorizante()"/>
          </div>
        </div>
</div>


<div class="col-sm-6 col-lg-4 celda2 columna1">
        <div class="form-group">
          <form:label  path="radioFecha" class="col-md-2 control-label titulos">
         Fecha:
          </form:label>
        
        
        <div class="col-md-1">
          <form:radiobutton id="botonFecha" path="radioFecha"  value="fecha" onclick="actualizarPicker()"   />
        </div>
          
         
           <div class="col-md-2">
            <form:input path="fechaDatePiker" onfocus="actualizarPicker()"
            onchange="actualizarHora()" disabled="true" />
          </div>
        </div>
</div>





<div class="col-sm-6 col-lg-3 text-center celda2 columna1" id="altura2">
        <div class="form-group">
            <form:label  path="fechaComentario" class="col-md-4 control-label titulos">
         Comentario:
          </form:label>
        
        
        <div class="col-md-1">
         <form:radiobutton id="botonFecha2" path="radioFecha"
        value="comentario" onclick="actualizarComentario()"
        checked="checked" />
        
        </div>
          
         
           <div class="col-md-1">
            <form:input path="fechaComentario" disabled="false"
            onfocus="actualizarComentario()"
            placeholder="Introduce un comentario" value="Lo antes posible" />
          </div>
          </div>
</div>


<div class="col-sm-6 col-lg-2 celda2 text-center columna1" id="altura2">
        <div class="form-group ">
          <div class="col-md-12">
             <form:checkbox  id="botonUrgente" path="urgente" value="URGENTE" onclick="actualizarUrgente()" />
          <form:label path="radioFecha" id="urge">URGENTE</form:label>
            
          </div>
        </div>
</div>


<div class="col-sm-6 col-lg-3 celda2 columna1" id="altura2" >
        <div class="form-group">
        <div class="col-md-6">
          <form:input  path="autorizador" id="textUsuario" placeholder="Autorizador" style="visibility:hidden" class="form-control"/>
        </div>
           
           <div class="col-md-6">
             <form:input  path="correoAutorizador" id="textCorreo" placeholder="Correo" style="visibility:hidden" class="form-control"/>
          </div>
        </div>
</div>

      

<div class="col-sm-6 col-lg-4 celda columna1" id="altura4" >
        <div class="form-group">
        
         <form:label  path="requiereParada" class="col-md-5 control-label titulos">
          ¿Requiere parada?
          </form:label>
         
           <div class="col-md-5">
            <form:radiobuttons path="requiereParada" items="${requiereParada}" />
          </div>
        </div>
</div>


    

<div class="col-sm-6 col-lg-3 celda columna1" id="altura4" >
        <div class="form-group">
        
      <div class="col-md-12">
            <form:input class="form-control" type="text" path="relaseNotes" placeholder="Release Notes" />
            
          </div>
        </div>
</div>


<div class="col-sm-6 col-lg-5 celda columna1" id="altura3">
        <div class="form-group ">
          <div class="col-md-12">
            <input name="archivos[0]" type="file">
            <input name="archivos[1]" type="file">
                      
            
            <c:if test="${not empty ficheros}">
                                    <c:forEach items="${ficheros}" var="fichero">
                                            nombre archivo => ${fichero} <a href="borrar/${fichero}">borrar</a>
                                        <br>
                                    </c:forEach>

                                </c:if>
            
            
          </div>
        </div>
</div>


<div class="col-sm-6 col-lg-7 celda2 columna1">
 
  <div class="form-group celda2">
              <form:label path="asunto" class="col-md-3 control-label titulos">Asunto del correo</form:label>
              <div class="col-md-9">
                     <form:textarea id="asunto" path="asunto" placeholder="Asunto del correo." class="form-control" rows="1" />
              </div>
   </div>
 
 
 </div>


<div class="col-sm-6 col-lg-12 celda columna1">

<div class="form-group celda">
              <form:label path="correosAnadidos" class="col-md-2 control-label titulos" >Añadir Correos:</form:label>
           
           
              <div class="col-md-10">
              <form:input path="correosAnadidos" id="sizeCorreo" placeholder="Añada correos." class="tm-input form-control"/>
                                <script type="text/javascript">jQuery(".tm-input").tagsManager();</script>           

              </div>
                 
              
            </div>
   
</div>


<div class="col-sm-6 col-lg-12 celda2 columna1">

<div class="form-group celda2">
              <form:label path="descripccion" class="col-md-2 control-label titulos">Descripción:</form:label>
              <div class="col-md-10">
                <form:textarea path="correosAnadidos" placeholder="Introduzca la descripción."  class="form-control" rows="4" />
              </div>
            </div>

</div>


<div class="col-sm-6 col-lg-12 celda columna1" >

<div class="form-group celda">
              <form:label path="documentacion" class="col-md-2 control-label titulos">Documentación:</form:label>
              <div class="col-md-8">
                <form:textarea path="documentacion" placeholder="Introduzca la url de la documentación."  class="form-control" rows="1" />
              </div>
            </div>

</div>


<div class="col-sm-6 col-lg-12 celda2 columna1">

<div class="form-group celda2">
              <form:label path="motivo" class="col-md-2 control-label titulos">Motivo:</form:label>
              <div class="col-md-8">
                <form:textarea path="motivo" placeholder="Introduzca el motivo de la solicitud."  class="form-control" rows="3"/>
              </div>
            </div>

</div>



<div class="col-sm-6 col-lg-7 celda columna1">

<div class="form-group celda">
              <form:label path="descripcionDOC" class="col-md-3 control-label titulos">DESCRIPCIÓN DEL CAMBIO:</form:label>
              <div class="col-md-9">
                <form:textarea path="descripcionDOC" placeholder="Describe el cambio que ha realizado."  class="form-control" rows="4" />
              </div>
            </div>

</div>


<div class="col-sm-6 col-lg-5 columna1 celda5" id="altura5">

<div class="form-group">

<div class="col-md-2"></div>

<div class="col-md-3" id="borderTop">
<input type="submit" class="btn btn-primary"  value="ENVIAR" >
</div>

<div class="col-md-3" id="borderTop">
<input type="submit" value="Descargar" formaction="descarga?${_csrf.parameterName}=${_csrf.token}" class="btn btn-warning">
</div>

<div class="col-md-2" id="borderTop">
<a href="../" role="button" class="btn btn-success">SALIR</a>
</div>

<div class="col-md-2"></div>
      
</div>


       
</div>







</form:form>


</div>



<script type="text/javascript">jQuery(".tm-input").tagsManager();</script>



</body>
</html>

 
