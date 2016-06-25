package com.connectis.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.connectis.model.Aplicaciones;

public interface AplicacionesDAOIntf {
	
	@Transactional
	public List<String> nombres(); 
	
	@Transactional
	public Aplicaciones aplicacion(String nombre); 
	
	@Transactional
	public List<String> nombres(String usuario); 

}
