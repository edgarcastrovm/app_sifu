package com.sifu.core.controller.site;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/site")
public class SiteController {
	
	private static final Logger log = LogManager.getLogger(SiteController.class);

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

}
