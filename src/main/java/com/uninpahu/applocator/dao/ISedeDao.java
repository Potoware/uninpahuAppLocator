package com.uninpahu.applocator.dao;

import org.springframework.data.repository.CrudRepository;

import com.uninpahu.applocator.models.entity.Sede;

public interface ISedeDao extends CrudRepository<Sede,Long>{

	public Sede findByNumero(String numero); 
}
