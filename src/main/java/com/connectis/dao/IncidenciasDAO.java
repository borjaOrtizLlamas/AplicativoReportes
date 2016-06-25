package com.connectis.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.connectis.model.Peticiones;

@Transactional
public interface IncidenciasDAO {

	public void guardarIncidencia(Peticiones incidencia); 
	public List<Peticiones> todasLasIncidencias(); 
	public Peticiones unaIndicenciaIncidencias(int id); 
	public void borrarIncidencia(Peticiones incidencia); 
	public void modificarIncidencia(Peticiones incidencia); 
	public int guardarIncidencia1(Peticiones incidencia); 
	public void cambiarEstadoPeticion(int id, int  estado); 
	public void edicionUsuario(Peticiones peticion);
	public List<Peticiones> listaPorUsuario(String usuario); 
	public String correosEnCC(Peticiones incidencia); 
	public List<Peticiones> todasLasIncidencias(String busqueda); 
	public List<String> correosIncidencia(int busqueda); 

	
}
