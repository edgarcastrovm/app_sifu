package com.sifu.core.controller.site;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sifu.core.service.AgricultorService;
import com.sifu.core.service.ClienteService;
import com.sifu.core.service.PersonaService;
import com.sifu.core.service.UsuarioService;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.dto.dominio.CrearClienteDto;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Cliente;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Rol;
import com.sifu.core.utils.entity.Usuario;
import org.springframework.security.crypto.password.PasswordEncoder;


@Controller
@RequestMapping("/site")
public class RegistroController {

	@Autowired
    private AgricultorService agricultorService;
	
	@Autowired
    private PersonaService personaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private ClienteService clienteService;
	
	
	
	@GetMapping("/registrarAgricultor")
	public String mostrarFormulario(Model model) {
		model.addAttribute("registro", new CrearAgricultorDto());
	    return "login/registrar-agricultor"; 
	}
	@GetMapping("/registroExitoso")
	public String mostrarRegistroExitoso() {
	    return "exito";
	}
	@GetMapping("/registrarCliente")
	public String mostrarFormularioCliente(Model model) {
		model.addAttribute("registroC", new CrearClienteDto());
	    return "login/registrar-cliente"; 
	}
		
    @PostMapping("/registrarAgricultor")
	public String registrarAgricultor(CrearAgricultorDto registro, Model model) {
		agricultorService.crearAgricultor(registro);
        return "redirect:/site/registroExitoso";
    }
    @PostMapping("/registrarCliente")
	public String registrarCliente(CrearClienteDto registroC, Model model) {
    	clienteService.crearCliente(registroC);
        return "redirect:/site/registroExitoso";
    }
    
    @GetMapping("/verCliente/{id}")
    public String verCliente(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null) {
            return "redirect:/sifu/site";
        }
        model.addAttribute("cliente", cliente);
        return "VisualizarCliente";
    }
    @GetMapping("/verAgricultor/{id}")
    public String verAgricultor(@PathVariable Integer id, Model model) {
    	Agricultor agricultor = agricultorService.obtenerPorId(id);
        if (agricultor == null) {
            return "redirect:/sifu/site";
        }
        model.addAttribute("agricultor", agricultor);
        return "VisualizarAgricultor";
    }
    
	
}
