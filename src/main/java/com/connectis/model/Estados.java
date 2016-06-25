package com.connectis.model;

public enum Estados{
	
	PENDIENTEDEREVISION(0,"Pendiente de revision"),
	SINENVIAR(1,"Sin enviar"),
	PORCOMPLETAR(2,"Por completar"),
	ENPROCESO(3,"En proceso"),
	PRECAMBIO(4,"Pre-Cambio"),
	CERRADA(5,"Cerrada"); 
	
	
	private int id; 
	private String nombre; 
	
	private Estados(int id, String nombre){
		this.id=id; 
		this.nombre=nombre;
	}

	public int getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}

	public static Estados estadoSegunId(String b){
		Estados a = null; 
		switch (b) {
		case "0":
			a = Estados.PENDIENTEDEREVISION;
			break;
		case "1":
			a = Estados.SINENVIAR;
			break;
		case "2":
			a = Estados.PORCOMPLETAR;
			break;
		case "3":
			a = Estados.ENPROCESO;
			break;
		case "4":
			a = Estados.PRECAMBIO;
			break;
		case "5":
			a = Estados.CERRADA;
			break;
		default:
			break;
		}
		return a; 
	}
	
	
	
	
}
