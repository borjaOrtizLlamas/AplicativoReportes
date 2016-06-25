package com.connectis.services;

import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.connectis.dao.AplicacionesDAOIntf;
import com.connectis.dao.UsuariosMedasDAO;
import com.connectis.model.Aplicaciones;
import com.connectis.model.Peticiones;
import com.connectis.model.UsuariosMedas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.print.DocFlavor.URL;

public class GeneraArchivoWord {

	public UsuariosMedasDAO usuariosMedasDAO;
	public AplicacionesDAOIntf aplicacionesDAO;

	public String replaceTextFound(Peticiones incidencia) throws IOException {

		UsuariosMedas usuario = incidencia.getUsuario(); 

		String realContextPath = "/datos/reportes";

		String inputfilepath = realContextPath + "/plantillas/rfc.docx";

		String outputfilepath = realContextPath + "/peticiones/"
				+ incidencia.getId() + "/";

		File folder = new File(outputfilepath);

		if (!folder.isDirectory())
			folder.mkdirs();

		java.util.Date fecha = new Date();

		DateFormat formatoFecha = new SimpleDateFormat("dd_MM_yyyy");

		String fechaDocumento = "" + formatoFecha.format(fecha);
		outputfilepath = outputfilepath + "/rfc-" + incidencia.getAplicacion()
				+ "-" + incidencia.getEntorno() + "-" + fechaDocumento
				+ ".docx";

		InputStream fs = new FileInputStream(inputfilepath);
		XWPFDocument doc = new XWPFDocument(fs);

		for (XWPFTable tablas : doc.getTables()) {
			List<XWPFTableRow> a = tablas.getRows();
			for (XWPFTableRow rows : a) {
				for (XWPFTableCell celdas : rows.getTableCells()) {
					for (XWPFParagraph parrafos : celdas.getParagraphs()) {
						List<XWPFRun> lineas = parrafos.getRuns();
						if (lineas != null) {
							for (XWPFRun r : lineas) {
								String text = r.getText(0);
								if (text != null && !text.equals("")) {
									if (text.contains("$NOMBRE")) {
										text = text.replace("$NOMBRE",
												usuario.getNombre());
										r.setText(text, 0);
									}

									
									if (text.contains("$DESCRIPCCION_BASICA")) {
										text = text.replace("$DESCRIPCCION_BASICA",
												incidencia.getDescripccion());
										r.setText(text, 0);
									}

									
									if (text.contains("$ENTORNO")) {
										text = text.replace("$ENTORNO",
												incidencia.getEntorno());
										r.setText(text, 0);
									}
									if (text.contains("$RELASE_NOTES")) {
										text = text.replace("$RELASE_NOTES",
												incidencia.getRelaseNotes());
										r.setText(text, 0);
									}
									if (text.contains("$TELEFONO")) {
										text = text.replace("$TELEFONO", ""
												+ usuario.getTelefono());
										r.setText(text, 0);
									}
									if (text.contains("$EXTENSION")) {
										text = text.replace("$EXTENSION", ""
												+ usuario.getExtension());
										r.setText(text, 0);
									}
									if (text.contains("$MOVIL")) {
										text = text.replace("$MOVIL", ""
												+ usuario.getMovil());
										r.setText(text, 0);
									}
									if (text.contains("$EMAIL")) {
										text = text.replace("$EMAIL",
												usuario.getEmail());
										r.setText(text, 0);
									}
									if (text.contains("$APLICACION_SELECCIONADA")) {
										text = text.replace(
												"$APLICACION_SELECCIONADA",
												incidencia.getAplicacion());
										r.setText(text, 0);
									}
									if (text.contains("$PRODUCCION")) {
										text = text.replace("$PRODUCCION",
												incidencia.getEntorno());
										r.setText(text, 0);
									}
									if (text.contains("$AUTORIZADO")) {
										text = text.replace("$AUTORIZADO",
												incidencia.getAutorizador());
										r.setText(text, 0);

									}

									if (text.contains("$CORREODEAUTORIZADO")) {
										text = text
												.replace(
														"$CORREODEAUTORIZADO",
														incidencia
																.getCorreoAutorizador());
										r.setText(text, 0);
									}
									if (text.contains("$DESCRIPCCION_DEL_CAMBIO")) {
										text = text.replace(
												"$DESCRIPCCION_DEL_CAMBIO",
												incidencia.getDescripcionDOC());
										r.setText(text, 0);
									}

									if (text.contains("$PARADA")) {
										if (incidencia.getRequiereParada()
												.contains("Si"))
											text = text.replace("$PARADA",
													"REQUIERE PARADA");
										else if (incidencia.getRequiereParada()
												.contains("No"))
											text = text.replace("$PARADA",
													" NO REQUIERE PARADA");
										else
											text = text.replace("$PARADA", "");
										r.setText(text, 0);

									}

									if (text.contains("$RAZON")) {
										text = text.replace("$RAZON",
												incidencia.getMotivo());
										r.setText(text, 0);
									}

									if (text.contains("$FECHA")) {
										if (incidencia.isUrgente()) {
											text = text.replace("$FECHA",
													"URGENTE");
											r.setText(text, 0);
										} else {
											text = text.replace("$FECHA",
													incidencia.getFecha());
											r.setText(text, 0);
										}
									}

									if (text.contains("$URGENTE")) {
										if (incidencia.isUrgente()) {
											text = text.replace("$URGENTE",
													"X URGENTE");
											r.setText(text, 0);
										} else {
											text = text.replace("$URGENTE",
													"URGENTE");
											r.setText(text, 0);
										}
									}
								}
							}
						}
					}
				}
			}

			FileOutputStream out = new FileOutputStream(outputfilepath);
			doc.write(out);
			out.close();
		}
		return outputfilepath;
	}

	public AplicacionesDAOIntf getAplicacionesDAO() {
		return aplicacionesDAO;
	}

	public void setAplicacionesDAO(AplicacionesDAOIntf aplicacionesDAO) {
		this.aplicacionesDAO = aplicacionesDAO;
	}

	public UsuariosMedasDAO getUsuariosMedasDAO() {
		return usuariosMedasDAO;
	}

	public void setUsuariosMedasDAO(UsuariosMedasDAO usuariosMedasDAO) {
		this.usuariosMedasDAO = usuariosMedasDAO;
	}

}