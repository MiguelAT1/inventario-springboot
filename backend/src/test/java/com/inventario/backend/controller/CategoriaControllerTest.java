package com.inventario.backend.controller;

import com.inventario.backend.model.Categoria;
import com.inventario.backend.service.CategoriaService;

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
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoriaService categoriaService;

    @Test
    void crearCategoria() throws Exception {

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        when(categoriaService.crear(
                org.mockito.ArgumentMatchers.any(Categoria.class)
        )).thenReturn(categoria);

        mockMvc.perform(
                post("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Tecnologia"
                                }
                                """)
        )
        .andExpect(status().isOk());
    }

    @Test
    void crearCategoriaInvalida() throws Exception {

        when(categoriaService.crear(
                org.mockito.ArgumentMatchers.any(Categoria.class)
        )).thenReturn(null);

        mockMvc.perform(
                post("/categorias")
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
    void listarCategorias() throws Exception {

        List<Categoria> categorias = new ArrayList<>();

        when(categoriaService.listar()).thenReturn(categorias);

        mockMvc.perform(
                get("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    }

    @Test
    void listarCategoriasConDatos() throws Exception {

        List<Categoria> categorias = new ArrayList<>();

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        categorias.add(categoria);

        when(categoriaService.listar()).thenReturn(categorias);

        mockMvc.perform(
                get("/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void buscarCategoriaExistente() throws Exception {

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        when(categoriaService.buscarPorId(1L))
                .thenReturn(categoria);

        mockMvc.perform(
                get("/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    void buscarCategoriaInexistente() throws Exception {

        when(categoriaService.buscarPorId(99L))
                .thenReturn(null);

        mockMvc.perform(
                get("/categorias/99")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    }

    @Test
    void actualizarCategoriaExistente() throws Exception {

        Categoria categoria = new Categoria();
        categoria.setId(1L);

        when(categoriaService.actualizar(
                org.mockito.ArgumentMatchers.eq(1L),
                org.mockito.ArgumentMatchers.any(Categoria.class)
        )).thenReturn(categoria);

        mockMvc.perform(
                put("/categorias/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "nombre":"Hogar"
                                }
                                """)
        )
        .andExpect(status().isOk());
    }

    @Test
    void actualizarCategoriaInexistente() throws Exception {

        when(categoriaService.actualizar(
                org.mockito.ArgumentMatchers.eq(99L),
                org.mockito.ArgumentMatchers.any(Categoria.class)
        )).thenReturn(null);

        mockMvc.perform(
                put("/categorias/99")
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
    void eliminarCategoriaExistente() throws Exception {

        when(categoriaService.eliminar(1L))
                .thenReturn(true);

        mockMvc.perform(
                delete("/categorias/1")
        )
        .andExpect(status().isNoContent());
    }

    @Test
    void eliminarCategoriaInexistente() throws Exception {

        when(categoriaService.eliminar(99L))
                .thenReturn(false);

        mockMvc.perform(
                delete("/categorias/99")
        )
        .andExpect(status().isNotFound());
    }
}