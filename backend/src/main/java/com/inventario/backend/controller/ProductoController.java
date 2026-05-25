package com.inventario.backend.controller;

import com.inventario.backend.dto.ProductoDTO;
import com.inventario.backend.model.Producto;
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
    public ResponseEntity<ProductoDTO> crear(@RequestBody Producto producto) {
        Producto creado = productoService.crear(producto);
        return ResponseEntity.ok(productoService.convertirADTO(creado));
    }

    @GetMapping
    public ResponseEntity<List<ProductoDTO>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscar(@PathVariable("id") Long id) {

        Producto producto = productoService.buscarPorId(id);

        if (producto == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(producto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable("id") Long id,
            @RequestBody Producto producto
    ) {

        Producto actualizado = productoService.actualizar(id, producto);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {

        boolean eliminado = productoService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/aumentar")
    public ResponseEntity<Producto> aumentarStock(
            @PathVariable("id") Long id,
            @RequestParam("cantidad") int cantidad
    ) {

        Producto producto = productoService.aumentarStock(id, cantidad);

        if (producto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(producto);
    }

    @PatchMapping("/{id}/disminuir")
    public ResponseEntity<Producto> disminuirStock(
            @PathVariable("id") Long id,
            @RequestParam("cantidad") int cantidad
    ) {

        Producto producto = productoService.disminuirStock(id, cantidad);

        if (producto == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(producto);
    }

    @GetMapping("/bajo-stock")
    public ResponseEntity<List<Producto>> bajoStock(@RequestParam("limite") int limite) {
        return ResponseEntity.ok(productoService.listarBajoStock(limite));
    }
}