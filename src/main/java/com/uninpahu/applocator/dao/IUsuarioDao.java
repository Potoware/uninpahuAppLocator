package com.uninpahu.applocator.dao;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import com.uninpahu.applocator.models.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long>{
	
	public Usuario findByUsuario(String usuario);
}
