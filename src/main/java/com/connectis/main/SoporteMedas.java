package com.connectis.main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.BindException;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.connectis.model.Estados;
import com.connectis.model.ModeloCambio;
import com.connectis.model.Peticiones;
import com.connectis.model.UsuariosMedas;
import com.connectis.services.GeneraArchivoWord;
import com.connectis.services.IncidenciasServicio;
import com.connectis.services.JavaMail;
import com.connectis.services.ServicioAplicaciones;
import com.connectis.services.ServicioUsuarios;

@Controller
@RequestMapping("/listaIncidencias/")
public class SoporteMedas {

	@Autowired
	ServicioAplicaciones servicioAplicaciones;

	@Autowired
	ServicioUsuarios usuarios;

	@Autowired
	IncidenciasServicio incidenciasServicio;


	
	@RequestMapping(value = "/{id}/", method = RequestMethod.GET)
	public ModelAndView incidencia(@PathVariable("id") Integer id)
			throws IOException {
		ModelAndView model = new ModelAndView();
		incidenciasServicio.generarModeloEditable(model, id,
				"incidenciaSoporte", false);
		return model;
	}

	@RequestMapping(value = "/descarga", method = RequestMethod.POST)
	public void getFile(@ModelAttribute("Incidencia") Peticiones incidencia,
			HttpServletResponse response) throws Exception {
		guardadoIncidencia(incidencia);
		incidenciasServicio.descargaIncidencia(incidencia, response);
	}

	@RequestMapping(value = "/descarga/{id}", method = RequestMethod.GET)
	public void getFileGET(@PathVariable("id") Integer id,
			HttpServletResponse response) throws Exception {
		Peticiones i = incidenciasServicio.incidencia(id);
		incidenciasServicio.descargaIncidencia(i, response);
	}

	@RequestMapping(value = "/{id}/borrar/{nombreArchivo}.{extension}", method = RequestMethod.GET)
	public String borrar(@PathVariable("id") Integer id,
			@PathVariable("nombreArchivo") String nombreArchivo,
			@PathVariable("extension") String extension) throws Exception {
		incidenciasServicio.borrado(id, nombreArchivo, extension);
		return "redirect:/listaIncidencias/" + id + "/";
	}

	@RequestMapping(value = "/editarFormulario", method = RequestMethod.POST)
	public String incidenciaEditado(
			@ModelAttribute("Incidencia") Peticiones incidencia)
			throws IOException {
		incidencia.setEstadoEnum(Estados.PORCOMPLETAR);
		guardadoIncidencia(incidencia);
		return "redirect:/?editado";
	}

	@RequestMapping(value = "/aplicativoSoporte", method = RequestMethod.POST)
	public ModelAndView soporte(
			@ModelAttribute("Incidencia") Peticiones formulario,
			BindException errors, HttpServletResponse response)
			throws IOException {
		formulario.setEstadoEnum(Estados.ENPROCESO);
		guardadoIncidencia(formulario);
		ModelAndView model = new ModelAndView();
		incidenciasServicio.mostrarCorreo(model, formulario);
		model.addObject("id", "" + formulario.getId());
		return model;
	}

	@RequestMapping(value = "/cambioEstado/cerrada/{id}", method = RequestMethod.GET)
	public String cambioEstadoaBorrada(@PathVariable("id") Integer id)
			throws IOException {
		Peticiones a = incidenciasServicio.incidencia(id);
		if (a.getEstadoEnum().getId() == 5 ) {
		} else
			incidenciasServicio.modificarEstadoIncidencia(Estados.CERRADA, id);
		return "redirect:/?editado";
	}

	@RequestMapping(value = "/cambioEstado/pre-cambio/{id}", method = RequestMethod.GET)
	public String cambioEstadoPrecambio(@PathVariable("id") Integer id)
			throws IOException {
		incidenciasServicio.modificarEstadoIncidencia(Estados.PRECAMBIO, id);
		return "redirect:/?editado";
	}

	@RequestMapping(value = "/cambioEstado/{id}", method = RequestMethod.GET)
	public ModelAndView cambioEstado(@PathVariable("id") Integer id)
			throws IOException {
		String usuarioLogeado = nombreLogeado();

		ModelAndView a = new ModelAndView();
		a.addObject("nombreUsuario",usuarioLogeado);

		a.addObject("modelo", new ModeloCambio(id));
		a.setViewName("CambioEstado");
		return a;
	}

	@RequestMapping(value = "/cambioEstado/", method = RequestMethod.POST)
	public String soporte(@ModelAttribute("modelo") ModeloCambio estado)
			throws IOException {
		if(estado.getEstado() != 666){
		Estados a = Estados.estadoSegunId(estado.getEstado() + "");
		incidenciasServicio.modificarEstadoIncidencia(a, estado.getId());
		} else {
			Peticiones a = incidenciasServicio.incidencia(estado.getId());
			incidenciasServicio.borrarIncidencia(a);
		}
		return "redirect:/?editado";
	}

	private void guardadoIncidencia(Peticiones incidencia) {
		incidencia.setAplicacionObjeto(servicioAplicaciones
				.unaAplicacion(incidencia.getAplicacion()));
		incidencia.setUsuario(usuarios.devolverUsuario(incidencia
				.getNombreUsuario()));
		creaccionCorreos(incidencia);
		incidenciasServicio.modificarIncidencia(incidencia);
		incidenciasServicio.subirArchivos(incidencia.getArchivos(),
				incidencia.getId());

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
	
	private String nombreLogeado(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		return userDetail.getUsername(); 
	}


}
