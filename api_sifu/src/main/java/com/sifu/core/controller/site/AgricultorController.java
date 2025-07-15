package com.sifu.core.controller.site;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sifu.core.service.AgricultorService;
import com.sifu.core.service.AnuncioService;
import com.sifu.core.service.security.CustomIUserDetails;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Anuncio;
import com.sifu.core.utils.entity.Usuario;

@Controller
@RequestMapping("/agricultor")
public class AgricultorController {

	private static final Logger log = LogManager.getLogger(AgricultorController.class);

	@Autowired
	private AnuncioService anuncioService;
	@Autowired
	private AgricultorService agricultorService;

	@GetMapping("/agregar-productos")
	public String agregarProducto() {
		log.info("agregarProducto() called");
		return "agricultor/agregar-productos";
	}

	@GetMapping("/ver-productos")
	public String listarProductos() {
		log.info("listarPorductos() called");
		return "agricultor/ver-productos";
	}
	
	@GetMapping("/validarAgricultor")
	public String validarAgricultor(Authentication auth) {
		log.info("validarAgricultor called");
		CustomIUserDetails userDetails = (CustomIUserDetails) auth.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		String rol = usuario.getRol().getNombre();

		Integer idPersona = usuario.getPersona().getId();

		if ("AGRICULTOR".equalsIgnoreCase(rol)) {
			Optional<Agricultor> agricultorOpt = agricultorService.findByPersonaId(idPersona);
			if (agricultorOpt.isPresent()) {
				return "redirect:/site/crearAnuncio/" + agricultorOpt.get().getId();
			}
		}
		return "redirect:/site/validarAgricultor";

	}
	
	//METODOS DE ANUNCIOS
	@GetMapping("/anuncios")
	public String mostrarFormulario(Model model, Authentication authentication) {
		log.info("mostrarFormulario() called");
		CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
		Usuario usuario = userDetails.getUsuario();
		Integer id = usuario.getPersona().getAgricultor().getId();
		log.info(String.format("Agricultor id: %d", id));

		Anuncio anuncio = new Anuncio();
		Agricultor agricultor = agricultorService.obtenerPorId(id);
		List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultor.getId());

		model.addAttribute("anuncio", anuncio);
		model.addAttribute("agricultor", agricultor);
		model.addAttribute("anunciosGuardados", anuncios);
		model.addAttribute("modoEdicion", false);
		return "agricultor/anuncios";
	}


	 @PostMapping("/crearAnuncio/{id}")
	 public String crearAnuncio(@PathVariable Integer id, @ModelAttribute Anuncio anuncio, Model model) {
	     log.info("crearAnuncio() called");
	     anuncio.setId(null);
	     Agricultor agricultor = agricultorService.obtenerPorId(id);
	     anuncio.setAgricultor(agricultor);
	     anuncio.setFechaCreacion(LocalDateTime.now());

	     anuncioService.crearAnuncio(anuncio);

	     // Recargar datos para mostrar en la misma página
	     List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultor.getId());
	     model.addAttribute("agricultor", agricultor);
	     model.addAttribute("anuncio", new Anuncio()); // limpiar el formulario
	     model.addAttribute("anunciosGuardados", anuncios);
	     model.addAttribute("modoEdicion", false);

	     return "agricultor/anuncios";
	 }

	
	@GetMapping("/editarAnuncio/{id}")
	public String editarAnuncio(@PathVariable Integer id, Model model, @ModelAttribute Anuncio anuncio) {
		log.info("editarAnuncio() called");
		Anuncio anuncioExistente = anuncioService.obtenerPorId(id);
		anuncio.setDescripcion(anuncioExistente.getDescripcion());
		anuncio.setEstado(anuncioExistente.getEstado());
		anuncioService.actualizarAnuncio(id, anuncioExistente);
		return "agricultor/anuncios";
	}

	@GetMapping("/eliminarAnuncio/{id}")
	public String eliminarAnuncio(@PathVariable Integer id, Authentication authentication, Model model) {
	    log.info("eliminarAnuncio() called");
	    anuncioService.eliminarAnuncio(id);

	    // Recuperar agricultorId del usuario autenticado
	    CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
	    Integer agricultorId = userDetails.getUsuario().getPersona().getAgricultor().getId();

	    // Recargar el agricultor y la lista de anuncios
	    Agricultor agricultor = agricultorService.obtenerPorId(agricultorId);
	    List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultorId);

	    // Cargar todos los datos al modelo
	    model.addAttribute("agricultor", agricultor);
	    model.addAttribute("anuncio", new Anuncio());
	    model.addAttribute("anunciosGuardados", anuncios);
	    model.addAttribute("modoEdicion", false);

	    return "agricultor/anuncios";
	}

}
