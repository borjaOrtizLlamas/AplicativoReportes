package com.connectis.model;

import java.util.Set;

public class UsuariosEnvio {

	private String id; 
	private String  nombre; 
	private String correo; 
	private String telefono; 
	private String soporte;
	private Set UsuariosEnvio; 

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getSoporte() {
		return soporte;
	}
	public void setSoporte(String soporte) {
		this.soporte = soporte;
	}
	public Set getUsuariosEnvio() {
		return UsuariosEnvio;
	}
	public void setUsuariosEnvio(Set usuariosEnvio) {
		UsuariosEnvio = usuariosEnvio;
	} 
	
	
	
	
}
