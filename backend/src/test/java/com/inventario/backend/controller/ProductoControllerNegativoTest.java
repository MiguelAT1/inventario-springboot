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
class ProductoControllerNegativoTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCrearProducto_PrecioNegativo() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Laptop\",\"stock\":10,\"precio\":-100.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void testCrearProducto_StockNegativo() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Laptop\",\"stock\":-5,\"precio\":1000.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void testCrearProducto_NombreVacio() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"\",\"stock\":10,\"precio\":1000.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void testCrearProducto_RespuestaNoNula_Negativo() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"\",\"stock\":-1,\"precio\":-1,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        String body = result.getResponse().getContentAsString();

        assertEquals(true, body != null);
    }
}