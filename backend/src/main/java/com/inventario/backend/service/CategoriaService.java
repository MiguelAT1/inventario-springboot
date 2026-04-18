package com.inventario.backend.service;

import com.inventario.backend.model.Categoria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();
    private Long contadorId = 1L;

    public CategoriaService() {
        categorias.add(new Categoria(contadorId++, "Tecnologia", "Productos tecnológicos", "ACTIVO"));
        categorias.add(new Categoria(contadorId++, "Hogar", "Productos para el hogar", "ACTIVO"));
    }

    public Categoria crear(Categoria categoria) {
        validar(categoria);
        categoria.setId(contadorId++);
        categorias.add(categoria);
        return categoria;
    }

    public List<Categoria> listar() {
        return categorias;
    }

    public Categoria buscarPorId(Long id) {
        return categorias.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
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

        return categoria;
    }

    public boolean eliminar(Long id) {
        return categorias.removeIf(c -> c.getId().equals(id));
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