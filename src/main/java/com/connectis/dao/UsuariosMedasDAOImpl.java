package com.connectis.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;

import com.connectis.model.UserRole;
import com.connectis.model.UsuariosMedas;

public class UsuariosMedasDAOImpl implements UsuariosMedasDAO {
	
	private SessionFactory sessionFactory;

	
	public UsuariosMedas extraerDatos(String username) {

		List<UsuariosMedas> users = new ArrayList<UsuariosMedas>();
		users = sessionFactory.getCurrentSession().createQuery("from UsuariosMedas where usuario=?").setParameter(0, username).list();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}
	
	
	
	@Override
	public List<UserRole> findByUserNamerRoles(String username) {
		List<UserRole> users = new ArrayList<UserRole>();
		users = sessionFactory.getCurrentSession().createQuery("from UserRole where usuario=?").setParameter(0, username).list();
		if (users.size() > 0) {
			return users;
		} else {
			return null;
		}
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}



	@Override
	public void anadirAplicacion(String usuario, int aplicacion) {
		sessionFactory
				.getCurrentSession()
				.createSQLQuery("insert into usuarios_aplicaciones values ('"+usuario +"',"+ aplicacion +")")
				.executeUpdate(); 
		
	}



	@Override
	public List<UsuariosMedas> usuariosMedas() {
		List<UsuariosMedas> users = new ArrayList<UsuariosMedas>();
		users = sessionFactory.getCurrentSession().createQuery("from UsuariosMedas").list();
		return users;
	}



	@Override
	public void crearUsuario(UsuariosMedas usuario) {
		sessionFactory.getCurrentSession().save(usuario);
	}

	@Override
	public void crearRoles(UsuariosMedas usuario) {
		System.out.println("esta es la queryy      insert into roles_medas (usuario,role) values ('"+ usuario.getUsuario() +"','ROLE_USER')");
		sessionFactory.getCurrentSession().createSQLQuery("insert into roles_medas (usuario,role) values ('"+ usuario.getUsuario() +"','ROLE_USER')")
		.executeUpdate(); 
	}

}
