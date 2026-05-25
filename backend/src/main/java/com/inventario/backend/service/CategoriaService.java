package com.inventario.backend.service;

import com.inventario.backend.model.Categoria;
import com.inventario.backend.repository.CategoriaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public Categoria crear(Categoria categoria) {

        validar(categoria);

        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listar() {

        return categoriaRepository.findAll();
    }

    public Categoria buscarPorId(Long id) {

        return categoriaRepository.findById(id).orElse(null);
    }

    public Categoria actualizar(Long id, Categoria nuevo) {

        Categoria categoria = buscarPorId(id);

        if (categoria == null) {
            return null;
        }

        validar(nuevo);

        categoria.setNombre(nuevo.getNombre());
        categoria.setDescripcion(nuevo.getDescripcion());
        categoria.setEstado(nuevo.getEstado());

        return categoriaRepository.save(categoria);
    }

    public boolean eliminar(Long id) {

        Categoria categoria = buscarPorId(id);

        if (categoria == null) {
            return false;
        }

        categoriaRepository.deleteById(id);

        return true;
    }

    private void validar(Categoria categoria) {

        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        if (categoria.getEstado() == null || categoria.getEstado().trim().isEmpty()) {
            throw new RuntimeException("El estado es obligatorio");
        }
    }
}