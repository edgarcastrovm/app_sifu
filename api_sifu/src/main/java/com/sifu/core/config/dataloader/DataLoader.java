package com.sifu.core.config.dataloader;

import com.sifu.core.repo.PersonaRepository;
import com.sifu.core.repo.RolRepository;
import com.sifu.core.repo.UsuarioRepository;
import com.sifu.core.utils.entity.Persona;
import com.sifu.core.utils.entity.Rol;
import com.sifu.core.utils.entity.Usuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements CommandLineRunner {
    private final Logger log = LogManager.getLogger(DataLoader.class);

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private RolRepository rolRepo;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("DataLoader start");
        log.info("Insertando roles");
        Rol rolAdmin = rolRepo.findByNombre("ADMIN").orElseGet(() ->
                rolRepo.save(Rol.builder().nombre("ADMIN").build()));

        Rol rolCliente = rolRepo.findByNombre("CLIENTE").orElseGet(() ->
                rolRepo.save(Rol.builder().nombre("CLIENTE").build()));

        Rol rolAgricultor = rolRepo.findByNombre("AGRICULTOR").orElseGet(() ->
                rolRepo.save(Rol.builder().nombre("AGRICULTOR").build()));

        log.info("Termina insert roles");
        log.info("Inicia insertando usuarios");
        // Crear persona y usuario admin
        crearPersonaYUsuario("1000000001", "admin", "admin@sifu.com", "098123456", "admin", "admin", rolAdmin);
        // Crear persona y usuario cliente
        crearPersonaYUsuario("2000000001", "cliente", "cliente@sifu.com", "098233456", "cliente", "clave123", rolCliente);
        // Crear persona y usuario agricultor
        crearPersonaYUsuario("3000000001", "agricultor", "agricultor@sifu.com", "081233456", "agricultor", "clave123", rolAgricultor);
        log.info("Termina insert usuarios");
    }

    private void crearPersonaYUsuario(String cedula, String nombre, String correo, String celular,
                                      String alias, String clave, Rol rol) {
        try {
            Persona persona = personaRepository.findByCedula(cedula).orElseGet(() -> {
                Persona p = new Persona();
                p.setCedula(cedula);
                p.setNombre(nombre);
                p.setApellido(nombre);
                p.setCorreo(correo);
                p.setCelular(celular);
                return personaRepository.save(p);
            });
            log.info("Persona con cedula " + cedula + " registrado");

            if (usuarioRepo.findByAlias(alias).isEmpty()) {
                String encodedPassword = encoder.encode(clave);
                usuarioRepo.save(new Usuario(null, alias, encodedPassword, true, persona, rol));
            }
            log.info("Persona con alias " + alias + " registrado");
        } catch (Exception e) {
            log.error("Error insertando usuario:{}", e.getMessage());
        }
    }
}
