package com.inventario.backend.controller;

import com.inventario.backend.model.Categoria;
import com.inventario.backend.service.CategoriaService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody Categoria categoria) {

        var createdCategoria = categoriaService.crear(categoria);

        if (createdCategoria == null) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok().body(createdCategoria);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {

        var categorias = categoriaService.listar();

        if (categorias.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscar(@PathVariable Long id) {
        
        var categoria = categoriaService.buscarPorId(id);

        if (categoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(categoria);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria categoria) {
        
        var updatedCategoria = categoriaService.actualizar(id, categoria);

        if (updatedCategoria == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(updatedCategoria);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable Long id) {

        var result = categoriaService.eliminar(id);

        if (!result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }
}