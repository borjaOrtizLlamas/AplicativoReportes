package com.connectis.main;

import java.io.File;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;

import com.connectis.dao.AplicacionesDAOIntf;
import com.connectis.model.Estados;
import com.connectis.model.Peticiones;
import com.connectis.services.IncidenciasServicio;
import com.connectis.services.ServicioAplicaciones;
import com.connectis.services.ServicioUsuarios;

/*
 * este controlador se encarga de insertar las incidencias en la base de datos
 * 
 * @author borja
 *
 *
 */

@Controller
public class UsuariosControlador {

	@Autowired
	ServicioAplicaciones servicioAplicaciones;

	@Autowired
	IncidenciasServicio incidenciasServicio;

	@Autowired
	ServicioUsuarios usuarios;

	@RequestMapping(value = "/aplicativo", method = RequestMethod.GET)
	public ModelAndView formularioUsuarios() throws IOException {
		ModelAndView model = new ModelAndView();

		List<String> requiereParada = new ArrayList<String>();

		requiereParada.add("Si");
		requiereParada.add("No");
		requiereParada.add("Nada");

		model.addObject("requiereParada", requiereParada);

		List<String> radioButton = new ArrayList<String>();

		radioButton.add("Producci�n");
		radioButton.add("Pre-Producci�n");

		model.addObject("entorno", radioButton);

		List<String> autorizadoras = new ArrayList<String>();

		autorizadoras.add("M�nica D�az");
		autorizadoras.add("Yolanda Llamas Nistal");
		autorizadoras.add("OTRO");

		model.addObject("autorizadoras", autorizadoras);

		Peticiones a = new Peticiones();

		a.setRadioFecha("fecha");
		a.setEntorno("Producci�n");
		a.setRequiereParada("Nada");
		model.addObject("incidencia", a);

		List<String> as = servicioAplicaciones.todosLosNombres(nombre());

		model.addObject("aplicaciones", as);

		model.setViewName("home");
		return model;
	}
	
	
	@RequestMapping(value = "/listaUsuario/", method = RequestMethod.GET)
	public ModelAndView listaUsuario() throws IOException {
		ModelAndView model = new ModelAndView();
		model.setViewName("listaUsuario");
		return model; 
	}
	
	@RequestMapping(value = "/listaUsuario/{id}", method = RequestMethod.GET)
	public ModelAndView listaUsuarioId(@PathVariable("id") Integer id) throws IOException {
		ModelAndView model = new ModelAndView();
		incidenciasServicio.generarModeloEditable(model, id, "EditarPorUsuario", true);
		return model; 
	}
	
	
	@RequestMapping(value = "/listaUsuario/{id}/descarga", method = RequestMethod.POST)
	public void getFileGET(@PathVariable("id") Integer id,	HttpServletResponse response) throws Exception {
		Peticiones i = incidenciasServicio.incidencia(id); 
		incidenciasServicio.descargaIncidencia(i,response); 
	}	
	
	
	@RequestMapping(value = "/listaUsuario/descarga", method = RequestMethod.POST)
	public void edicion2(@ModelAttribute("Peticion") Peticiones formulario,HttpServletResponse response) throws IOException {
		guardadoUsuarios(formulario);
		incidenciasServicio.descargaIncidencia(formulario,response); 
	}
	
	@RequestMapping(value = "/listaUsuario/{id}/borrar/{nombreArchivo}.{extension}", method = RequestMethod.GET)
	public String borrar(@PathVariable("id") Integer id, @PathVariable("nombreArchivo") String nombreArchivo, @PathVariable("extension") String extension) throws Exception {
		incidenciasServicio.borrado(id, nombreArchivo , extension);
		return "redirect:/listaUsuario/" + id + "/"; 
	}
	
	@RequestMapping(value = "/listaUsuario/actualizar", method = RequestMethod.POST)
	public String edicion(@ModelAttribute("Peticion") Peticiones formulario) throws IOException {
		guardadoUsuarios(formulario);
		
		return "redirect:/indexMedas?editar";
	}

	@RequestMapping(value = "/guardarIncidencia", method = RequestMethod.POST)
	public String guardarIncidencia(@ModelAttribute("Formulario") Peticiones formulario) throws IOException {
		System.out.println("EL CORREO SALE COMO " + formulario.getCorreosAnadidos());
		formulario.setEstadoEnum(Estados.SINENVIAR);		
		formulario.setAplicacionObjeto(servicioAplicaciones.unaAplicacion(formulario.getAplicacion()));
		formulario.setUsuario(usuarios.devolverUsuario(nombre()));
		incidenciasServicio.guardarIncidencia1(formulario);
		incidenciasServicio.subirArchivos(formulario.getArchivos(), formulario.getId());
		return "redirect:/indexMedas?insertado";
	}


	
	private String nombre(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		return userDetail.getUsername(); 
	}
	
	private void creaccionCorreos(Peticiones incidencia) {
		String enviarA = "";
		if (incidencia.isAplicacionIN())
			enviarA += "aplicaciones.cedas@salud.madrid.org;";
		if (incidencia.isBbdd())
			enviarA += "bbdd.cedas@salud.madrid.org;";
		incidencia.setCorreosEnvio(incidencia.getUsuario().getEmail() + ";"
				+ enviarA);
		String correosEnCopiaEnvio = correosEnCC(incidencia);
		incidencia.setCorreosEnCopiaEnvio(correosEnCopiaEnvio);
	}

	public String correosEnCC(Peticiones incidencia) {
		String correosEnCopiaEnvio = "";
		correosEnCopiaEnvio = "cedas@salud.madrid.org"
				+ ";soporte.cibeles@salud.madrid.org;"
				+ "oficina.medas@salud.madrid.org;"
				+ incidencia.getCorreoAutorizador() + ";";
		if (incidencia.getAplicacionObjeto().getWindows() == 1)
			correosEnCopiaEnvio += "microsoft.cedas@salud.madrid.org;";
		System.out.println("AQUI " + incidencia.getCorreosAnadidos());
		if (incidencia.getCorreosAnadidos() != null)
			correosEnCopiaEnvio += incidencia.getCorreosAnadidos();
		return correosEnCopiaEnvio;
	}

	
	private void guardadoUsuarios(Peticiones peticion){
		peticion.setAplicacionObjeto(servicioAplicaciones.unaAplicacion(peticion.getAplicacion()));
		peticion.setUsuario(usuarios.devolverUsuario(peticion.getNombreUsuario()));
		creaccionCorreos(peticion);
		incidenciasServicio.actualizacionDeUsuario(peticion);
		incidenciasServicio.subirArchivos(peticion.getArchivos(),peticion.getId());
	}
	
}
