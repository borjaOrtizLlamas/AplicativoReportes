package com.connectis.model;

public class ModeloCambio {

	
	private int id; 
	private int estado;

	public ModeloCambio(){}
	public ModeloCambio(int i){
		id=i;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	} 
	
	
}
