package com.inventario.backend.controller;

import com.inventario.backend.dto.SolicitudMovimiento;
import com.inventario.backend.model.MovimientoInventario;
import com.inventario.backend.service.MovimientoInventarioService;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<MovimientoInventario> registrarEntrada(@RequestBody SolicitudMovimiento solicitud) {
        var movimiento = movimientoInventarioService.registrarEntrada(solicitud);

        if (movimiento == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(movimiento);
    }

    @PostMapping("/salida")
    public ResponseEntity<MovimientoInventario> registrarSalida(@RequestBody SolicitudMovimiento solicitud) {
        var movimiento = movimientoInventarioService.registrarSalida(solicitud);

        if (movimiento == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok().body(movimiento);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoInventario>> listar() {
        var movimientos = movimientoInventarioService.listar();

        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(movimientos);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<MovimientoInventario>> listarPorProducto(@PathVariable Long productoId) {
        var movimientos = movimientoInventarioService.listarPorProducto(productoId);

        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok().body(movimientos);
    }
}