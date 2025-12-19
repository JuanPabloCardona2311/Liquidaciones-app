package com.example.demo.config;

import com.example.demo.model.Usuario;
import com.example.demo.model.enums.Rol;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public DataLoader(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        usuarioRepository.findByUsername("admin").orElseGet(() -> {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setNombreCompleto("Administrador");
            admin.setRol(Rol.ADMIN);
            admin.setPassword(encoder.encode("admin123"));
            admin.setActivo(true);
            return usuarioRepository.save(admin);
        });
    }
}
