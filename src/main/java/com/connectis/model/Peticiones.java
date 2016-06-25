package com.connectis.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.connectis.services.ServicioAplicaciones;
import com.connectis.services.ServicioUsuarios;

public class Peticiones {

	// bbdd esto singifica que el dato esta relacionado con la base de datos.
	private int id;

	// bbdd
	// fecha en la que se crea la incidencia
	private String fechaCreacionIncidencia;

	// bbdd
	// usuario de la incidencia
	private UsuariosMedas usuario;

	// nombre que pasamos al formulario para despues buscar el objeto de el
	// usuario.
	private String nombreUsuario;
	// bbdd
	// esto es si es pre-produccion o produccion
	private String entorno;

	private Estados estadoEnum;

	private int estado = 404;
	// bbdd
	// nombre de la aplicacion
	private String aplicacion;

	// bbdd
	
	private Aplicaciones aplicacionObjeto;

	// radio que indica si la fecha es un comentario o un datepiker
	// los valores pueden ser 'URGENTE','fecha', que sera el datepiker, o
	// 'comentario' que sera el un comentario.
	private String radioFecha;

	// bbdd
	// fecha dirigida a bbdd
	private String fecha;

	// fecha datepiker
	private String fechaDatePiker;
	// fecha comentario
	private String fechaComentario;

	// bbdd
	// descripccion dirigida en el correo despues de buenos dias.
	private String descripccion;

	// bbdd
	// documentacion del correo
	private String documentacion;
	// bbdd

	// motivo de la incidencia
	private String motivo;
	// bbdd

	// si requiere parada la incidencia
	private String requiereParada;

	// bbdd

	// dscripccion del doc
	private String descripcionDOC;
	
	// bbdd
	private String autorizacionFuncional; 
	
	// relase notes
	private String relaseNotes;
	// bbdd
	// nombre del autorizador BBDD
	private String autorizador;
	// correo del autorizador BBDD
	private String correoAutorizador;

	// bbdd
	// lista por defecto de autorizadores, si este tienen el valor de Monica o
	// julia afectara al correo y a autorizador.
	private String listaPorDefecto;

	// bbdd
	// si la incidencia es en relacion con la aplicacion (programacion)
	private boolean aplicacionIN = false;

	// bbdd
	private int aplicacionIN1;

	// si la incidencia tiene relacion con la BBDD del aplicativo
	private boolean bbdd = false;

	// bbdd del anterior, 0 y el otro es falso, 1 y es verdadero
	private int bbdd1;

	private boolean urgente = false;

	private int urgente1;
	// bbdd
	// codigo que el rol de ServicioSoporte se encarga de poner
	private String codigoUnicenter;

	// bbdd
	// Asunto del correo que se va a enviar
	private String asunto;

	// bbdd
	// si la incidencia en si a sido resuelta.
	private int revisada;

	// bbdd

	private String correosAnadidos;
	private String correosEnvio;
	// bbdd
	private String correosEnCopiaEnvio;

	private List<MultipartFile> archivos;

