package com.sifu.core.controller.site;

import com.sifu.core.service.AnuncioService;
import com.sifu.core.service.security.CustomIUserDetails;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Anuncio;
import com.sifu.core.utils.entity.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/site")
public class SiteController {
	
	private static final Logger log = LogManager.getLogger(SiteController.class);
	private final AnuncioService anuncioService;

	public SiteController(AnuncioService anuncioService) {
		this.anuncioService = anuncioService;
	}

	@GetMapping("/")
	public String index() {
		log.info("index() called");
		return "public/index";
	}

	@GetMapping("/home")
	public String home() {
		log.info("home() called");
		return "public/index";
	}

	// Login
	@GetMapping("/login")
	public String login() {
		log.info("login() called");
		return "login/login";
	}

	@GetMapping("/registrar-usuario")
	public String registrarUsuario() {
		log.info("resgitrarUsuario() called");
		return "login/registrar-usuario";
	}

	// Usuarios
	@GetMapping("/formulario-usuario")
	public String formularioUsuario() {
		log.info("formularioUsuario() called");
		return "perfiles/usuarios/formulario-usuario";
	}

	// Productos tienda
	@GetMapping("/shop")
	public String shop() {
		log.info("shop() called");
		return "public/productos";
	}

	// Agricultor
	@GetMapping("/shop/agricultor")
	public String agricultores() {
		log.info("agricultores() called");
		return "public/agricultor";
	}

	// Usuarios
	@PreAuthorize("hasAuthority('CLIENTE')")
	@GetMapping("/shop/mi-carrito")
	public String carrito() {
		log.info("carrito() called");
		return "cliente/carrito";
	}

	@GetMapping("/anuncios")
	public String mostrarAnuncios(Model model) {
		log.info("mostrarFormulario() called");
		List<Anuncio> anuncios = anuncioService.obtenerTodosAnuncios();
		model.addAttribute("anunciosGuardados", anuncios);
		model.addAttribute("modoEdicion", false);
		return "public/anuncios";
	}
}
