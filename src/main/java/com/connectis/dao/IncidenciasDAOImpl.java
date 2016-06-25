package com.connectis.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import com.connectis.model.Aplicaciones;
import com.connectis.model.Peticiones;

public class IncidenciasDAOImpl implements IncidenciasDAO {

	private SessionFactory sessionFactory;

	@Override
	public void guardarIncidencia(Peticiones incidencia) {
		java.util.Date fecha = new Date();
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fecha1 = "" + formatoFecha.format(fecha);
		parseoABBDD(incidencia);
		incidencia.setFechaCreacionIncidencia(fecha1);
		sessionFactory.getCurrentSession().save(incidencia);
	}

	public int guardarIncidencia1(Peticiones incidencia) {
		java.util.Date fecha = new Date();
		DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
		String fecha1 = "" + formatoFecha.format(fecha);
		parseoABBDD(incidencia);
		
		incidencia.setFechaCreacionIncidencia(fecha1);
		String enviarA = "";
		if (incidencia.isAplicacionIN())
			enviarA += "aplicaciones.cedas@salud.madrid.org;";
		if (incidencia.isBbdd())
			enviarA += "bbdd.cedas@salud.madrid.org;";
		incidencia.setCorreosEnvio(incidencia.getUsuario().getEmail() + ";"
				+ enviarA);
		String correosEnCopiaEnvio = correosEnCC(incidencia);
		incidencia.setCorreosEnCopiaEnvio(correosEnCopiaEnvio);

		
		
		sessionFactory.getCurrentSession().save(incidencia);
		return incidencia.getId();
	}

	@Override
	public List<Peticiones> todasLasIncidencias() {
		List<Peticiones> a = sessionFactory
				.getCurrentSession()
				.createSQLQuery("select *  from INCIDENCIAS where estado != 5 order by  id desc").addEntity(Peticiones.class)
				.list();
		for (int i = 0; i < a.size(); i++) {
			parseoAFormulario(a.get(i));
		}

		if (a.size() > 0)
			return a;
		else {
			System.out.println("no hay Peticiones");
			return null;
		}
	}

	
	public List<Peticiones> todasLasIncidencias(String busqueda) {
		
		List<Peticiones> a = sessionFactory
				.getCurrentSession()
				.createSQLQuery("select *  from INCIDENCIAS  where " + busqueda + " order by FECHA_CREACION_INCIDENCIA desc, id desc")
				.addEntity(Peticiones.class)
				.list();
		for (int i = 0; i < a.size(); i++) {
			parseoAFormulario(a.get(i));
		}

		if (a.size() > 0)
			return a;
		else {
			System.out.println("no hay Peticiones");
			return null;
		}
	}

	
	
	
	@Override
	public Peticiones unaIndicenciaIncidencias(int id) {
		List<Peticiones> a = sessionFactory.getCurrentSession()
				.createQuery("from Peticiones where id=?").setParameter(0, id)
				.list();

		if (a.size() > 0) {
			parseoAFormulario(a.get(0));
			return a.get(0);
		} else {
			System.out.println("la Peticion con el id " + id
					+ " no ha sido encontrada");
			return null;
		}
	}

	@Override
	public void cambiarEstadoPeticion(int id, int estado) {
		int result = sessionFactory
				.getCurrentSession()
				.createQuery(
						"update Peticiones p set p.estado = :newEstado where p.id = :newid")
				.setParameter("newEstado", estado).setParameter("newid", id)
				.executeUpdate();


	}

	@Override
	public void borrarIncidencia(Peticiones incidencia) {
		sessionFactory.getCurrentSession().delete(incidencia);
	}

	@Override
	public void modificarIncidencia(Peticiones incidencia) {
		parseoABBDD(incidencia);
		sessionFactory.getCurrentSession().update(incidencia);
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// como la clase incicencias es la misma que va dirigida al formulario con
	// los checkboxs es mas sencillo que fueran boolean,
	// como bases de datos oracle no admiten boolean hay que hacer el apaño
	// a traves de una variable que hemos generado de tipo int
	// la base de datos en estos solo admite 0(FALSE) y 1 (TRUE), de esta manera
	// conseguimos simularlo.

	private void parseoABBDD(Peticiones i) {
		i.setBbdd1(i.getBbdd());
		i.setAplicacionIN1(i.getAplicacionIN());
	}

	private void parseoAFormulario(Peticiones i) {
		i.setBbdd(i.getBbdd1());
		i.setAplicacionIN(i.getAplicacionIN1());
	}

	@Override
	public void edicionUsuario(Peticiones peticion) {
		sessionFactory.getCurrentSession().update(peticion);

	}

	@Override
	public List<Peticiones> listaPorUsuario(String usuario) {
		List<Peticiones> nombres = new ArrayList<Peticiones>();
		nombres = sessionFactory.getCurrentSession().createSQLQuery
				("select *  from  incidencias where usuario = '" + usuario + "' and estado = 1").addEntity(Peticiones.class).list(); 

		if (nombres.size() > 0)return nombres;	
				return null;
	}

	
	
	public String correosEnCC(Peticiones incidencia) {

		String correosEnCopiaEnvio = "";

		correosEnCopiaEnvio = "cedas@salud.madrid.org"
				+ ";soporte.cibeles@salud.madrid.org;"
				+ "oficina.medas@salud.madrid.org;"
				+ incidencia.getCorreoAutorizador() + ";";

		if (incidencia.getAplicacionObjeto().getWindows() == 1)
			correosEnCopiaEnvio += "microsoft.cedas@salud.madrid.org;";

		if (incidencia.getCorreosAnadidos() != null)
			correosEnCopiaEnvio += incidencia.getCorreosAnadidos();

		return correosEnCopiaEnvio;

	}

	@Override
	public List<String> correosIncidencia(int busqueda) {
		List<String> nombres = new ArrayList<String>();
		nombres = sessionFactory.getCurrentSession().createSQLQuery
				("select correos_copia  from  incidencias where id = "+ busqueda ).addEntity(String.class).list(); 

		if (nombres.size() > 0)	return nombres;	
				return null;
	}
	

}
