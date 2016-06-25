package com.connectis.model;

public class UsuariosMedas {

	private String usuario; 
	private String contrasena;
	private int valido; 
	private String nombre; 
	private int telefono; 
	private int extension; 
	private int movil; 
	private String email;
	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getContrasena() {
		return contrasena;
	}
	
	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	

	
	public int getValido() {
		return valido;
	}
	public void setValido(int valido) {
		this.valido = valido;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getTelefono() {
		return telefono;
	}
	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}
	public int getExtension() {
		return extension;
	}
	public void setExtension(int extension) {
		this.extension = extension;
	}
	public int getMovil() {
		return movil;
	}
	public void setMovil(int movil) {
		this.movil = movil;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "UsuariosMedas [usuario=" + usuario + ", contrasena="
				+ contrasena + ", valido=" + valido + ", nombre=" + nombre
				+ ", telefono=" + telefono + ", extension=" + extension
				+ ", movil=" + movil + ", email=" + email + "]";
	} 
	
	
	
	
	
	
}