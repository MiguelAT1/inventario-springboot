package com.inventario.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    public void testListarProductos_RespuestaCorrecta() throws Exception {

        URI uri = new URI("/productos");

        MockHttpServletRequestBuilder req =
                MockMvcRequestBuilders.get(uri);

        MvcResult result = mockMvc.perform(req).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void testCrearProducto_RespuestaCorrecta() throws Exception {

        URI uri = new URI("/productos");

        MockHttpServletRequestBuilder req =
                MockMvcRequestBuilders.post(uri)
                        .contentType("application/json")
                        .content("{\"nombre\":\"Laptop\",\"stock\":10,\"precio\":1500.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}");

        MvcResult result = mockMvc.perform(req).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void testEliminarProducto_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Laptop\",\"stock\":10,\"precio\":1500.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultEliminar = mockMvc.perform(
                MockMvcRequestBuilders.delete("/productos/" + id)
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultEliminar.getResponse().getStatus());
    }
 
    @Test
    public void testActualizarProducto_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Laptop\",\"stock\":10,\"precio\":1500.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultActualizar = mockMvc.perform(
                MockMvcRequestBuilders.put("/productos/" + id)
                        .contentType("application/json")
                        .content("{\"nombre\":\"Laptop Actualizada\",\"stock\":20,\"precio\":1500.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultActualizar.getResponse().getStatus());
    }

    @Test
    public void testBuscarProducto_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Laptop\",\"stock\":10,\"precio\":1500.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultBuscar = mockMvc.perform(
                MockMvcRequestBuilders.get("/productos/" + id)
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultBuscar.getResponse().getStatus());
    }

}