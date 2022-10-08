package com.uninpahu.applocator.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.uninpahu.applocator.models.entity.Usuario;
import com.uninpahu.applocator.service.ICifradoService;
import com.uninpahu.applocator.service.IUsuarioService;

@Controller
@SessionAttributes("usuario")
public class UsuarioController {
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ICifradoService cifradoService;
	
	@RequestMapping(value ={"/usuario/login"})
	public String login(Map <String, Object> model) {
		model.put("titulo", "Inicio de sesión");
		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		return "usuario/login";
		
	}
	
	@RequestMapping(value = "/login/verificar",method=RequestMethod.POST)
	public String validarLogin(Usuario usuario, Model model, SessionStatus status, HttpSession session) {
		boolean credencialesCorrectas = false;
		
		if(usuario!=null && usuario.getUsuario()!=null && usuario.getContrasenia()!=null) {
			
			Usuario usuarioConsultado = usuarioService.findByUsuario(usuario.getUsuario());
			
			if(usuarioConsultado !=null 
					&& cifradoService.verificarContrasenia(usuario.getContrasenia(),usuarioConsultado.getContrasenia())) {
				credencialesCorrectas=true;
				usuarioConsultado.setContrasenia(null);

				session.setAttribute("usuarioSesion", usuarioConsultado);
			}
		}
		
		if(!credencialesCorrectas) {
			return "redirect:/usuario/login";
		}
		status.setComplete();
		return "inicio";
		
		
	}
	
	@RequestMapping(value = "/locator")
	public String inicio(Map <String, Object> model) {
		model.put("titulo", "");
		return "locator/locator";
		
		
	}

}
