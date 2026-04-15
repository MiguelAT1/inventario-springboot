package com.inventario.backend.controller;

import com.inventario.backend.dto.SolicitudMovimiento;
import com.inventario.backend.model.MovimientoInventario;
import com.inventario.backend.service.MovimientoInventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@CrossOrigin
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoInventarioService;

    public MovimientoInventarioController(MovimientoInventarioService movimientoInventarioService) {
        this.movimientoInventarioService = movimientoInventarioService;
    }

    @PostMapping("/entrada")
    public MovimientoInventario registrarEntrada(@RequestBody SolicitudMovimiento solicitud) {
        return movimientoInventarioService.registrarEntrada(solicitud);
    }

    @PostMapping("/salida")
    public MovimientoInventario registrarSalida(@RequestBody SolicitudMovimiento solicitud) {
        return movimientoInventarioService.registrarSalida(solicitud);
    }

    @GetMapping
    public List<MovimientoInventario> listar() {
        return movimientoInventarioService.listar();
    }

    @GetMapping("/producto/{productoId}")
    public List<MovimientoInventario> listarPorProducto(@PathVariable Long productoId) {
        return movimientoInventarioService.listarPorProducto(productoId);
    }
}