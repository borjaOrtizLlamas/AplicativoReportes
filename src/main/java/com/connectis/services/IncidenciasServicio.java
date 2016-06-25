package com.connectis.services;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.connectis.dao.IncidenciasDAO;
import com.connectis.model.Estados;
import com.connectis.model.Peticiones;

public class IncidenciasServicio {

	IncidenciasDAO incidenciasDAOImpl;
	ServicioAplicaciones servicioAplicaciones;
	GeneraArchivoWord generaArchivoWorld;
	
	private String correo;

	public void guardarIncidencia(Peticiones incidencia) {
		incidenciasDAOImpl.guardarIncidencia(incidencia);
	}

	
	public void borrarIncidencia(Peticiones incidencia) {
		incidenciasDAOImpl.borrarIncidencia(incidencia);
	}


	public List<Peticiones> todasLasIncidencias() {
		return incidenciasDAOImpl.todasLasIncidencias();
	}

	public List<Peticiones> busquedaIncidencias(Estados estado) {
		String busqueda = " estado = " + estado.getId() + " "; 
		return incidenciasDAOImpl.todasLasIncidencias(busqueda);
	}
	
	public List<Peticiones> busquedaIncidencias(String codigoUnicenter) {
		String busqueda = " CODIGO_UNICENTER = '" + codigoUnicenter + "' "; 
		return incidenciasDAOImpl.todasLasIncidencias(busqueda);
	}

	
	public Peticiones incidencia(Integer id) {
		return incidenciasDAOImpl.unaIndicenciaIncidencias(id);
	}

	public void modificarIncidencia(Peticiones incidencia) {
		incidenciasDAOImpl.modificarIncidencia(incidencia);
	}
	
	public void correosPorAjax(int incidencia) {
		incidenciasDAOImpl.correosIncidencia(incidencia);
	}

	
	public void actualizacionDeUsuario(Peticiones incidencia) {
	
		Peticiones a = incidenciasDAOImpl.unaIndicenciaIncidencias(incidencia.getId()); 
		incidencia.setCodigoUnicenter(a.getCodigoUnicenter());
		incidencia.setFechaCreacionIncidencia(a.getFechaCreacionIncidencia());
		incidencia.setCorreosEnvio(a.getCorreosEnvio());
		incidencia.setCorreosEnCopiaEnvio(incidenciasDAOImpl.correosEnCC(incidencia));
		incidenciasDAOImpl.edicionUsuario(incidencia);
	}
	
	public int guardarIncidencia1(Peticiones incidencia) {
		return incidenciasDAOImpl.guardarIncidencia1(incidencia);
	}

	public void modificarEstadoIncidencia(Estados a, int b) {
		incidenciasDAOImpl.cambiarEstadoPeticion(b, a.getId());
	}

	public List<Peticiones> incidenciasPorUsuario(String usuario) {
	
		return incidenciasDAOImpl.listaPorUsuario(usuario);
	}

	// genera el modelo de edicion de incidencias.
	public void generarModeloEditable(ModelAndView model, int id, String view,
			boolean editableCorreos ) {

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

		List<String> as = servicioAplicaciones.todosLosNombres();

		model.addObject("aplicaciones", as);

		// incidencia
		Peticiones a = incidencia(id);
		this.correo = a.getCorreoAutorizador();
		a.setNombreUsuario(a.getUsuario().getUsuario());


			String[] array = a.getCorreosEnCopiaEnvio().split(";");
			List<String> correosAnadidos = new ArrayList<String>();
			System.out.println("aqui estamnos wey" + array[0]);
			for (int i = 0; i < array.length; i++) {
				if (correo(array[i])) {
					correosAnadidos.add(array[i]);
				}
			}
			
			String correosEnCopia = ""; 
			
			for(String h : correosAnadidos){
				correosEnCopia += h +	";"; 
			}
			a.setCorreosAnadidos(correosEnCopia);

		

		String fecha1 = a.getFecha();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		String[] fecha = fecha1.split(" ", 3);

		try {
			formatter.parse(fecha[0]);
			Pattern pat = Pattern.compile("^[0-2][0-9]:[0-5][0-9]$");
			Matcher mat = pat.matcher(fecha[1]);
			if (mat.find()) {
				a.setFechaDatePiker(fecha[0] + " " + fecha[1]);
				if (fecha[2] != null) {
					a.setFechaComentario(fecha[2]);
				}
			} else {
				a.setFechaDatePiker(fecha[0]);
				a.setFechaComentario(fecha[1] + " " + fecha[2]);
			}
			a.setRadioFecha("fecha");
		} catch (Exception excepcion) {
			a.setFechaComentario(fecha1);
			a.setRadioFecha("comentario");
		}

		File dir = new File("/datos/reportes/peticiones/" + id + "/");

		model.addObject("ficheros", dir.list());
		// model.addObject("Formulario", new Incidencias());

		model.addObject("Incidencia", a);

		model.setViewName(view);
	}

	private Boolean correo(String nombre) {
		System.out.println(correo+ "AQUI SE�ORITOS");
		if (!nombre.contains(correo)) {
			switch (nombre) {
			case "cedas@salud.madrid.org":
				return false;
			case "soporte.cibeles@salud.madrid.org":
				return false;
			case "oficina.medas@salud.madrid.org":
				return false;
			case "microsoft.cedas@salud.madrid.org":
				return false;				
			default:
				return true;
			}
		} else
			return false;
	}

