package com.sifu.core.controller.site;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.sifu.core.service.AgricultorService;
import com.sifu.core.service.ClienteService;
import com.sifu.core.service.PersonaService;
import com.sifu.core.service.UsuarioService;
import com.sifu.core.service.security.CustomIUserDetails;
import com.sifu.core.utils.Utils;
import com.sifu.core.utils.dto.dominio.ActualizarClienteDto;
import com.sifu.core.utils.dto.dominio.CrearAgricultorDto;
import com.sifu.core.utils.dto.dominio.CrearClienteDto;
import com.sifu.core.utils.entity.Agricultor;
import com.sifu.core.utils.entity.Cliente;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Usuario;

import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/site")
public class RegistroController {

    private static final Logger log = LogManager.getLogger(RegistroController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgricultorService agricultorService;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/registrarAgricultor")
    public String mostrarFormulario(Model model) {
        model.addAttribute("registro", new CrearAgricultorDto());
        
        model.addAttribute("provincias", Utils.obtenerProvincias());
        model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());
        return "login/registrar-agricultor";
    }

    @GetMapping("/registroExitoso")
    public String mostrarRegistroExitoso() {
        return "exito";
    }

    @GetMapping("/registrarCliente")
    public String mostrarFormularioCliente(Model model) {
        model.addAttribute("registroC", new CrearClienteDto());
        
        model.addAttribute("provincias", Utils.obtenerProvincias());
        model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());
        return "login/registrar-cliente";
    }

    @PostMapping("/registrarAgricultor")
    public String registrarAgricultor(@Valid @ModelAttribute("registro") CrearAgricultorDto registro, Model model, BindingResult result) {
        //agricultorService.crearAgricultor(registro);
        //return "redirect:/site/registroExitoso";
    	if (result.hasErrors()) {
            model.addAttribute("provincias", Utils.obtenerProvincias());
            model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());
            return "login/registrar-agricultor";
        }
    	 try {
    	        agricultorService.crearAgricultor(registro);
    	        return "redirect:/site/registroExitoso";
    	    } catch (IllegalArgumentException e) {
    	        // Mensaje general
    	        model.addAttribute("error", e.getMessage());
    	        // Mantener provincias y cantones para que no se pierda al recargar la vista
    	        model.addAttribute("provincias", Utils.obtenerProvincias());
    	        model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());
    	        return "login/registrar-agricultor";
    	    }
    }

    @PostMapping("/registrarCliente")
    public String registrarCliente(@Valid @ModelAttribute("registroC") CrearClienteDto registroC,
            BindingResult result, Model model) {
        //clienteService.crearCliente(registroC);
    	//return "redirect:/site/registroExitoso";
    	if (result.hasErrors()) {
            model.addAttribute("provincias", Utils.obtenerProvincias());
            model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());
            return "login/registrar-cliente";
        }
    	 try {
    	        clienteService.crearCliente(registroC);
    	        return "redirect:/site/registroExitoso";
    	    } catch (IllegalArgumentException e) {
    	        // Mensaje general
    	        model.addAttribute("error", e.getMessage());
    	        // Mantener provincias y cantones para que no se pierda al recargar la vista
    	        model.addAttribute("provincias", Utils.obtenerProvincias());
    	        model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());
    	        return "login/registrar-cliente";
    	    }
        
        
    }

    @GetMapping("/verCliente/{id}")
    public String verCliente(@PathVariable Integer id, Model model) {
        Cliente cliente = clienteService.obtenerPorId(id);
        if (cliente == null) {
            return "redirect:/sifu/site";
        }

        ActualizarClienteDto dto = new ActualizarClienteDto();
        Persona persona = cliente.getPersona();

        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setCedula(persona.getCedula());
        dto.setCorreo(persona.getCorreo());
        dto.setCelular(persona.getCelular());
        dto.setProvincia(persona.getProvincia());
        dto.setCanton(persona.getCanton());
        dto.setEntidadSFL(cliente.getEntidadSFL());
        dto.setId(cliente.getId());
        // model.addAttribute("cliente", cliente.getId());
        model.addAttribute("clienteDto", dto);
        model.addAttribute("provincias", Utils.obtenerProvincias());
        model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());

        return "/cliente/VisualizarCliente";
    }

    @GetMapping("/verAgricultor/{id}")
    public String verAgricultor(@PathVariable Integer id, Model model) {

        Agricultor agricultor = agricultorService.obtenerPorId(id);
        if (agricultor == null) {
            return "redirect:/sifu/site";
        }

        CrearAgricultorDto dto = new CrearAgricultorDto();
        Persona persona = agricultor.getPersona();

        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setCedula(persona.getCedula());
        dto.setCorreo(persona.getCorreo());
        dto.setCelular(persona.getCelular());
        dto.setProvincia(persona.getProvincia());
        dto.setCanton(persona.getCanton());
        dto.setId(agricultor.getId());
        model.addAttribute("agricultorDto", dto);
        model.addAttribute("provincias", Utils.obtenerProvincias());
        model.addAttribute("cantonesPorProvincia", Utils.cantonPorProvincia());

        return "/agricultor/VisualizarAgricultor";
    }

    @GetMapping("/perfil")
    public String redirigirSegunRol(Authentication authentication) throws JsonProcessingException {

        CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        String rol = usuario.getRol().getNombre();
        Integer idPersona = usuario.getPersona().getId();
        log.info("Usuario logueago: {}", objectMapper.writeValueAsString(usuario));
        log.info("Rol logueago:'{}'", userDetails.getAuthorities());
        log.info("Rol logueago:'{}'", rol);

        if ("AGRICULTOR".equalsIgnoreCase(rol)) {
            Optional<Agricultor> agricultorOpt = agricultorService.findByPersonaId(idPersona);
            if (agricultorOpt.isPresent()) {
                return "redirect:/site/verAgricultor/" + agricultorOpt.get().getId();
            }
        }

        if ("CLIENTE".equalsIgnoreCase(rol)) {
            Optional<Cliente> clienteOpt = clienteService.findByPersonaId(idPersona);
            log.info("Cliente logueago: {}", objectMapper.writeValueAsString(clienteOpt.get()));
            if (clienteOpt.isPresent()) {
                return "redirect:/site/verCliente/" + clienteOpt.get().getId();
            }
        }
        return "redirect:/site";
    }

    @PostMapping("/verCliente/{id}")
    public String actualizarCliente(@PathVariable Integer id, @ModelAttribute("clienteDto") ActualizarClienteDto dto, Model model) {
        clienteService.actualizarCliente(id, dto);

        
        return "redirect:/site/verCliente/" + id;
    }

    @PostMapping("/verAgricultor/{id}")
    public String actualizarAgricultor(@PathVariable Integer id, @ModelAttribute("agricultorDto") CrearAgricultorDto dto, Model model) {
        agricultorService.actualizarAgricultor(id, dto);

        return "redirect:/site/verAgricultor/" + id;
    }

    @GetMapping("/cambiarClave")
    public String mostrarFormCambioClave(Model model, Authentication authentication) {
        CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();
        //Integer idUser = usuario.getId();

        model.addAttribute("usuario", usuario);
        return "cambiarClave";
    }

    @PostMapping("/cambiarClave")
    public String cambiarClave(@RequestParam("claveActual") String claveActual, @RequestParam("nuevaClave") String nuevaClave, Authentication authentication, Model model) {

        CustomIUserDetails userDetails = (CustomIUserDetails) authentication.getPrincipal();
        Usuario usuario = userDetails.getUsuario();

        if (!passwordEncoder.matches(claveActual, usuario.getClave())) {
            model.addAttribute("error", "La clave actual es incorrecta");
            model.addAttribute("usuario", usuario);
            return "cambiarClave";
        }
        usuario.setClave(nuevaClave);
        usuarioService.actualizarClave(usuario.getId(), usuario);

        model.addAttribute("mensaje", "Clave actualizada exitosamente");

        return "redirect:/site/login";
    }


}
