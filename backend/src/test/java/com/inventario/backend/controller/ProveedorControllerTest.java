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
class ProveedorControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testListarProveedores_RespuestaCorrecta() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.get("/proveedores")
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void testCrearProveedor_RespuestaCorrecta() throws Exception {

        MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/proveedores")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tech Import\"}")
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }

    @Test
    public void testEliminarProveedor_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/proveedores")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tech Import\",\"telefono\":\"999111222\",\"correo\":\"tech@correo.com\",\"direccion\":\"Lima\"}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultEliminar = mockMvc.perform(
                MockMvcRequestBuilders.delete("/proveedores/" + id)
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultEliminar.getResponse().getStatus());
    }
    @Test
    public void testBuscarProveedor_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/proveedores")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tech Import\",\"telefono\":\"999111222\",\"correo\":\"tech@correo.com\",\"direccion\":\"Lima\"}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultBuscar = mockMvc.perform(
                MockMvcRequestBuilders.get("/proveedores/" + id)
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultBuscar.getResponse().getStatus());
    }
    @Test
    public void testActualizarProveedor_RespuestaCorrecta() throws Exception {

        MvcResult resultCrear = mockMvc.perform(
                MockMvcRequestBuilders.post("/proveedores")
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tech Import\",\"telefono\":\"999111222\",\"correo\":\"tech@correo.com\",\"direccion\":\"Lima\"}")
        ).andReturn();

        String response = resultCrear.getResponse().getContentAsString();

        String id = response.substring(
                response.indexOf("\"id\":") + 5,
                response.indexOf(",", response.indexOf("\"id\":"))
        ).trim();

        MvcResult resultActualizar = mockMvc.perform(
                MockMvcRequestBuilders.put("/proveedores/" + id)
                        .contentType("application/json")
                        .content("{\"nombre\":\"Tech Import Actualizado\",\"telefono\":\"111222333\",\"correo\":\"nuevo@correo.com\",\"direccion\":\"Arequipa\"}")
        ).andReturn();

        assertEquals(HttpStatus.OK.value(), resultActualizar.getResponse().getStatus());
    }
}