	public void subirArchivos(List<MultipartFile> lista, int id) {
		try {
			if (lista.size() > 0) {
				for (MultipartFile archivo : lista) {
					if (!archivo.isEmpty()) {

						File ficheroDestino = new File(
								"/datos/reportes/peticiones/" + id);
						if (!ficheroDestino.exists())
							ficheroDestino.mkdir();

						System.out.println("/datos/reportes/peticiones/" + id
								+ "/" + archivo.getOriginalFilename());
						ficheroDestino = new File("/datos/reportes/peticiones/"
								+ id + "/" + archivo.getOriginalFilename());
						archivo.transferTo(ficheroDestino);
						System.out.println("funciono");
					} else {
						System.out.println("no hay fichero");
					}
				}
			}
		} catch (Exception b) {
			System.out.println("error => " + b.getMessage());
		}
	}

	private String crearZip(String id) throws IOException {
		File dir = new File("/datos/reportes/peticiones/" + id + "/");
		List<FileInputStream> lista = new ArrayList<FileInputStream>();
		String[] t = dir.list();
		for (String l : t) {
			lista.add(new FileInputStream("/datos/reportes/peticiones/" + id
					+ "/" + l));
		}
		FileOutputStream out = new FileOutputStream(
				"/datos/reportes/peticiones/" + id + "/" + id
						+ "_incidencia.zip");
		ZipOutputStream zipOut = new ZipOutputStream(out);
		for (int i = 0; i < lista.size(); i++) {
			byte b[] = new byte[2048];
			ZipEntry entry = new ZipEntry(t[i]);
			zipOut.putNextEntry(entry);
			int len = 0;
			while ((len = lista.get(i).read(b)) != -1) {
				zipOut.write(b, 0, len);
			}
			zipOut.closeEntry();
		}
		for (FileInputStream a1 : lista) {
			a1.close();
		}
		zipOut.close();
		return "/datos/reportes/peticiones/" + id + "/" + id
				+ "_incidencia.zip";
	}

	public void descargaIncidencia(Peticiones incidencia,
			HttpServletResponse response) throws NumberFormatException,
			IOException {

		String a = generaArchivoWorld.replaceTextFound(incidencia);
		File file = new File(crearZip("" + incidencia.getId()));
		if (!file.exists()) {
			String errorMessage = "error, zip no creado";
			System.out.println(errorMessage);
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}
		String mimeType = URLConnection
				.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}
		System.out.println("mimetype : " + mimeType);
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition",
				String.format("inline; filename=\"" + file.getName() + "\""));
		response.setHeader("Content-Disposition",
				String.format("attachment; filename=\"%s\"", file.getName()));
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(
				file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
		file.delete();
		borrado(a);
	}

	public void mostrarCorreo(ModelAndView model, Peticiones formulario) {
		model.addObject("correoEnvio", formulario.getCorreosEnvio());
		model.addObject("enCopia", formulario.getCorreosEnCopiaEnvio());
		if (!formulario.getRequiereParada().contains(""))
			model.addObject("parada", formulario.getRequiereParada());
		model.addObject("descripccion", formulario.getDescripccion());
		model.addObject("fecha", formulario.getFecha());
		model.addObject("documentacion", formulario.getDocumentacion());
		model.addObject("entornos", formulario.getEntorno().toUpperCase());
		model.addObject("aplicacion", formulario.getAplicacion().toUpperCase());
		model.addObject("relaseNotes", formulario.getRelaseNotes());
		model.addObject("motivo", formulario.getMotivo());
		model.addObject("nombre", formulario.getUsuario().getNombre());
		model.addObject("telefono", formulario.getUsuario().getTelefono());
		model.addObject("extension", formulario.getUsuario().getExtension());
		model.addObject("email", formulario.getUsuario().getEmail());
		model.addObject("asunto", formulario.getAsunto());
		model.addObject("unicenter", formulario.getCodigoUnicenter());
		System.out.println("autorizacion funcional"+formulario.getAutorizacionFuncional());
		if(formulario.getAutorizacionFuncional() != null ){
			if (!(formulario.getAutorizacionFuncional().equals("")) ) {
				model.addObject("funcionalCliente", formulario.getAutorizacionFuncional());
			}
		}
		model.setViewName("visualizacion");
	}

	public void borrado(Integer id, String nombreArchivo, String extension) {
		File file = new File("/datos/reportes/peticiones/" + id + "/"
				+ nombreArchivo + "." + extension);
		System.out.println("" + file.delete());
	}

	public void borrado(String nombreArchivo) {
		File file = new File(nombreArchivo);
		System.out.println("" + file.delete());

	}

	public ServicioAplicaciones getServicioAplicaciones() {
		return servicioAplicaciones;
	}

	public void setServicioAplicaciones(
			ServicioAplicaciones servicioAplicaciones) {
		this.servicioAplicaciones = servicioAplicaciones;
	}

	public IncidenciasDAO getIncidenciasDAOImpl() {
		return incidenciasDAOImpl;
	}

	public void setIncidenciasDAOImpl(IncidenciasDAO incidenciasDAOImpl) {
		this.incidenciasDAOImpl = incidenciasDAOImpl;
	}

	public GeneraArchivoWord getGeneraArchivoWorld() {
		return generaArchivoWorld;
	}

	public void setGeneraArchivoWorld(GeneraArchivoWord generaArchivoWorld) {
		this.generaArchivoWorld = generaArchivoWorld;
	}

}
