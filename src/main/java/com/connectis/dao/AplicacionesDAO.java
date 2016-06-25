package com.connectis.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.connectis.model.Aplicaciones;

public class AplicacionesDAO implements AplicacionesDAOIntf {

	private SessionFactory sessionFactory;

	public List<String> nombres() {
		List<Aplicaciones> nombres = new ArrayList<Aplicaciones>();
		List<String> nombresS = new ArrayList<String>();
		nombres = sessionFactory.getCurrentSession()
				.createQuery("from Aplicaciones order by nombre").list();
		for (Aplicaciones a : nombres) {
			nombresS.add(a.getNombre());
		}
		return nombresS;
	}
	
	
	public List<String> nombres(String usuario) {
		List<Aplicaciones> nombres = new ArrayList<Aplicaciones>();
		List<String> nombresS = new ArrayList<String>();
		nombres = sessionFactory.getCurrentSession()
				.createSQLQuery("select * from aplicaciones "
						+ "where id IN "
						+ "(select id_aplicativo  from  usuarios_aplicaciones"
						+ " where id_usuario = '" + usuario + "')")
						.addEntity(Aplicaciones.class)
						.list();
		for (Aplicaciones a : nombres) {
			nombresS.add(a.getNombre());
		}
		return nombresS;
	}


	@Override
	public Aplicaciones aplicacion(String nombre) {
		List<Aplicaciones> nombres = new ArrayList<Aplicaciones>();
		nombres = sessionFactory.getCurrentSession()
				.createQuery("from Aplicaciones where nombre=?")
				.setParameter(0, nombre).list();
		if (nombres.size() > 0)
			return nombres.get(0);
		return null;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
