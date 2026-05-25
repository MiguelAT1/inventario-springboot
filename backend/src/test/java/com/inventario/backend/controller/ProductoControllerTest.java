package com.inventario.backend.controller;

import com.inventario.backend.model.Producto;
import com.inventario.backend.service.ProductoService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductoService productoService;

    @Test
    void crearProducto() throws Exception {

        Producto producto = new Producto();
        producto.setId(1L);

        when(productoService.crear(org.mockito.ArgumentMatchers.any(Producto.class)))
                .thenReturn(producto);

        when(productoService.convertirADTO(org.mockito.ArgumentMatchers.any(Producto.class)))
                .thenReturn(null);

        mockMvc.perform(
                post("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Laptop Gamer",
                                    "precio":1500,
                                    "stock":10
                                }
                                """)
        )
        .andExpect(status().isOk());
    }

    @Test
    void listarProductos() throws Exception {

        when(productoService.listar()).thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/productos")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void buscarProductoExistente() throws Exception {

        Producto producto = new Producto();
        producto.setId(1L);

        when(productoService.buscarPorId(1L)).thenReturn(producto);

        mockMvc.perform(
                get("/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void buscarProductoInexistente() throws Exception {

        when(productoService.buscarPorId(99L)).thenReturn(null);

        mockMvc.perform(
                get("/productos/99")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void actualizarProductoExistente() throws Exception {

        Producto producto = new Producto();
        producto.setId(1L);

        when(productoService.actualizar(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any(Producto.class)
        )).thenReturn(producto);

        mockMvc.perform(
                put("/productos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Monitor Gamer",
                                    "precio":900,
                                    "stock":5
                                }
                                """)
        )
        .andExpect(status().isOk());
    }

    @Test
    void actualizarProductoInexistente() throws Exception {

        when(productoService.actualizar(
                org.mockito.ArgumentMatchers.eq(99L),
                org.mockito.ArgumentMatchers.any(Producto.class)
        )).thenReturn(null);

        mockMvc.perform(
                put("/productos/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Producto Fantasma",
                                    "precio":100,
                                    "stock":1
                                }
                                """)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void eliminarProductoExistente() throws Exception {

        when(productoService.eliminar(1L)).thenReturn(true);

        mockMvc.perform(
                delete("/productos/1")
        )
        .andExpect(status().isNoContent());
    }

    @Test
    void eliminarProductoInexistente() throws Exception {

        when(productoService.eliminar(99L)).thenReturn(false);

        mockMvc.perform(
                delete("/productos/99")
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void aumentarStock() throws Exception {

        Producto producto = new Producto();
        producto.setId(1L);

        when(productoService.aumentarStock(1L, 5)).thenReturn(producto);

        mockMvc.perform(
                patch("/productos/1/aumentar?cantidad=5")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void aumentarStockInvalido() throws Exception {

        when(productoService.aumentarStock(1L, -1)).thenReturn(null);

        mockMvc.perform(
                patch("/productos/1/aumentar?cantidad=-1")
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void disminuirStock() throws Exception {

        Producto producto = new Producto();
        producto.setId(1L);

        when(productoService.disminuirStock(1L, 2)).thenReturn(producto);

        mockMvc.perform(
                patch("/productos/1/disminuir?cantidad=2")
        )
        .andExpect(status().isOk());
    }

    @Test
    void disminuirStockInvalido() throws Exception {

        when(productoService.disminuirStock(1L, 999)).thenReturn(null);

        mockMvc.perform(
                patch("/productos/1/disminuir?cantidad=999")
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void listarProductosBajoStock() throws Exception {

        when(productoService.listarBajoStock(5))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(
                get("/productos/bajo-stock?limite=5")
        )
        .andExpect(status().isOk());
    }
}