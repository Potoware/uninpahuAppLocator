package com.uninpahu.applocator.service;

import java.util.List;

import com.uninpahu.applocator.models.entity.Salon;


public interface ISalonService {

	public List<Salon> findAll();
	
	public Salon save(Salon salon);
	
	public Salon findById(Long id);
	
	public void deleteById(Long id);
	
	public List<Salon> findBySede(Long idSede);
	
	public Salon findByNumeroSedeNumero(String numeroSalon,String numeroSede);
	
}
