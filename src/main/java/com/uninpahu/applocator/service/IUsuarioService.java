package com.uninpahu.applocator.service;

import java.util.List;

import com.uninpahu.applocator.models.entity.Usuario;

public interface IUsuarioService {

	public List<Usuario> findAll();
	
	public Usuario save(Usuario usuario);
	
	public Usuario findById(Long id);
	
	public Usuario findByUsuario(String usuario);

	public void deleteById(Long id);
}
