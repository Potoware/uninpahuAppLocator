package com.uninpahu.applocator.models.entity;

public class Mensaje {
	
	private String tipo;
	private String mensaje;
	
	public Mensaje() {
		this.tipo="";
		this.mensaje="";
	}
	
	public Mensaje(String tipo, String mensaje) {
		this.mensaje = mensaje;
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
