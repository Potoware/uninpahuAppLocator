package com.uninpahu.applocator.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.uninpahu.applocator.models.entity.Salon;

public interface ISalonDao extends CrudRepository<Salon,Long>{

	public List<Salon> findBySedeIdSede(Long id);
	
	public Salon findByNumeroAndSedeNumero(String numero, String numero2); 
}
