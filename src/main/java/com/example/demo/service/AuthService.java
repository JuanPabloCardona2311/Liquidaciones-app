package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, JwtService jwtService, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername())
                .orElse(null);

        if (usuario == null || !usuario.isActivo()) {
            return new LoginResponse(null, null, null, "Usuario no encontrado o inactivo");
        }

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return new LoginResponse(null, null, null, "Contraseña incorrecta");
        }

        String token = jwtService.generateToken(usuario.getUsername(), usuario.getRol().toString());
        return new LoginResponse(token, usuario.getUsername(), usuario.getRol().toString(), "Login exitoso");
    }

    public boolean validateToken(String token) {
        return jwtService.isTokenValid(token);
    }

    public String getUsernameFromToken(String token) {
        return jwtService.extractUsername(token);
    }
}
