package com.connectis.services;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.connectis.dao.AplicacionesDAOIntf;
import com.connectis.dao.UsuariosMedasDAO;
import com.connectis.model.Peticiones;
import com.connectis.model.UsuariosMedas;
import com.sun.mail.smtp.SMTPMessage;
//SIN USO REAL, lo dejo por si las moscas. 
public class JavaMail {

	
	private UsuariosMedasDAO usuariosMedasDAO;
	private AplicacionesDAOIntf aplicacionesDAO;
	private IncidenciasServicio incidenciasServicio;

	private final  String azul = "<font color=\"blue\">";  
	private final String cierreAzul = "</font>";
	
	
	
	
	public void enviarMail(Peticiones incidencia/*,String pathArchivo*/) {

		final String username = "borja.irtiz@connectis-ict.es";
		//final String username = "drojo.externo@salud.madrid.org"; 
		
		
		
		Properties props = new Properties();
		
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.office365.com");
		//props.put("mail.smtp.host", "smtp.gmail.com");	
		props.put("mail.smtp.port", "587");

		try {Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username,"321");
				}
			});

			MimeMessage mensaje = new MimeMessage(session);

			
			/*
			String [] emails = incidencia.getCorreosEnvio().split(";"); 
			String [] emailsCopia = incidencia.getCorreosEnCopiaEnvio().split(";"); 

			InternetAddress[] envio = new InternetAddress[emails.length]; 
			InternetAddress[] envioCopia = new InternetAddress[emails.length]; 

			for (int i = 0; i < envio.length; i++) {
				envio[i] = new InternetAddress(emails[i]);
			}

			for (int i = 0; i < envioCopia.length; i++) {
				envioCopia[i] = new InternetAddress(emailsCopia[i]);
			}
			
			mensaje.setRecipients(Message.RecipientType.TO,  envio	);
			
			mensaje.addRecipients(Message.RecipientType.CC,envioCopia );
			*/
			
			mensaje.setFrom(new InternetAddress(username));
			mensaje.setRecipients(Message.RecipientType.TO,new InternetAddress[] { 
					new InternetAddress ("borja.ortizllamas1@gmail.com") ,
					new InternetAddress ("victor.lopez@connectis-ict.es")
								});
			
			//drojo.externo@salud.madrid.org

			
			
			
			mensaje.addRecipients(Message.RecipientType.CC, new InternetAddress[] { new InternetAddress("uyu002@gmail.com") });

			
			
			mensaje.setSubject(incidencia.getAsunto());
			
			Multipart multiparte = new MimeMultipart();
			BodyPart para = new MimeBodyPart();
			
			//texto del correo
			
			String correo = "<font size =\"3\" face=\"arial\" > "; 
			
			correo += "para:" + incidencia.getCorreosEnvio();
			correo += "<br> en copia" + incidencia.getCorreosEnCopiaEnvio(); 
			
			
			correo += "<b>Buenos días<b> <br><br> " + incidencia.getDescripccion()+"<br>"; 
					
			if(incidencia.getRequiereParada().contains("si")) correo += azul +"<br><b>REQUIERE PARADA</b>" + cierreAzul;  
			else if(incidencia.getRequiereParada().contains("no"))correo += azul +"<b>NO REQUIERE PARADA</b>" + cierreAzul;  
			
			correo += texto("DOCUMENTACION",incidencia.getDocumentacion(),false); 
			
			correo += texto("MOTIVO DE LA SOLICITUD",incidencia.getMotivo(),false); 

			correo += texto("ENTORNOS AFECTADOS",incidencia.getAplicacion() + " " + incidencia.getEntorno() ,true); 
			
			if(incidencia.isUrgente())	correo += texto("FECHA PROPUESTA",incidenciasServicio.incidencia(incidencia.getId()).getFecha() + "<font color=\"red\">urgente</font>",true); 
			 else 	correo += texto("FECHA PROPUESTA",incidenciasServicio.incidencia(incidencia.getId()).getFecha() ,true); 

			correo += texto("ORDEN DE TRABAJO","se adjunta RFC en peticion <li>se crea orden en unicenter: "
				+	incidencia.getCodigoUnicenter() + "</li>",false); 
	
			correo += texto("RELASE NOTES","</font>libera la orden <font color=\"blue\">" + incidencia.getRelaseNotes(),true); 

			UsuariosMedas usuario = incidencia.getUsuario(); 					
										
			correo += "CONTACTO <ul> <li>" +usuario.getNombre() + "</li><li>" + usuario.getTelefono() +"(" + usuario.getExtension() + ")</li><li> Email: " + usuario.getEmail() +"</li><ul><<br>soporte.cibeles@salud.madrid.org" ; 
			
			correo += "</font>";
			
			para.setContent(correo,"text/html; charset=utf-8");
			
			multiparte.addBodyPart(para);
			
			/*
			BodyPart documento = new MimeBodyPart();
			DataSource fuente = new FileDataSource(pathArchivo);
			documento.setDataHandler(new DataHandler(fuente));
			String a[] = pathArchivo.split("/");
			int b = a.length;
			documento.setFileName(a[b - 1]);
			multiparte.addBodyPart(documento);
			*/
			
			
			
			mensaje.setContent(multiparte);
			Transport.send(mensaje);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
	
	private String texto(String titulo, String texto,boolean azul) throws MessagingException{
		if(azul) return "<br><b>"+ titulo+ "</b><br><ul><li>"+this.azul + texto + this.cierreAzul+"</li></ul>"; 
		else return "<br><b>"+ titulo+ "</b><br><ul><li>" + texto +"</li></ul>"; 
	}

	public UsuariosMedasDAO getUsuariosMedasDAO() {
		return usuariosMedasDAO;
	}

	public void setUsuariosMedasDAO(UsuariosMedasDAO usuariosMedasDAO) {
		this.usuariosMedasDAO = usuariosMedasDAO;
	}

	public AplicacionesDAOIntf getAplicacionesDAO() {
		return aplicacionesDAO;
	}

	public void setAplicacionesDAO(AplicacionesDAOIntf aplicacionesDAO) {
		this.aplicacionesDAO = aplicacionesDAO;
	}

	public IncidenciasServicio getIncidenciasServicio() {
		return incidenciasServicio;
	}

	public void setIncidenciasServicio(IncidenciasServicio incidenciasServicio) {
		this.incidenciasServicio = incidenciasServicio;
	}
	

	
	
}
