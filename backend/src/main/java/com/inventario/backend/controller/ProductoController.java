package com.inventario.backend.controller;

import com.inventario.backend.model.Producto;
import com.inventario.backend.dto.ProductoDTO;
import com.inventario.backend.service.ProductoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<Producto> crear(@RequestBody Producto producto) {
        try {
            var createdProducto = productoService.crear(producto);

            if (createdProducto == null) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok().body(createdProducto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        var productos = productoService.listar();

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable("id") Long id) {
        var producto = productoService.buscarPorId(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable("id") Long id, @RequestBody Producto producto) {
        var updatedProducto = productoService.actualizar(id, producto);

        if (updatedProducto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(updatedProducto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable("id") Long id) {
        var result = productoService.eliminar(id);

        if (!result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/bajo-stock/{limite}")
    public ResponseEntity<List<Producto>> bajoStock(@PathVariable("limite") int limite) {
        var productos = productoService.listarBajoStock(limite);

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(productos);
    }
}