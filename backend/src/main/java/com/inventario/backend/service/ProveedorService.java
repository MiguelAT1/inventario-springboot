package com.inventario.backend.service;

import com.inventario.backend.model.Proveedor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProveedorService {

    private final List<Proveedor> proveedores = new ArrayList<>();

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

    public Proveedor actualizar(Long id, Proveedor proveedor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actualizar'");
    }

    public boolean eliminar(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
    }
}