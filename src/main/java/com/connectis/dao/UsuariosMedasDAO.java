package com.connectis.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.connectis.model.UsuariosMedas;
import com.connectis.model.UserRole;

public interface UsuariosMedasDAO {

	@Transactional
	public UsuariosMedas extraerDatos(String Usuario); 
	
	@Transactional
	public List<UserRole> findByUserNamerRoles(String username); 
	
	@Transactional
	public void anadirAplicacion(String usuario, int aplicacion); 
	
	
	@Transactional
	public List<UsuariosMedas> usuariosMedas(); 
	
	@Transactional
	public void crearUsuario(UsuariosMedas usuario); 
	
	@Transactional
	public void crearRoles(UsuariosMedas usuario); 

	
	
}
