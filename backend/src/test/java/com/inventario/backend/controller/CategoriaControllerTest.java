package com.inventario.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class CategoriaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testListarCategorias_RespuestaCorrecta() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/categorias")
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void testCrearCategoria_RespuestaCorrecta() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/categorias")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Electrónica\",\"descripcion\":\"Productos electrónicos\",\"estado\":\"ACTIVO\"}")
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void testEliminarCategoria_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/categorias")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Hogar\",\"descripcion\":\"Productos del hogar\",\"estado\":\"ACTIVO\"}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultEliminar = mockMvc.perform(
                MockMvcRequestBuilders.delete("/categorias/" + id)
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultEliminar.getResponse().getStatus());
    }

    @Test
    public void testBuscarCategoria_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/categorias")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tecnología\",\"descripcion\":\"Productos tech\",\"estado\":\"ACTIVO\"}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultBuscar = mockMvc.perform(
                MockMvcRequestBuilders.get("/categorias/" + id)
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultBuscar.getResponse().getStatus());
    }

    @Test
    public void testActualizarCategoria_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/categorias")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tecnología\",\"descripcion\":\"Productos tech\",\"estado\":\"ACTIVO\"}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultActualizar = mockMvc.perform(
                MockMvcRequestBuilders.put("/categorias/" + id)
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tecnología Actualizada\",\"descripcion\":\"Nuevo contenido\",\"estado\":\"INACTIVO\"}")
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultActualizar.getResponse().getStatus());
    }
}