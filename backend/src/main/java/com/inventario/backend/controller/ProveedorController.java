package com.inventario.backend.controller;

import com.inventario.backend.model.Proveedor;
import com.inventario.backend.service.ProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@CrossOrigin
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Proveedor>> listar() {

        List<Proveedor> proveedores = service.listar();

        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscar(@PathVariable("id") Long id) {

        Proveedor proveedor = service.buscarPorId(id);

        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(proveedor);
    }

    @PostMapping
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {

        Proveedor createdProveedor = service.crear(proveedor);

        if (createdProveedor == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(createdProveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(
            @PathVariable("id") Long id,
            @RequestBody Proveedor proveedor) {

        Proveedor updatedProveedor = service.actualizar(id, proveedor);

        if (updatedProveedor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedProveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Long id) {

        boolean eliminado = service.eliminar(id);

        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}