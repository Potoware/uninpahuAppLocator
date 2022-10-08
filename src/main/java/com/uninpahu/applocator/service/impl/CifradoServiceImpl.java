package com.uninpahu.applocator.service.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.uninpahu.applocator.service.ICifradoService;

@Service
public class CifradoServiceImpl implements ICifradoService{

	@Override
	public String cifrarContrasenia(String contrasenia) {	
		return BCrypt.hashpw(contrasenia, BCrypt.gensalt(10)) ;
	}

	@Override
	public boolean verificarContrasenia(String contrasenia, String hash) {
		return BCrypt.checkpw(contrasenia, hash);
	}

}