package com.connectis.main;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.connectis.dao.AplicacionesDAOIntf;
import com.connectis.model.AnadirUsuarioAplicacionModel;
import com.connectis.model.Aplicaciones;
import com.connectis.model.Estados;
import com.connectis.model.Peticiones;
import com.connectis.model.UsuariosMedas;
import com.connectis.services.IncidenciasServicio;
import com.connectis.services.ServicioAplicaciones;
import com.connectis.services.ServicioUsuarios;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	@Autowired
	ServicioAplicaciones servicioAplicaciones;

	@Autowired
	ServicioUsuarios usuarios;

	@Autowired
	IncidenciasServicio incidenciasServicio;


	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login(
			@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout,
			HttpServletRequest request) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return new ModelAndView("redirect:indexMedas");
		} else {
			ModelAndView model = new ModelAndView();
			if (error != null) {
				model.addObject(
						"usuarioNoLogeado",
						getErrorMessage(request,
								"SPRING_SECURITY_LAST_EXCEPTION"));
			}
			if (logout != null) {
				model.addObject("msg", "has salido satisfactoriamente.");
			}
			model.setViewName("login");
			return model;
		}
	}
	
	
	

	@RequestMapping(value = "/indexMedas", method = RequestMethod.GET)
	public ModelAndView indexMedas(
			@RequestParam(value = "insertado", required = false) String insertada,
			@RequestParam(value = "editado", required = false) String editada,
			@RequestParam(value = "estado", required = false) String estado,
			@RequestParam(value = "codigoUnicenter", required = false) String codigoUnicenter,
			HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		if (insertada != null) {
			model.addObject("insertada", "incidencia creada satisfactoriamente.");
		}
		if (editada != null) {
			model.addObject("editada", "ha sido editada satisfactoriamente.");
		}
		model.setViewName("indexMedas");
		String usuarioLogeado = nombreLogeado();
		model.addObject("nombreUsuario",usuarioLogeado);
		
		UserDetails usuario = usuario(); 
		boolean usuarioAplicativo = false; 
		boolean soporte =  false; 
		
		
		for (GrantedAuthority rol : usuario.getAuthorities()) {
			if(rol.getAuthority().contains("ROLE_SOPORTE")) soporte = true; 
			if(rol.getAuthority().contains("ROLE_USER")) usuarioAplicativo = true; 
		}
		
		
		if(usuarioAplicativo) model.addObject("incidenciasUsuario", incidenciasServicio.incidenciasPorUsuario(usuario.getUsername())); 

		if(soporte){
			List<Peticiones> b;
			if (estado != null && !estado.equals("")) {
				Estados a = Estados.estadoSegunId(estado);
				b = incidenciasServicio.busquedaIncidencias(a);
			} else if (codigoUnicenter != null) {
				b = incidenciasServicio.busquedaIncidencias(codigoUnicenter);
			} else {
				b = incidenciasServicio.todasLasIncidencias();
			}
			model.addObject("incidenciasSoporte", b);
		} 
		return model;
	}

	
	private UserDetails usuario(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		return userDetail; 
	}

	
	private String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession()
				.getAttribute(key);

		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = "introduzca correo y contraseña valido!";
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else {
			error = "introduzca correo y contraseña valido!";
		}

		return error;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();
		model.setViewName("403");
		return model;

	}

	@RequestMapping(value = "/anadirAplicacion", method = RequestMethod.GET)
	public ModelAndView anadirUsuario() {
		ModelAndView model = new ModelAndView();
		
		List<String> usuarios = new ArrayList<String>();
		List<UsuariosMedas> usuariosMedas = this.usuarios.listaUsuarios(); 

		for(UsuariosMedas usuario : usuariosMedas){
			usuarios.add(usuario.getUsuario());
		}
		
		String usuarioLogeado = nombreLogeado();
		model.addObject("nombreUsuario",usuarioLogeado);
		model.addObject("usuarios",usuarios);
		model.addObject("aplicaciones",servicioAplicaciones.todosLosNombres());
		model.addObject("modelo", new AnadirUsuarioAplicacionModel());
		model.setViewName("anadirAplicacionUsuario");
		return model;

	}
	
	@RequestMapping(value = "/anadirAplicacion", method = RequestMethod.POST)
	public String anadirUsuarioPOST(@ModelAttribute("AnadirUsuarioAplicacionModel") AnadirUsuarioAplicacionModel anadir) {
		usuarios.anadirAplicacionUsuario(anadir.getUsuario(), servicioAplicaciones.unaAplicacion(anadir.getAplicacion()).getId());
		return "redirect:/?editado";

	}
	
	@RequestMapping(value = "/crearUsuario", method = RequestMethod.GET)
	public ModelAndView crearUsuarioGET() {
		ModelAndView model = new ModelAndView();
		model.addObject("modelo", new UsuariosMedas());
		model.setViewName("crearUsuario");
		return model;
	}
	
	@RequestMapping(value = "/crearUsuario", method = RequestMethod.POST)
	public String crearUsuario(@ModelAttribute("usuario") UsuariosMedas usuario) {
		usuario.setContrasena("" + 1);
		usuario.setValido(1);
		usuarios.crearUsuario(usuario);
		return "redirect:/?creado";

	}

	private String nombreLogeado(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
		return userDetail.getUsername(); 
	}

}
