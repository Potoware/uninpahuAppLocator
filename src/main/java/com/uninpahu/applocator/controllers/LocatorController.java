package com.uninpahu.applocator.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.uninpahu.applocator.models.entity.Sede;
import com.uninpahu.applocator.models.entity.Mensaje;
import com.uninpahu.applocator.models.entity.Salon;
import com.uninpahu.applocator.models.entity.Usuario;
import com.uninpahu.applocator.service.ISalonService;
import com.uninpahu.applocator.service.ISedeService;

@Controller
@SessionAttributes({"usuario","salon","sede"})
public class LocatorController extends Base{

	@Autowired
	private ISalonService salonService;
	
	@Autowired
	private ISedeService sedeService;
	
	@RequestMapping(value="/locator/admin")
	public String admin(Map<String,Object> model,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		model.put("titulo", "Inicio");
		model.put("mensaje", new Mensaje());
		return "locator/admin/base";
	}
	
	@RequestMapping(value="/locator/admin/salones")
	public String listarSalones(Map<String,Object> model,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		if(model.get("mensaje")!=null) {
			System.out.println((Mensaje) model.get("mensaje"));
		}else {
			model.put("mensaje", new Mensaje());
		}
		model.put("titulo", "Inicio");
		List<Salon> salones = salonService.findAll();
		model.put("salones", salones);
		return "locator/admin/salones";
	}
	
	@RequestMapping(value="/locator/admin/sedes")
	public String listarSedes(Map<String,Object> model,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		if(model.get("mensaje")!=null) {
			System.out.println((Mensaje) model.get("mensaje"));
		}else {
			model.put("mensaje", new Mensaje());
		}
		model.put("titulo", "Inicio");
		List<Sede> sedes = sedeService.findAll();
		model.put("sedes", sedes);
		return "locator/admin/sedes";
	}
	
	@RequestMapping(value="/locator/admin/sede")
	public String crearSede(Map <String, Object> model,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		Sede sede = new Sede();
		model.put("titulo","Formulario Sede");
		model.put("sede", sede);
		model.put("mensaje", new Mensaje());
		return "locator/admin/formSede";
	}
	
	@RequestMapping(value="/locator/admin/sede", method=RequestMethod.POST)
	public String guardarSede(@Valid Sede sede, BindingResult result, Model model, SessionStatus status,HttpSession session, RedirectAttributes redirectAttributes) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		if(result.hasErrors() ||
				(sede.getIdSede()==null
				&& sedeService.findByNumero(sede.getNumero())!=null)) {
			Mensaje mensaje = new Mensaje();
			if(result.hasErrors()) {
				model.addAttribute("titulo","Formulario Sede");
			}
			
			if(sede.getIdSede()==null
					&& sedeService.findByNumero(sede.getNumero())!=null) {
				mensaje.setTipo("error");
				mensaje.setMensaje("la sede ya existe");
				model.addAttribute("mensaje",mensaje);
			}
			model.addAttribute("mensaje",mensaje);
			return "locator/admin/formSede";
		}
		
