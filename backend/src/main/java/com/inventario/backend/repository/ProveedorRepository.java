package com.inventario.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventario.backend.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}