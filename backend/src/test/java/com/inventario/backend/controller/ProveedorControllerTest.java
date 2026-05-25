package com.inventario.backend.controller;

import com.inventario.backend.model.Proveedor;
import com.inventario.backend.service.ProveedorService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProveedorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProveedorService service;

    @Test
    void listarProveedores() throws Exception {

        List<Proveedor> proveedores = new ArrayList<>();

        when(service.listar()).thenReturn(proveedores);

        mockMvc.perform(
                get("/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    }

    @Test
    void listarProveedoresConDatos() throws Exception {

        List<Proveedor> proveedores = new ArrayList<>();

        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);

        proveedores.add(proveedor);

        when(service.listar()).thenReturn(proveedores);

        mockMvc.perform(
                get("/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void buscarProveedorExistente() throws Exception {

        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);

        when(service.buscarPorId(1L))
                .thenReturn(proveedor);

        mockMvc.perform(
                get("/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void buscarProveedorInexistente() throws Exception {

        when(service.buscarPorId(99L))
                .thenReturn(null);

        mockMvc.perform(
                get("/proveedores/99")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void crearProveedor() throws Exception {

        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);

        when(service.crear(
                org.mockito.ArgumentMatchers.any(Proveedor.class)
        )).thenReturn(proveedor);

        mockMvc.perform(
                post("/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Tech Supplier"
                                }
                                """)
        )
        .andExpect(status().isOk());
    }

    @Test
    void crearProveedorInvalido() throws Exception {

        when(service.crear(
                org.mockito.ArgumentMatchers.any(Proveedor.class)
        )).thenReturn(null);

        mockMvc.perform(
                post("/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":""
                                }
                                """)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarProveedorExistente() throws Exception {

        Proveedor proveedor = new Proveedor();
        proveedor.setId(1L);

        when(service.actualizar(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any(Proveedor.class)
        )).thenReturn(proveedor);

        mockMvc.perform(
                put("/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Proveedor Actualizado"
                                }
                                """)
        )
        .andExpect(status().isOk());
    }

    @Test
    void actualizarProveedorInexistente() throws Exception {

        when(service.actualizar(
                org.mockito.ArgumentMatchers.eq(99L),
                org.mockito.ArgumentMatchers.any(Proveedor.class)
        )).thenReturn(null);

        mockMvc.perform(
                put("/proveedores/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Fantasma"
                                }
                                """)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void eliminarProveedorExistente() throws Exception {

        when(service.eliminar(1L))
                .thenReturn(true);

        mockMvc.perform(
                delete("/proveedores/1")
        )
        .andExpect(status().isNoContent());
    }

    @Test
    void eliminarProveedorInexistente() throws Exception {

        when(service.eliminar(99L))
                .thenReturn(false);

        mockMvc.perform(
                delete("/proveedores/99")
        )
        .andExpect(status().isNotFound());
    }
}