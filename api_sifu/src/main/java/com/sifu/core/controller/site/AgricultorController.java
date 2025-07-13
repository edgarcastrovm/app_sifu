package com.sifu.core.controller.site;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/site")
//@PreAuthorize("hasRole('AGRICULTOR')")
public class AgricultorController {
	
	@Autowired 
	private AnuncioService anuncioService;
	@Autowired
    private AgricultorService agricultorService;
	
	@GetMapping("/crearAnuncio/{id}")
	public String mostrarFormulario(@PathVariable Integer id,Model model) {
		Anuncio anuncio = new Anuncio();
	    Agricultor agricultor = agricultorService.obtenerPorId(id);
	    List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultor.getId());

	    model.addAttribute("anuncio", anuncio);
	    model.addAttribute("agricultor", agricultor);
	    model.addAttribute("anunciosGuardados", anuncios); 
	    model.addAttribute("modoEdicion", false);
	    return "agricultores/anuncios"; 
	}
	
	@GetMapping("/validarAgricultor")
	public String validarAgricultor(Authentication auth) {
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
	@PostMapping("/crearAnuncio/{id}")
	public String crearAnuncio(@PathVariable Integer id, @ModelAttribute Anuncio anuncio, Model model) {
		anuncio.setId(null);
		Agricultor agricultor = agricultorService.obtenerPorId(id);
	    anuncio.setAgricultor(agricultor);
	    
		anuncioService.crearAnuncio(anuncio);
        return "redirect:/site/crearAnuncio/" + id;
	}
	
	@PostMapping("/seleccionarAnuncio/{id}")
	public String seleccionarAnuncio(@PathVariable Integer id, @ModelAttribute Anuncio anuncio) {
		Anuncio anuncioExistente = anuncioService.obtenerPorId(id);
	    anuncio.setDescripcion(anuncioExistente.getDescripcion());
	    anuncio.setEstado(anuncioExistente.getEstado());
	    return "redirect:/site/editarAnuncio/" + id;
	}
	
	
	@GetMapping("/editarAnuncio/{id}")
	public String editarAnuncio(@PathVariable Integer id, Model model, @ModelAttribute Anuncio anuncio) {
		Anuncio anuncioExistente = anuncioService.obtenerPorId(id);
	    anuncio.setDescripcion(anuncioExistente.getDescripcion());
	    anuncio.setEstado(anuncioExistente.getEstado());
	    anuncioService.actualizarAnuncio(id, anuncioExistente);
	    return "agricultores/anuncios";
	}

	
	@GetMapping("/listarAnuncios/{id}")
	public String listarAnunciosPorAgricultor(@PathVariable Integer id, Model model) {
	    Agricultor agricultor = agricultorService.obtenerPorId(id);
	    List<Anuncio> anuncios = anuncioService.obtenerAnunciosPorAgricultor(agricultor.getId());

	    model.addAttribute("agricultor", agricultor);
	    model.addAttribute("anunciosGuardados", anuncios);

	    return "agricultores/anuncios";
	}
}
