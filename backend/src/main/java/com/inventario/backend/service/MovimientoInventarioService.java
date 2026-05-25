package com.inventario.backend.service;

import com.inventario.backend.dto.SolicitudMovimiento;
import com.inventario.backend.model.MovimientoInventario;
import com.inventario.backend.model.Producto;
import com.inventario.backend.repository.MovimientoInventarioRepository;
import com.inventario.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MovimientoInventarioService {

    private final MovimientoInventarioRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public MovimientoInventarioService(MovimientoInventarioRepository movimientoRepository,
                                       ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    public MovimientoInventario registrarEntrada(SolicitudMovimiento solicitud) {

        Producto producto = productoRepository.findById(solicitud.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no existe"));

        producto.setStock(producto.getStock() + solicitud.getCantidad());
        productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setTipo("ENTRADA");
        movimiento.setCantidad(solicitud.getCantidad());
        movimiento.setFecha(LocalDate.now());
        movimiento.setObservacion(solicitud.getObservacion());
        movimiento.setProducto(producto);

        return movimientoRepository.save(movimiento);
    }

    public MovimientoInventario registrarSalida(SolicitudMovimiento solicitud) {

        Producto producto = productoRepository.findById(solicitud.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no existe"));

        if (producto.getStock() < solicitud.getCantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }

        producto.setStock(producto.getStock() - solicitud.getCantidad());
        productoRepository.save(producto);

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setTipo("SALIDA");
        movimiento.setCantidad(solicitud.getCantidad());
        movimiento.setFecha(LocalDate.now());
        movimiento.setObservacion(solicitud.getObservacion());
        movimiento.setProducto(producto);

        return movimientoRepository.save(movimiento);
    }

    public List<MovimientoInventario> listar() {
        return movimientoRepository.findAll();
    }

    public List<MovimientoInventario> listarPorProducto(Long productoId) {
        return movimientoRepository.findAll()
                .stream()
                .filter(m -> m.getProducto().getId().equals(productoId))
                .toList();
    }
}