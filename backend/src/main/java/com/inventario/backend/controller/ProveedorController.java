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
        var proveedores = service.listar();

        if (proveedores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(proveedores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> buscar(@PathVariable Long id) {

        var proveedor = service.buscarPorId(id);

        if (proveedor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(proveedor);
    }

    @PostMapping
    public ResponseEntity<Proveedor> crear(@RequestBody Proveedor proveedor) {
        var createdProveedor = service.crear(proveedor);

        if (createdProveedor == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(createdProveedor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        var updatedProveedor = service.actualizar(id, proveedor);

        if (updatedProveedor == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(updatedProveedor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> eliminar(@PathVariable Long id) {
        var result = service.eliminar(id);

        if (!result) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok().body(result);
    }
}