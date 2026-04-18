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

    public ProductoDTO(Long id, String nombre, int stock, double precio, String estado,
                       String ubicacion, String categoria, String proveedor) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.precio = precio;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.categoria = categoria;
        this.proveedor = proveedor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getProveedor() { return proveedor; }
    public void setProveedor(String proveedor) { this.proveedor = proveedor; }
}