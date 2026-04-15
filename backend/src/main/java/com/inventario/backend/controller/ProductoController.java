package com.inventario.backend.controller;

import com.inventario.backend.model.Producto;
import com.inventario.backend.model.Proveedor;
import com.inventario.backend.service.ProductoService;
import com.inventario.backend.service.ProveedorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@CrossOrigin
public class ProductoController {

    private final ProductoService productoService;
    private final ProveedorService proveedorService;

    public ProductoController(ProductoService productoService, ProveedorService proveedorService) {
        this.productoService = productoService;
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crear(producto);
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable Long id) {
        return productoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    public boolean eliminar(@PathVariable Long id) {
        return productoService.eliminar(id);
    }

    @GetMapping("/bajo-stock/{limite}")
    public List<Producto> bajoStock(@PathVariable int limite) {
        return productoService.listarBajoStock(limite);
    }

    @GetMapping("/proveedores")
    public List<Proveedor> listarProveedores() {
        return proveedorService.listar();
    }
}