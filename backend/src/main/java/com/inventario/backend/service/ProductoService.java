package com.inventario.backend.service;

import com.inventario.backend.dto.ProductoDTO;
import com.inventario.backend.model.Categoria;
import com.inventario.backend.model.Producto;
import com.inventario.backend.model.Proveedor;
import com.inventario.backend.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProveedorService proveedorService;
    private final CategoriaService categoriaService;

    public ProductoService(ProductoRepository productoRepository,
                           ProveedorService proveedorService,
                           CategoriaService categoriaService) {

        this.productoRepository = productoRepository;
        this.proveedorService = proveedorService;
        this.categoriaService = categoriaService;
    }

    public Producto crear(Producto producto) {

        Categoria categoria = categoriaService.buscarPorId(producto.getCategoria().getId());
        Proveedor proveedor = proveedorService.buscarPorId(producto.getProveedor().getId());

        if (categoria == null) {
            throw new RuntimeException("Categoria no existe");
        }

        if (proveedor == null) {
            throw new RuntimeException("Proveedor no existe");
        }

        producto.setCategoria(categoria);
        producto.setProveedor(proveedor);

        validarProducto(producto);

        return productoRepository.save(producto);
    }
    public List<ProductoDTO> listar() {

        List<Producto> productos = productoRepository.findAll();
        List<ProductoDTO> resultado = new ArrayList<>();

        for (Producto p : productos) {

            String nombreCategoria = (p.getCategoria() != null)
                    ? p.getCategoria().getNombre()
                    : "SIN CATEGORIA";

            String nombreProveedor = (p.getProveedor() != null)
                    ? p.getProveedor().getNombre()
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
        return productoRepository.findById(id).orElse(null);
    }

public Producto actualizar(Long id, Producto nuevo) {

    Producto producto = buscarPorId(id);

    if (producto == null) {
        return null;
    }

    Categoria categoria =
            categoriaService.buscarPorId(nuevo.getCategoria().getId());

    Proveedor proveedor =
            proveedorService.buscarPorId(nuevo.getProveedor().getId());

    if (categoria == null) {
        throw new RuntimeException("Categoria no existe");
    }

    if (proveedor == null) {
        throw new RuntimeException("Proveedor no existe");
    }

    validarProducto(nuevo);

    producto.setNombre(nuevo.getNombre());
    producto.setStock(nuevo.getStock());
    producto.setPrecio(nuevo.getPrecio());
    producto.setEstado(nuevo.getEstado());
    producto.setUbicacion(nuevo.getUbicacion());

    producto.setCategoria(categoria);
    producto.setProveedor(proveedor);

    return productoRepository.save(producto);
}

    public boolean eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            return false;
        }
        productoRepository.deleteById(id);
        return true;
    }

    public List<Producto> listarBajoStock(int limite) {
        return productoRepository.findAll()
                .stream()
                .filter(p -> p.getStock() <= limite)
                .toList();
    }

    public Producto aumentarStock(Long id, int cantidad) {
        Producto producto = buscarPorId(id);

        if (producto == null || cantidad <= 0) {
            return null;
        }

        producto.setStock(producto.getStock() + cantidad);
        return productoRepository.save(producto);
    }

    public Producto disminuirStock(Long id, int cantidad) {
        Producto producto = buscarPorId(id);

        if (producto == null || cantidad <= 0 || producto.getStock() < cantidad) {
            return null;
        }

        producto.setStock(producto.getStock() - cantidad);
        return productoRepository.save(producto);
    }
    public ProductoDTO convertirADTO(Producto p) {
        return new ProductoDTO(
                p.getId(),
                p.getNombre(),
                p.getStock(),
                p.getPrecio(),
                p.getEstado(),
                p.getUbicacion(),
                p.getCategoria().getNombre(),
                p.getProveedor().getNombre()
        );
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

        if (producto.getCategoria() == null ||
                producto.getCategoria().getId() == null ||
                categoriaService.buscarPorId(producto.getCategoria().getId()) == null) {
            throw new RuntimeException("La categoria no existe");
        }

        if (producto.getProveedor() == null ||
                producto.getProveedor().getId() == null ||
                proveedorService.buscarPorId(producto.getProveedor().getId()) == null) {
            throw new RuntimeException("El proveedor no existe");
        }
    }
}