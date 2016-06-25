package com.connectis.model;

import java.util.Set;

public class Aplicaciones {

	private int id;
	private String nombre;
	private String funcional; 
	private int windows; 
	private String emailFuncional; 

	
	public String getEmailFuncional() {
		return emailFuncional;
	}

	public void setEmailFuncional(String emailFuncional) {
		this.emailFuncional = emailFuncional;
	}

	public String getFuncional() {
		return funcional;
	}

	public void setFuncional(String funcional) {
		this.funcional = funcional;
	}

	public int getWindows() {
		return windows;
	}

	public void setWindows(int windows) {
		this.windows = windows;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!this.getClass().equals(obj.getClass()))
			return false;

		Aplicaciones obj2 = (Aplicaciones) obj;
		
		if ((this.id == obj2.getId()) && (this.nombre.equals(obj2.getNombre()))) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		int tmp = 0;
		tmp = (id + nombre).hashCode();
		return tmp;
	}

}
