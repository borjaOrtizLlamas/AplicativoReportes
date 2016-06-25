$('#addFile').click(function() {
    $('#filesContainer').append(
        $('<input/>').attr('type', 'file').attr('name', 'someName')
    );
});


var hola = ""; 

function foo() {
	$(".tm-tag").each(function(i){
	      var correo = quitarX($(this).text());
	      
	      var valorInput = $('input:text[name=correosAnadidos]').val(); 
	      if(valorInput !=  ""){
	      $('input:text[name=correosAnadidos]').val(valorInput + ";" + correo);
	      } else {
		     $('input:text[name=correosAnadidos]').val(correo);
	      }
	});
}

function creacionSpans() {
	var c = $('input:text[name=correosAnadidos]').val(); 
	var correos =  c.split(";");
	for (var i=0; i < correos.length -1; i++) {
		var a = i + 1; 
		$("#divCorreos").prepend("<span class=\"tm-tag\" id=\"ncVoN_"+ a  +"\"><span>"+ correos[i]+ "</span><a href=\"#\" class=\"tm-tag-remove\" id=\"ncVoN_Remover_"+ a +"\" tagidtoremove=\"1\" onclick=\"borrarSpans('ncVoN_"+ a +"')\" >x</a></span>");
	}
	
	 $('input:text[name=correosAnadidos]').val(""); 
}

function borrarSpans(id){
	 $("#"+id).remove();
}




function fooMedas() {
	
   var hijosCorreosEnvio = $("div#divCorreosEnvio").children("span");
   
   
	$(hijosCorreosEnvio).each(function(i){
		var correo = quitarX($(this).text());
	      var valorInput = $('input:text[name=correosEnvio]').val(); 
	      if(valorInput !=  ""){
	      $('input:text[name=correosEnvio]').val(valorInput + ";" + correo);
	      } else {
		     $('input:text[name=correosEnvio]').val(correo);
	      }
	});
	
	$(hijosCorreosEnCopia).each(function(i){
		var correo = quitarX($(this).text());
	      var valorInput = $('input:text[name=correosEnCopiaEnvio]').val(); 
	      if(valorInput !=  ""){
	      $('input:text[name=correosEnCopiaEnvio]').val(valorInput + ";" + correo);
	      } else {
		     $('input:text[name=correosEnCopiaEnvio]').val(correo);
	      }
	});
   
}



function creacionSpansMedas() {
	var c = $('input:text[name=correosEnCopiaEnvio]').val(); 
	var correos =  c.split(";");
	for (var i=0; i < correos.length -1; i++) {
		var a = i + 1; 
		$("#divCorreosEnCopia").prepend("<span class=\"tm-tag\" id=\"ncVoN_"+ a  +"\"><span>"+ correos[i]+ "</span><a href=\"#\" class=\"tm-tag-remove\" id=\"ncVoN_Remover_"+ a +"\" tagidtoremove=\"1\" onclick=\"borrarSpans('ncVoN_"+ a +"')\" >x</a></span>");
	}
	
	 $('input:text[name=correosEnCopiaEnvio]').val(""); 
		var c = $('input:text[name=correosEnvio]').val(); 
		var correos =  c.split(";");
		for (var i=0; i < correos.length -1; i++) {
			var a = i + 1; 
			$("#divCorreosEnvio").prepend("<span class=\"tm-tag\" id=\"ncVoN2_"+ a  +"\"><span>"+ correos[i]+ "</span><a href=\"#\" class=\"tm-tag-remove\" id=\"ncVoN2_Remover_"+ a +"\" tagidtoremove=\"1\" onclick=\"borrarSpans('ncVoN2_"+ a +"')\" >x</a></span>");
		}
		 $('input:text[name=correosEnvio]').val(""); 
}


function quitarX(x) {
	var datos = x.split(""); 
	var c = ""; 
	for(var i=0; i < datos.length - 1; i++){
		c = c + datos[i]; 
	}
	return c; 
}









