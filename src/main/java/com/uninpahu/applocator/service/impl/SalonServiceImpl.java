package com.uninpahu.applocator.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uninpahu.applocator.dao.ISalonDao;
import com.uninpahu.applocator.models.entity.Salon;
import com.uninpahu.applocator.service.ISalonService;

@Service
public class SalonServiceImpl implements ISalonService{

	@Autowired
	private ISalonDao salonDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Salon> findAll() {
		return (List<Salon>) salonDao.findAll();
	}

	@Override
	@Transactional
	public Salon save(Salon salon) {
		
		return salonDao.save(salon);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
	
		salonDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Salon findById(Long id) {
		
		return salonDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Salon> findBySede(Long idSede) {
		
		return salonDao.findBySedeIdSede(idSede);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Salon findByNumeroSedeNumero(String numeroSalon,String numeroSede){
		return salonDao.findByNumeroAndSedeNumero(numeroSalon, numeroSede);
	}
}
