package com.uninpahu.applocator.service;

public interface ICifradoService {

	String cifrarContrasenia(String contrasenia);
	
	boolean verificarContrasenia(String contrasenia, String hash);
	
}