	public List<MultipartFile> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<MultipartFile> archivos) {
		this.archivos = archivos;
	}

	public boolean isUrgente() {
		return urgente;
	}

	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}

	public int getUrgente1() {
		if (urgente)
			return 1;
		else
			return 0;
	}

	public void setUrgente1(int urgente1) {
		if (urgente1 == 1)
			urgente = true;
		else
			urgente = false;

	}

	public boolean isAplicacionIN() {
		return aplicacionIN;
	}

	public void setAplicacionIN(boolean aplicacionIN) {
		this.aplicacionIN = aplicacionIN;
	}

	public int getAplicacionIN() {
		if (aplicacionIN)
			return 1;
		else
			return 0;
	}

	public void setAplicacionIN(int a) {
		if (a == 1)
			this.aplicacionIN = true;
		else
			this.aplicacionIN = false;
	}

	public boolean isBbdd() {
		return bbdd;
	}

	public void setBbdd(boolean bbdd) {
		this.bbdd = bbdd;
	}

	public int getBbdd() {
		if (bbdd)
			return 1;
		else
			return 0;
	}

	public void setBbdd(int bbdd) {
		if (bbdd == 1)
			this.bbdd = true;
		else
			this.bbdd = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UsuariosMedas getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosMedas usuario) {
		this.usuario = usuario;
	}

	public String getCodigoUnicenter() {
		return codigoUnicenter;
	}

	public void setCodigoUnicenter(String codigoUnicenter) {
		this.codigoUnicenter = codigoUnicenter;
	}

	public int getRevisada() {
		return revisada;
	}

	public void setRevisada(int revisada) {
		this.revisada = revisada;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getListaPorDefecto() {
		return listaPorDefecto;
	}

	public void setListaPorDefecto(String listaPorDefecto) {
		this.listaPorDefecto = listaPorDefecto;
	}

	public void setAutorizador(String autorizador) {
		this.autorizador = autorizador;
	}

	public String getAutorizador() {
		if (listaPorDefecto != null) {
			if (listaPorDefecto.contains("Mónica Díaz")) {
				return "Mónica Díaz";

			} else if (listaPorDefecto.contains("Yolanda Llamas Nistal")) {
				return "Yolanda Llamas Nistal";
			} else {
				return autorizador;
			}
		} else {
			return autorizador;
		}

	}

	public String getCorreoAutorizador() {
		if (listaPorDefecto != null) {
			if (listaPorDefecto.contains("Mónica Díaz")) {
				return "monica.diaz@salud.madrid.org";
			} else if (listaPorDefecto.contains("Yolanda Llamas Nistal")) {
				return "Yolanda.llamas@salud.madrid.org";
			} else
				return correoAutorizador;
		} else {
			return correoAutorizador;
		}
	}

	public void setCorreoAutorizador(String correoAutorizador) {
		this.correoAutorizador = correoAutorizador;
	}

	public String getDescripcionDOC() {
		return descripcionDOC;
	}

	public void setDescripcionDOC(String descripcionDOC) {
		this.descripcionDOC = descripcionDOC;
	}

	public String getRelaseNotes() {
		return relaseNotes;
	}

	public void setRelaseNotes(String relaseNotes) {
		this.relaseNotes = relaseNotes;
	}

	public String getRequiereParada() {
		try {
			if (requiereParada.contains("nada"))
				return "";
			else
				return requiereParada;
		} catch (Exception e) {
			return null;
		}
	}

	public void setRequiereParada(String requiereParada) {
		this.requiereParada = requiereParada;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDocumentacion() {
		return documentacion;
	}

	public void setDocumentacion(String documentacion) {
		this.documentacion = documentacion;
	}

	public String getDescripccion() {
		return descripccion;
	}

	public void setDescripccion(String descripccion) {
		this.descripccion = descripccion;
	}

	public String getFechaDatePiker() {
		return fechaDatePiker;
	}

	public void setFechaDatePiker(String fechaDatePiker) {
		this.fechaDatePiker = fechaDatePiker;
	}

	public String getFechaComentario() {
		return fechaComentario;
	}

	public void setFechaComentario(String fechaComentario) {
		this.fechaComentario = fechaComentario;
	}

	public String getRadioFecha() {
		return radioFecha;
	}

	public void setRadioFecha(String radioFecha) {
		this.radioFecha = radioFecha;
	}

	public String getEntorno() {
		return entorno;
	}

	public void setEntorno(String entorno) {
		this.entorno = entorno;
	}

	public String getAplicacion() {
		return aplicacion;
	}

	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}

	// radio que indica si la fecha es un comentario o un datepiker
	// los valores pueden ser 'URGENTE','fecha', que sera el datepiker, o
	// 'comentario' que sera el un comentario.

	public String getFecha() {
		if (radioFecha != null) {
			if (radioFecha.contains("comentario")) {
				if (isUrgente()) {
					return fechaComentario
							+ "<font color=\"red\"> (URGENTE)</font>";
				} else
					return fechaComentario;
			} else {
				if (isUrgente()) {
					return fechaDatePiker + " " + fechaComentario
							+ "<font color=\"red\"> (URGENTE)</font>";
				} else
					return fechaDatePiker + " " + fechaComentario;
			}
		} else {
			return fecha;
		}
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getAplicacionIN1() {
		return aplicacionIN1;
	}

	public void setAplicacionIN1(int aplicacionIN1) {
		this.aplicacionIN1 = aplicacionIN1;
	}

	public int getBbdd1() {
		return bbdd1;
	}

	public void setBbdd1(int bbdd1) {
		this.bbdd1 = bbdd1;
	}

	public String getFechaCreacionIncidencia() {
		return fechaCreacionIncidencia;
	}

	public void setFechaCreacionIncidencia(String fechaCreacionIncidencia) {
		this.fechaCreacionIncidencia = fechaCreacionIncidencia;
	}

	public String getNombreUsuario() {
		return this.nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreosAnadidos() {
		return correosAnadidos;
	}

	public void setCorreosAnadidos(String correosAnadidos) {
		this.correosAnadidos = correosAnadidos;
	}

	public String getCorreosEnvio() {
		return correosEnvio;
	}

	public void setCorreosEnvio(String correosEnvio) {
		this.correosEnvio = correosEnvio;
	}

	public String getCorreosEnCopiaEnvio() {
		return correosEnCopiaEnvio;
	}

	public void setCorreosEnCopiaEnvio(String correosEnCopiaEnvio) {
		this.correosEnCopiaEnvio = correosEnCopiaEnvio;
	}

	public Aplicaciones getAplicacionObjeto() {
		return aplicacionObjeto;
	}

	public void setAplicacionObjeto(Aplicaciones aplicacionObjeto) {
		this.aplicacionObjeto = aplicacionObjeto;
	}

	public int getEstado() {
		if (estadoEnum != null) {
			return estadoEnum.getId();
		}
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Estados getEstadoEnum() {
		if (estado != 404) {
			Estados a = null;
			switch (estado) {
			case 0:
				a = Estados.PENDIENTEDEREVISION;
				break;
			case 1:
				a = Estados.SINENVIAR;
				break;
			case 2:
				a = Estados.PORCOMPLETAR;
				break;
			case 3:
				a = Estados.ENPROCESO;
				break;
			case 4:
				a = Estados.PRECAMBIO;
				break;
			case 5:
				a = Estados.CERRADA;
				break;
			default:
				break;
			}
			return a; 
		}

		return estadoEnum;
	}

	public void setEstadoEnum(Estados estadoEnum) {
		this.estadoEnum = estadoEnum;
	}
	
	
	
	
	

	public String getAutorizacionFuncional() {
		return autorizacionFuncional;
	}

	public void setAutorizacionFuncional(String autorizacionFuncional) {
		this.autorizacionFuncional = autorizacionFuncional;
	}

	@Override
	public String toString() {
		return "Incidencias [id=" + id + ", fechaCreacionIncidencia="
				+ fechaCreacionIncidencia + "nombreUsuario=" + nombreUsuario
				+ ", entorno=" + entorno + ", aplicacion=" + aplicacion
				+ ", radioFecha=" + radioFecha + ", fecha=" + fecha
				+ ", fechaDatePiker=" + fechaDatePiker + ", fechaComentario="
				+ fechaComentario + ", descripccion=" + descripccion
				+ ", documentacion=" + documentacion + ", motivo=" + motivo
				+ ", requiereParada=" + requiereParada + ", descripcionDOC="
				+ descripcionDOC + ", relaseNotes=" + relaseNotes
				+ ", autorizador=" + autorizador + ", correoAutorizador="
				+ correoAutorizador + ", listaPorDefecto=" + listaPorDefecto
				+ ", aplicacionIN=" + aplicacionIN + ", aplicacionIN1="
				+ aplicacionIN1 + ", bbdd=" + bbdd + ", bbdd1=" + bbdd1
				+ ", urgente=" + urgente + ", urgente1=" + urgente1
				+ ", codigoUnicenter=" + codigoUnicenter + ", asunto=" + asunto
				+ ", revisada=" + revisada + ", archivos=" + archivos + "]";
	}

}
