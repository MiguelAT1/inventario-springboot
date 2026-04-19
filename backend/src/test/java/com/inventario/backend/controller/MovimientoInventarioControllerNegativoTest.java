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
class MovimientoInventarioControllerNegativoTest {

    @Autowired
    MockMvc mockMvc;

    private String crearProducto() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/productos")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Mouse\",\"stock\":5,\"precio\":100.0,\"estado\":\"ACTIVO\",\"ubicacion\":\"ALMACEN\",\"categoriaId\":1,\"proveedorId\":1}")
        ).andReturn();

        String response = result.getResponse().getContentAsString();

        return response.split("\"id\":")[1].split(",")[0].trim();
    }

    @Test
    public void testRegistrarSalida_MayorAlStock() throws Exception {

        String id = crearProducto();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/movimientos/salida")
                        .contentType("application/json")
                        .content("{\"productoId\":" + id + ",\"cantidad\":999}")
        ).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void testRegistrarEntrada_CantidadNegativa() throws Exception {

        String id = crearProducto();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/movimientos/entrada")
                        .contentType("application/json")
                        .content("{\"productoId\":" + id + ",\"cantidad\":-5}")
        ).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void testRegistrarSalida_CantidadNegativa() throws Exception {

        String id = crearProducto();

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/movimientos/salida")
                        .contentType("application/json")
                        .content("{\"productoId\":" + id + ",\"cantidad\":-10}")
        ).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }

    @Test
    public void testRegistrarMovimiento_ProductoNoExiste() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/movimientos/entrada")
                        .contentType("application/json")
                        .content("{\"productoId\":9999,\"cantidad\":5}")
        ).andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
    }
}