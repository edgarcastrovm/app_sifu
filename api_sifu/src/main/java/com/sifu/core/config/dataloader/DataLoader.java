package com.sifu.core.config.dataloader;

import com.sifu.core.repo.RolRepository;
import com.sifu.core.repo.UsuarioRepository;
import com.sifu.core.utils.entity.Rol;
import com.sifu.core.utils.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private RolRepository rolRepo;
    @Autowired private PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (rolRepo.findAll().isEmpty()) {
            rolRepo.save(new Rol(null, "ADMIN"));
            rolRepo.save(new Rol(null, "CLIENTE"));
            rolRepo.save(new Rol(null, "PRODUCTOR"));
        }

        if (usuarioRepo.findByUsername("admin").isEmpty()) {
            Rol adminRol = rolRepo.findAll().stream()
                .filter(r -> r.getNombre().equals("ADMIN"))
                .findFirst().orElseThrow();
            usuarioRepo.save(new Usuario(null, "admin", encoder.encode("admin"), true, adminRol));
        }

        if (usuarioRepo.findByUsername("cliente").isEmpty()) {
            Rol userRol = rolRepo.findAll().stream()
                    .filter(r -> r.getNombre().equals("CLIENTE"))
                    .findFirst().orElseThrow();
            usuarioRepo.save(new Usuario(null, "cliente", encoder.encode("clave123"), true, userRol));
        }

        if (usuarioRepo.findByUsername("productor").isEmpty()) {
            Rol userRol = rolRepo.findAll().stream()
                    .filter(r -> r.getNombre().equals("PRODUCTOR"))
                    .findFirst().orElseThrow();
            usuarioRepo.save(new Usuario(null, "productor", encoder.encode("clave123"), true, userRol));
        }
    }
}
