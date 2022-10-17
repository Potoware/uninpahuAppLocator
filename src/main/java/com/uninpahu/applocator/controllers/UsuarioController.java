package com.uninpahu.applocator.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uninpahu.applocator.models.entity.Mensaje;
import com.uninpahu.applocator.models.entity.Usuario;
import com.uninpahu.applocator.service.ICifradoService;
import com.uninpahu.applocator.service.IUsuarioService;

@Controller
@SessionAttributes("usuario")
public class UsuarioController extends Base{
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private ICifradoService cifradoService;
	
	@RequestMapping(value ={"/usuario/login"})
	public String login(Map <String, Object> model,HttpSession session) {
		model.put("titulo", "Inicio de sesión");
		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		session.removeAttribute("usuarioSesion");
		return "usuario/login";
		
	}
	
	@RequestMapping(value = "/login/verificar",method=RequestMethod.POST)
	public String validarLogin(Usuario usuario, Model model, SessionStatus status, HttpSession session) {
		boolean credencialesCorrectas = false;
		Usuario usuarioConsultado = null;
		if(usuario!=null && usuario.getUsuario()!=null && usuario.getContrasenia()!=null) {
			
			usuarioConsultado = usuarioService.findByUsuario(usuario.getUsuario());
			
			if(usuarioConsultado !=null 
					&& cifradoService.verificarContrasenia(usuario.getContrasenia(),usuarioConsultado.getContrasenia())) {
				credencialesCorrectas=true;
				usuarioConsultado.setContrasenia(null);
				
				
			}
		}
		session.setAttribute("usuarioSesion", usuarioConsultado);
		if(!credencialesCorrectas) {
			return "redirect:/usuario/login";
		}
		status.setComplete();
		return "redirect:/locator/admin";
		
		
	}
	
	@RequestMapping(value="/locator/admin/usuarios")
	public String listarUsuarios(Map<String,Object> model,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		if(model.get("mensaje")!=null) {
			System.out.println((Mensaje) model.get("mensaje"));
		}else {
			model.put("mensaje", new Mensaje());
		}
		model.put("titulo", "Inicio");
		List<Usuario> usuarios=usuarioService.findAll();
		model.put("usuarios", usuarios);
		return "locator/admin/usuarios";
	}

	@RequestMapping(value="/locator/admin/usuario")
	public String crearUsuario(Map <String, Object> model,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		Usuario usuario = new Usuario();
		model.put("titulo","Formulario Usuario");
		model.put("usuario", usuario);
		model.put("mensaje", new Mensaje());
		return "locator/admin/formUsuario";
	}
	
	@RequestMapping(value="/locator/admin/usuario", method=RequestMethod.POST)
	public String guardarUsuario(@Valid Usuario usuario, BindingResult result, Model model, SessionStatus status,HttpSession session, RedirectAttributes redirectAttrs) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		
		if(result.hasErrors()
				||(usuario.getId()==null 
				&& usuarioService.findByUsuario(usuario.getUsuario())!=null)) {
			Mensaje mensaje = new Mensaje();
			if(result.hasErrors()) {
				model.addAttribute("titulo","Formulario Usuario");
			}
			
			if(usuario.getId()==null 
					&& usuarioService.findByUsuario(usuario.getUsuario())!=null) {
				mensaje.setTipo("error");
				mensaje.setMensaje("el usuario ya existe");
				model.addAttribute("mensaje",mensaje);
			}
			model.addAttribute("mensaje",mensaje);
			return "locator/admin/formUsuario";
		}
		
		if(usuario.getId()==null 
				&& usuarioService.findByUsuario(usuario.getUsuario())!=null) {
			
		}
		if(usuario.getId()==null
				|| !usuarioService.findById(usuario.getId()).getContrasenia().equals(usuario.getContrasenia())) {
			usuario.setContrasenia(cifradoService.cifrarContrasenia(usuario.getContrasenia()));
		}
		
		usuarioService.save(usuario);
		super.mostrarMensajeCorrecto("Usuario guardado correctamente", redirectAttrs);
		status.setComplete();
		
		return "redirect:usuarios";
	}
	
	@RequestMapping(value="/locator/admin/usuario/{id}")
	public String editarUsuario(@PathVariable(value="id") Long id, Map <String, Object> model,HttpSession session, RedirectAttributes redirectAttrs) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		Usuario usuario = null;
		if(id>0) {
			usuario = usuarioService.findById(id);
		}
		else {
			mostrarMensajeError("El usuario no existe",redirectAttrs);
			return "redirect:listarUsuarios";
		}
		model.put("usuario", usuario);
		model.put("titulo", "Editar Usuario");
		model.put("mensaje", new Mensaje());
		return "locator/admin/formUsuario"; 
	}
	
	@RequestMapping(value="/locator/admin/eliminarUsuario/{id}")
	public String eliminarUsuario(@PathVariable(value="id") Long id,RedirectAttributes redirectAttrs,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		
		try {
			if(usuarioService.findAll().size()==1) {
				throw new Exception("debe haber al menos 1 usuario");
			}
			usuarioService.deleteById(id);
			mostrarMensajeCorrecto("Usuario eliminado Correctamente", redirectAttrs);
		}catch(Exception e) {
			mostrarMensajeError("Se ha producido un error al eliminar el usuario, " + e.getMessage(),redirectAttrs);
		}
			return "redirect:/locator/admin/usuarios";
		
	}
	

	

}
