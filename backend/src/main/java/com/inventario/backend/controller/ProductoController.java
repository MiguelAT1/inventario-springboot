package com.inventario.backend.controller;

import com.inventario.backend.model.Producto;
import com.inventario.backend.dto.ProductoDTO;
import com.inventario.backend.service.ProductoService;
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
    public Producto crear(@RequestBody Producto producto) {
        return productoService.crear(producto);
    }

    @GetMapping
    public List<ProductoDTO> listar() {
        return productoService.listar();
    }

    @GetMapping("/{id}")
    public Producto buscar(@PathVariable("id") Long id) {
        return productoService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Producto actualizar(@PathVariable("id") Long id, @RequestBody Producto producto) {
        return productoService.actualizar(id, producto);
    }

    @DeleteMapping("/{id}")
    public boolean eliminar(@PathVariable("id") Long id) {
        return productoService.eliminar(id);
    }

    @GetMapping("/bajo-stock/{limite}")
    public List<Producto> bajoStock(@PathVariable int limite) {
        return productoService.listarBajoStock(limite);
    }
}