package com.inventario.backend.controller;

import com.inventario.backend.model.Proveedor;
import com.inventario.backend.service.ProveedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@CrossOrigin
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    // CREAR proveedor
    @PostMapping
    public Proveedor crear(@RequestBody Proveedor proveedor) {
        return proveedorService.crear(proveedor);
    }

    // LISTAR todos
    @GetMapping
    public List<Proveedor> listar() {
        return proveedorService.listar();
    }

    // BUSCAR por ID
    @GetMapping("/{id}")
    public Proveedor buscar(@PathVariable Long id) {
        return proveedorService.buscarPorId(id);
    }

    // ACTUALIZAR
    @PutMapping("/{id}")
    public Proveedor actualizar(@PathVariable Long id,
                                @RequestBody Proveedor proveedor) {
        return proveedorService.actualizar(id, proveedor);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public boolean eliminar(@PathVariable Long id) {
        return proveedorService.eliminar(id);
    }
}