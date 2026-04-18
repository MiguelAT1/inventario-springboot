package com.inventario.backend.service;
import com.inventario.backend.dto.ProductoDTO;

import com.inventario.backend.model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
    private Long contadorId = 1L;

    private final ProveedorService proveedorService;
    private final CategoriaService categoriaService;

    public ProductoService(ProveedorService proveedorService,
                           CategoriaService categoriaService) {
        this.proveedorService = proveedorService;
        this.categoriaService = categoriaService;
    }

    public Producto crear(Producto producto) {
        validarProducto(producto);
        producto.setId(contadorId++);
        productos.add(producto);
        return producto;
    }

    public List<ProductoDTO> listar() {

        List<ProductoDTO> resultado = new ArrayList<>();

        for (Producto p : productos) {

            String nombreCategoria = categoriaService.buscarPorId(p.getCategoriaId()) != null
                    ? categoriaService.buscarPorId(p.getCategoriaId()).getNombre()
                    : "SIN CATEGORIA";

            String nombreProveedor = proveedorService.buscarPorId(p.getProveedorId()) != null
                    ? proveedorService.buscarPorId(p.getProveedorId()).getNombre()
                    : "SIN PROVEEDOR";

            ProductoDTO dto = new ProductoDTO(
                    p.getId(),
                    p.getNombre(),
                    p.getStock(),
                    p.getPrecio(),
                    p.getEstado(),
                    p.getUbicacion(),
                    nombreCategoria,
                    nombreProveedor
            );

            resultado.add(dto);
        }

        return resultado;
    }

    public Producto buscarPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Producto actualizar(Long id, Producto nuevo) {
        Producto producto = buscarPorId(id);

        if (producto == null) {
            return null;
        }

        validarProducto(nuevo);

        producto.setNombre(nuevo.getNombre());
        producto.setStock(nuevo.getStock());
        producto.setPrecio(nuevo.getPrecio());
        producto.setEstado(nuevo.getEstado());
        producto.setUbicacion(nuevo.getUbicacion());
        producto.setCategoriaId(nuevo.getCategoriaId());
        producto.setProveedorId(nuevo.getProveedorId());

        return producto;
    }

    public boolean eliminar(Long id) {
        return productos.removeIf(p -> id.equals(p.getId()));
    }

    public List<Producto> listarBajoStock(int limite) {
        List<Producto> resultado = new ArrayList<>();

        for (Producto producto : productos) {
            if (producto.getStock() <= limite) {
                resultado.add(producto);
            }
        }

        return resultado;
    }

    public Producto aumentarStock(Long id, int cantidad) {
        Producto producto = buscarPorId(id);

        if (producto == null || cantidad <= 0) {
            return null;
        }

        producto.setStock(producto.getStock() + cantidad);
        return producto;
    }

    public Producto disminuirStock(Long id, int cantidad) {
        Producto producto = buscarPorId(id);

        if (producto == null || cantidad <= 0 || producto.getStock() < cantidad) {
            return null;
        }

        producto.setStock(producto.getStock() - cantidad);
        return producto;
    }

    private void validarProducto(Producto producto) {

        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new RuntimeException("El nombre es obligatorio");
        }

        if (producto.getPrecio() < 0) {
            throw new RuntimeException("El precio no puede ser negativo");
        }

        if (producto.getStock() < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }

        if (producto.getCategoriaId() == null ||
                categoriaService.buscarPorId(producto.getCategoriaId()) == null) {
            throw new RuntimeException("La categoria no existe");
        }

        if (producto.getProveedorId() == null ||
                proveedorService.buscarPorId(producto.getProveedorId()) == null) {
            throw new RuntimeException("El proveedor no existe");
        }
    }
}