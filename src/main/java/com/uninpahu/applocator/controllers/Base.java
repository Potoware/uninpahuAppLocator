package com.uninpahu.applocator.controllers;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uninpahu.applocator.models.entity.Mensaje;
import com.uninpahu.applocator.models.entity.Usuario;

public class Base {

	private final String TIPO_MENSAJE_ERROR = "error";
	private final String TIPO_MENSAJE_CORRECTO = "correcto";
	
	public boolean usuarioLogueado(Usuario usuario){
		return (usuario != null 
				&& usuario.getId()!= null
				&& usuario.getId()>0);
	}
	
	protected void mostrarMensajeCorrecto(String texto, RedirectAttributes redirect) {
		redirect.addFlashAttribute("mensaje", new Mensaje(TIPO_MENSAJE_CORRECTO,texto));
	}
	
	protected void mostrarMensajeError(String texto, RedirectAttributes redirect) {
		redirect.addFlashAttribute("mensaje", new Mensaje(TIPO_MENSAJE_ERROR,texto));
	}
}
