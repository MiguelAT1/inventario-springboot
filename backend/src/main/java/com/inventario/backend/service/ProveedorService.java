package com.inventario.backend.service;

import com.inventario.backend.model.Proveedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService {

    private final List<Proveedor> proveedores = new ArrayList<>();
    private Long contadorId = 3L;

    public ProveedorService() {
        proveedores.add(new Proveedor(1L, "Tech Import", "999111222", "tech@correo.com", "Lima"));
        proveedores.add(new Proveedor(2L, "CompuMarket", "988777666", "ventas@compu.com", "Arequipa"));
    }

    public List<Proveedor> listar() {
        return proveedores;
    }

    public Proveedor buscarPorId(Long id) {
        return proveedores.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Proveedor crear(Proveedor proveedor) {
        proveedor.setId(contadorId++);
        proveedores.add(proveedor);
        return proveedor;
    }

    public Proveedor actualizar(Long id, Proveedor proveedorActualizado) {
        for (Proveedor p : proveedores) {
            if (p.getId().equals(id)) {
                p.setNombre(proveedorActualizado.getNombre());
                p.setTelefono(proveedorActualizado.getTelefono());
                p.setCorreo(proveedorActualizado.getCorreo());
                p.setDireccion(proveedorActualizado.getDireccion());
                return p;
            }
        }
        return null;
    }

    public boolean eliminar(Long id) {
        return proveedores.removeIf(p -> p.getId().equals(id));
    }
}