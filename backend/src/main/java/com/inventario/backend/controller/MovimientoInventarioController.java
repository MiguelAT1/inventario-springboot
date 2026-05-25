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

        try {
            MovimientoInventario movimiento = movimientoInventarioService.registrarEntrada(solicitud);
            return ResponseEntity.ok(movimiento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/salida")
    public ResponseEntity<MovimientoInventario> registrarSalida(@RequestBody SolicitudMovimiento solicitud) {

        try {
            MovimientoInventario movimiento = movimientoInventarioService.registrarSalida(solicitud);
            return ResponseEntity.ok(movimiento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MovimientoInventario>> listar() {

        List<MovimientoInventario> movimientos = movimientoInventarioService.listar();

        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(movimientos);
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<MovimientoInventario>> listarPorProducto(@PathVariable("productoId") Long productoId) {

        List<MovimientoInventario> movimientos = movimientoInventarioService.listarPorProducto(productoId);

        if (movimientos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(movimientos);
    }
}