package com.inventario.backend.service;

import com.inventario.backend.model.Proveedor;
import com.inventario.backend.repository.ProveedorRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public Proveedor crear(Proveedor proveedor) {

        validar(proveedor);

        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> listar() {

        return proveedorRepository.findAll();
    }

    public Proveedor buscarPorId(Long id) {

        return proveedorRepository.findById(id).orElse(null);
    }

    public Proveedor actualizar(Long id, Proveedor nuevo) {

        Proveedor proveedor = buscarPorId(id);

        if (proveedor == null) {
            return null;
        }

        validar(nuevo);

        proveedor.setNombre(nuevo.getNombre());
        proveedor.setTelefono(nuevo.getTelefono());
        proveedor.setCorreo(nuevo.getCorreo());
        proveedor.setDireccion(nuevo.getDireccion());

        return proveedorRepository.save(proveedor);
    }

    public boolean eliminar(Long id) {

        Proveedor proveedor = buscarPorId(id);

        if (proveedor == null) {
            return false;
        }

        proveedorRepository.deleteById(id);

        return true;
    }

    private void validar(Proveedor proveedor) {

        if (proveedor.getNombre() == null || proveedor.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        if (proveedor.getTelefono() == null || proveedor.getTelefono().trim().isEmpty()) {
            throw new RuntimeException("El teléfono es obligatorio");
        }

        if (proveedor.getCorreo() == null || proveedor.getCorreo().trim().isEmpty()) {
            throw new RuntimeException("El correo es obligatorio");
        }
    }
}