package com.inventario.backend.dto;

public class ProductoDTO {

    private Long id;
    private String nombre;
    private int stock;
    private double precio;
    private String estado;
    private String ubicacion;

    private String categoria;
    private String proveedor;

    public ProductoDTO() {
    }

    public ProductoDTO(Long id, String nombre, int stock, double precio,
                       String estado, String ubicacion,
                       String categoria, String proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public double getPrecio() {
        return precio;
    }

    public String getEstado() {
        return estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getProveedor() {
        return proveedor;
    }
}