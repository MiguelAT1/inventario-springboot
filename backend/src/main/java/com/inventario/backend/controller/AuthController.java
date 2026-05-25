package com.inventario.backend.controller;

import com.inventario.backend.model.Usuario;
import com.inventario.backend.repository.UsuarioRepository;
import com.inventario.backend.security.JwtUtil;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;

    public AuthController(UsuarioRepository usuarioRepository, JwtUtil jwtUtil) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario request) {

        Optional<Usuario> usuarioOpt =
                usuarioRepository.findByUsername(request.getUsername());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuario no existe");
        }

        Usuario usuario = usuarioOpt.get();

        if (!usuario.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Password incorrecta");
        }

        String token = jwtUtil.generarToken(usuario.getUsername(), usuario.getRol());

        return ResponseEntity.ok(new AuthResponse(token, usuario.getRol()));
    }

    static class AuthResponse {
        public String token;
        public String rol;

        public AuthResponse(String token, String rol) {
            this.token = token;
            this.rol = rol;
        }
    }
}