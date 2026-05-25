package com.inventario.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.inventario.backend.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}