/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.connectis.services;

/**
 *
 * @author borja
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;

import com.connectis.dao.UsuariosMedasDAO;
import com.connectis.model.UserRole;
import com.connectis.model.UsuariosMedas;

public class ServicioUsuarios implements UserDetailsService {

	private UsuariosMedasDAO userDao;

	public UsuariosMedas devolverUsuario(final String username) {
		return userDao.extraerDatos(username);
	}

	public void anadirAplicacionUsuario(String usuario, int aplicacion) {
		userDao.anadirAplicacion(usuario, aplicacion);
	}
	
	public void crearUsuario(UsuariosMedas usuario) {
		userDao.crearUsuario(usuario);
		userDao.crearRoles(usuario);
	}	


	public List<UsuariosMedas> listaUsuarios() {
		return userDao.usuariosMedas();
	}

	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {

		UsuariosMedas user = userDao.extraerDatos(username);
		if (user == null) {
			throw new UsernameNotFoundException("usuario no encontrado");
		}

		List<GrantedAuthority> authorities = buildUserAuthority(username);
		return buildUserForAuthentication(user, authorities);
	}

	private User buildUserForAuthentication(UsuariosMedas user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUsuario(), user.getContrasena(), true, true,
				true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(String username) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		List<UserRole> a = userDao.findByUserNamerRoles(username);

		for (UserRole b : a) {
			setAuths.add(new SimpleGrantedAuthority(b.getRole()));
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(
				setAuths);
		return Result;
	}

	public UsuariosMedasDAO getUserDao() {
		return userDao;
	}

	public void setUserDao(UsuariosMedasDAO userDao) {
		this.userDao = userDao;
	}

}