		sedeService.save(sede);
		status.setComplete();
		super.mostrarMensajeCorrecto("Sede guardada correctamente", redirectAttributes);
		return "redirect:sedes";
	}
	
	@RequestMapping(value="/locator/admin/salon")
	public String crearSalon(Map <String, Object> model,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		Salon salon = new Salon();
		model.put("titulo","Formulario Salon");
		model.put("salon", salon);
		
		List<Sede> sedes = sedeService.findAll();
		model.put("sedes", sedes);
		model.put("mensaje", new Mensaje());
		
		return "locator/admin/formSalon";
	}
	
	@RequestMapping(value="/locator/admin/salon", method=RequestMethod.POST)
	public String guardarSalon(@Valid Salon salon, BindingResult result, Model model, SessionStatus status,HttpSession session, RedirectAttributes redirectAttributes) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		if(result.hasErrors()
				||(salon.getIdSalon()==null && salonService.findByNumeroSedeNumero(salon.getNumero(),salon.getSede().getNumero() )!= null)) {
			Mensaje mensaje = new Mensaje();
			if(salon.getIdSalon()==null  && salon.getSede()!=null&& salonService.findByNumeroSedeNumero(salon.getNumero(),salon.getSede().getNumero() )!= null) {
				mensaje.setTipo("error");
				mensaje.setMensaje("el salon ya existe en la sede");
				model.addAttribute("mensaje",mensaje);
			}
			
			model.addAttribute("mensaje",mensaje);
			model.addAttribute("sedes",sedeService.findAll());
			return "locator/admin/formSalon";
		}
		
		if(salon.getIdSalon() ==null 
				|| !salonService.findById(salon.getIdSalon()).getUrlStreetView().equals(salon.getUrlStreetView())
				) {
			salon.setUrlStreetView(obtenerUrlSVW(salon.getUrlStreetView()));
		}
		
		salonService.save(salon);
		status.setComplete();
		super.mostrarMensajeCorrecto("Salon guardado correctamente", redirectAttributes);
		return "redirect:salones";
	}
	
	@RequestMapping(value="/locator/admin/sede/{idSede}")
	public String editarSede(@PathVariable(value="idSede") Long id, Map <String, Object> model,HttpSession session,RedirectAttributes redirectAttrs) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		Sede sede = null;
		if(id>0) {
			sede = sedeService.findById(id);
		}
		else {
			mostrarMensajeError("La sede no existe",redirectAttrs);
			return "redirect:sedes";
		}
		model.put("sede", sede);
		model.put("mensaje", new Mensaje());
		return "locator/admin/formSede";
	}
	
	@RequestMapping(value="/locator/admin/salon/{idSalon}")
	public String editarSalon(@PathVariable(value="idSalon") Long id, Map <String, Object> model,HttpSession session, RedirectAttributes redirectAttrs) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		Salon salon = null;
		if(id>0 && salonService.findById(id) != null) {
			salon = salonService.findById(id);
		}
		else {
			mostrarMensajeError("El salon no existe",redirectAttrs);
			return "redirect:/locator/admin/salones";
		}
		model.put("salon", salon);
		
		List<Sede> sedes = sedeService.findAll();
		model.put("sedes", sedes);
		model.put("mensaje", new Mensaje());
		return "locator/admin/formSalon";
	}
	
	
	@RequestMapping(value="/locator/admin/eliminarSede/{id}")
	public String eliminarSede(@PathVariable(value="id") Long id,RedirectAttributes redirectAttrs,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		
		if(id>0 && salonService.findBySede(id).size()==0) {
			sedeService.deleteById(id);
			mostrarMensajeCorrecto("Salon eliminado correctamente", redirectAttrs);
		}else {
			mostrarMensajeError("Se ha producido un error al eliminar el salon, revise los salones asociadas",redirectAttrs);
		}
		return "redirect:/locator/admin/sedes";
		
	}
	
	@RequestMapping(value="/locator/admin/eliminarSalon/{id}")
	public String eliminarSalon(@PathVariable(value="id") Long id,RedirectAttributes redirectAttrs,HttpSession session) {
		if(!usuarioLogueado((Usuario)session.getAttribute("usuarioSesion"))){
			return "redirect:/usuario/login";
		}
		
		try {
			salonService.deleteById(id);
			mostrarMensajeCorrecto("Salon eliminado correctamente", redirectAttrs);
		}catch(Exception e) {
			mostrarMensajeError("Se ha producido un error al eliminar el salon",redirectAttrs);
		}
			return "redirect:/locator/admin/salones";
		
	}
	
	@RequestMapping(value={"/locator",""})
	public String inicio(Map<String,Object> model) {
		model.put("titulo", "Inicio");
		List<Salon> salones = salonService.findAll();
		model.put("salones", salones);
		model.put("salon",new Salon());
		if(model.get("mensaje")!=null) {
			System.out.println((Mensaje) model.get("mensaje"));
		}else {
			model.put("mensaje", new Mensaje());
		}
		return "locator/locator";
	}
	
	@RequestMapping(value={"/locator",""}, method=RequestMethod.POST)
	public String cargarSalon(Salon criterio, Model model, SessionStatus status, RedirectAttributes redirectAttrs) {
		Mensaje mensaje= new Mensaje();
		Salon salon = new Salon();
		System.out.println(criterio.getCriterio());
		if(criterio.getCriterio().toLowerCase().equals("Como llegar desde el parqueadero a la sede 1".toLowerCase())) {
			salon.setEsRuta(true);
			salon.setIdSalon(999L);
			salon.setCoordenadas("");
			salon.setUrlStreetView("https://www.google.com/maps/embed?pb=!4v1665989695020!6m8!1m7!1sCAoSLEFGMVFpcE0tbDdPZHhsd2V1NlEwR1ZOSjhySlE4RDMtdDVpUnZCaE03SzQt!2m2!1d4.629659552288262!2d-74.07023772188658!3f194.1812591552734!4f-3.4398269653320312!5f1.8581076242228396");
		}
		else if(criterio.getCriterio()!=null && criterio.getCriterio().contains("-")) {
			salon = salonService.findByNumeroSedeNumero(criterio.getCriterio().split("-")[1],criterio.getCriterio().split("-")[0]);
			if(salon == null) {
				salon = new Salon();
			}
		}else {
			mostrarMensajeError("Has ingresado un formato invalido de texto", redirectAttrs);
			return "redirect:locator/";
		}
			
		
		if(salon.getIdSalon()==null) {
			mostrarMensajeError("No se encontro el salon", redirectAttrs);
			model.addAttribute("salon",salon);
			return "redirect:locator";
		}else {
			salon.setCoordenadas(salon.getCoordenadas().replaceAll("/", ","));
		}
		model.addAttribute("salon",salon);
		mensaje.setMensaje("Se encontro el salon");
		mensaje.setTipo(salon.getIdSalon().equals(999L)?"personalizadoRuta":"personalizado");
		model.addAttribute("mensaje",mensaje);
		
		List<Salon> salones = salonService.findAll();
		model.addAttribute("salones", salones);
		
		return "locator/locator";
	}
	
	@RequestMapping(value={"/locatorOld"})
	public String inicioold(Map<String,Object> model) {
		model.put("titulo", "Inicio");
		return "locator/locatorOld";
	}
	
	private String obtenerUrlSVW(String url) {
		if(url.equals("")) {
			return "";
		}
		return  url.split("\"")[1];
	}
	@CrossOrigin(origins = "*")
	@PostMapping("/api/ajax/search")
	public ResponseEntity<HashMap> buscarSalon(@RequestParam("arr") String criterio) {
		HashMap<String, Object> response = new HashMap<String, Object>();
		Salon salon = new Salon();

		if(criterio!=null && criterio.contains("-")) {
			salon = salonService.findByNumeroSedeNumero(criterio.split("-")[1],criterio.split("-")[0]);
		}else {
			List<Salon> salones=salonService.findAll();
			salon = salones.get((int)(Math.random()*salones.size()));
		}
		
		if(salon == null ||salon.getIdSalon()==null) {
			response.put("encontrado",false);
		}else {
			response.put("encontrado",true);
			response.put("salon", salon.getSede().getNumero()+"-"+salon.getNumero());
		}
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("locator/{parametro}")
    public String paginaComun(
            @PathVariable("parametro") String parametro,
            Model model,
            RedirectAttributes redirectAttrs) {

		Mensaje mensaje= new Mensaje();
		Salon salon = new Salon();
		System.out.println(parametro);
		if(parametro!=null && parametro.contains("-")) {
			salon = salonService.findByNumeroSedeNumero(parametro.split("-")[1],parametro.split("-")[0]);
			if(salon == null) {
				salon = new Salon();
			}
		}else {
			mostrarMensajeError("Has ingresado un formato invalido de texto", redirectAttrs);
			return "redirect:locator/";
		}
			
		
		if(salon.getIdSalon()==null) {
			mostrarMensajeError("No se encontro el salon", redirectAttrs);
			model.addAttribute("salon",salon);
			return "redirect:locator";
		}else {
			salon.setCoordenadas(salon.getCoordenadas().replaceAll("/", ","));
		}
		model.addAttribute("salon",salon);
		mensaje.setMensaje("Se encontro el salon");
		mensaje.setTipo(salon.getIdSalon().equals(999L)?"personalizadoRuta":"personalizado");
		model.addAttribute("mensaje",mensaje);
		
		List<Salon> salones = salonService.findAll();
		model.addAttribute("salones", salones);
		
		return "locator/locator";
    }
}
