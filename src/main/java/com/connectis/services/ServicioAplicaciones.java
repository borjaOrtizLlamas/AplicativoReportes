package com.connectis.services;

import java.util.List;

import com.connectis.dao.AplicacionesDAO;
import com.connectis.dao.AplicacionesDAOIntf;
import com.connectis.dao.UsuariosMedasDAO;
import com.connectis.model.Aplicaciones;
import com.connectis.model.UsuariosMedas;

public class ServicioAplicaciones {

	private AplicacionesDAOIntf  aplicacionesDao;  

		
	
	public List<String> todosLosNombres(){
		return aplicacionesDao.nombres(); 
	}
	
	public List<String> todosLosNombres(String usuario){
		return aplicacionesDao.nombres(usuario); 
	}


	
	public Aplicaciones unaAplicacion(String nombre){
		return aplicacionesDao.aplicacion(nombre);
	}
	
	
	public boolean isWindows(String nombre){
		Aplicaciones a = aplicacionesDao.aplicacion(nombre); 
		System.out.println(a.toString());
		int i = a.getWindows(); 
		
		if(i == 0){
			return false; 
		} else
		return true;  
	}
	
	public AplicacionesDAOIntf getAplicacionesDao() {
		return aplicacionesDao;
	}


	public void setAplicacionesDao(AplicacionesDAOIntf aplicacionesDao) {
		this.aplicacionesDao = aplicacionesDao;
	}





	
	
	
}
