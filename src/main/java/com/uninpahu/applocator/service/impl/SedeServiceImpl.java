package com.uninpahu.applocator.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uninpahu.applocator.dao.ISedeDao;
import com.uninpahu.applocator.models.entity.Sede;
import com.uninpahu.applocator.service.ISedeService;

@Service
public class SedeServiceImpl implements ISedeService{

	@Autowired
	private ISedeDao sedeDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Sede> findAll() {
		return (List<Sede>) sedeDao.findAll();
	}

	@Override
	@Transactional
	public Sede save(Sede sede) {
		
		return sedeDao.save(sede);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
	
		sedeDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Sede findById(Long id) {
		
		return sedeDao.findById(id).orElse(null);
	}

	@Override
	public Sede findByNumero(String numero) {
		return sedeDao.findByNumero(numero);
	}
}
