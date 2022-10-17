package com.uninpahu.applocator.service;

import java.util.List;

import com.uninpahu.applocator.models.entity.Sede;

public interface ISedeService {

	public List<Sede> findAll();
	
	public Sede save(Sede sede);
	
	public Sede findById(Long id);

	public void deleteById(Long id);
	
	public Sede findByNumero(String numero);
}
