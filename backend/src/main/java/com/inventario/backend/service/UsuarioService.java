package com.inventario.backend.service;

import com.inventario.backend.model.Usuario;
import com.inventario.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario crear(Usuario usuario) {

        validar(usuario);

        if (repository.findByUsername(usuario.getUsername()).isPresent()) {
            throw new RuntimeException("El username ya existe");
        }

        return repository.save(usuario);
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario actualizar(Long id, Usuario nuevo) {

        Usuario existente = buscarPorId(id);

        validar(nuevo);

        existente.setUsername(nuevo.getUsername());
        existente.setPassword(nuevo.getPassword());
        existente.setRol(nuevo.getRol());
        existente.setEstado(nuevo.getEstado());

        return repository.save(existente);
    }

    public void eliminar(Long id) {

        Usuario usuario = buscarPorId(id);

        repository.delete(usuario);
    }

    private void validar(Usuario usuario) {

        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new RuntimeException("El username es obligatorio");
        }

        if (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty()) {
            throw new RuntimeException("La contraseña es obligatoria");
        }

        if (usuario.getRol() == null || usuario.getRol().trim().isEmpty()) {
            throw new RuntimeException("El rol es obligatorio");
        }

        if (usuario.getEstado() == null || usuario.getEstado().trim().isEmpty()) {
            throw new RuntimeException("El estado es obligatorio");
        }
    }
}