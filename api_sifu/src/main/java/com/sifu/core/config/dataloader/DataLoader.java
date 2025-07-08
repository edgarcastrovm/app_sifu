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

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {
    private final Logger log = LogManager.getLogger(DataLoader.class);

    @Autowired
    private PersonaRepository personaRepository;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private RolRepository rolRepo;
    @Autowired private PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        log.info("Clave generada: {}",encoder.encode("admin"));

        if (rolRepo.findAll().isEmpty()) {
            rolRepo.save(Rol.builder().id(null).nombre("ADMIN").build());
            rolRepo.save(Rol.builder().id(null).nombre("CLIENTE").build());
            rolRepo.save(Rol.builder().id(null).nombre("PRODUCTOR").build());
        }
        Optional<Persona> _persona = personaRepository.findByCedula("0000000001");
        Persona persona = new Persona();
        if (!_persona.isPresent()) {
            persona.setCedula("1000000001");
            persona.setNombre("admin");
            persona.setApellido("admin");
            persona.setCorreo("admin@sifu.com");
            persona.setCelular("098123456");
            personaRepository.save(persona);
        }else {
            persona = _persona.get();
        }

        Optional<Usuario> _usuario = usuarioRepo.findByAlias("admin");
        Usuario usuario = new Usuario();
        if (!_usuario.isPresent()) {
            Rol adminRol = rolRepo.findAll().stream()
                .filter(r -> r.getNombre().equals("ADMIN"))
                .findFirst().orElseThrow();
            log.info("Clave generada: {}",encoder.encode("admin"));
            usuarioRepo.save(new Usuario(null, "admin", encoder.encode("admin"),true,persona, adminRol));
        }else{
            usuario = _usuario.get();
        }

         _persona = personaRepository.findByCedula("0000000002");
        if (!_persona.isPresent()) {
            persona.setCedula("0000000002");
            persona.setNombre("cliente");
            persona.setApellido("cliente");
            persona.setCorreo("cliente@sifu.com");
            persona.setCelular("098233456");
            personaRepository.save(persona);
        }else {
            persona = _persona.get();
        }

        _usuario = usuarioRepo.findByAlias("cliente");
        if (!_usuario.isPresent()) {
            Rol userRol = rolRepo.findAll().stream()
                    .filter(r -> r.getNombre().equals("CLIENTE"))
                    .findFirst().orElseThrow();
            usuarioRepo.save(new Usuario(null, "cliente", encoder.encode("clave123"),true, persona, userRol));
        }

        _persona = personaRepository.findByCedula("3000000001");
        if (!_persona.isPresent()) {
            persona.setCedula("3000000001");
            persona.setNombre("cliente");
            persona.setApellido("cliente");
            persona.setCorreo("productor@sifu.com");
            persona.setCelular("081233456");
            personaRepository.save(persona);
        }else {
            persona = _persona.get();
        }

        _usuario = usuarioRepo.findByAlias("productor");
        if (!_usuario.isPresent()) {
            Rol userRol = rolRepo.findAll().stream()
                    .filter(r -> r.getNombre().equals("PRODUCTOR"))
                    .findFirst().orElseThrow();
            usuarioRepo.save(new Usuario(null, "productor", encoder.encode("clave123"),true, persona, userRol));
        }
    }
}
