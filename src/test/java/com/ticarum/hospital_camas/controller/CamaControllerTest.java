package com.ticarum.hospital_camas.controller;

import com.ticarum.hospital_camas.model.Cama;
import com.ticarum.hospital_camas.model.EstadoCama;
import com.ticarum.hospital_camas.repository.CamaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CamaController.class)
class CamaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CamaRepository camaRepository;

    private Cama cama;

    @BeforeEach
    void setUp() {
        cama = new Cama();
        cama.setId(200L);
        cama.setEstado(EstadoCama.LIBRE);
    }

    @Test
    void crearYObtenerCama() throws Exception {
        // POST /camas/200 → devuelve la misma Cama con estado LIBRE
        when(camaRepository.save(any(Cama.class))).thenReturn(cama);
        // GET /camas/200 → devuelve Optional.of(cama)
        when(camaRepository.findById(200L)).thenReturn(Optional.of(cama));

        mockMvc.perform(post("/camas/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("LIBRE"));

        mockMvc.perform(get("/camas/200"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(200));
    }

    @Test
    void actualizarEstadoCama() throws Exception {
        when(camaRepository.findById(200L)).thenReturn(Optional.of(cama));
        Cama updated = new Cama();
        updated.setId(200L);
        updated.setEstado(EstadoCama.OCUPADA);
        when(camaRepository.save(any(Cama.class))).thenReturn(updated);

        mockMvc.perform(put("/camas/200")
                .param("nuevoEstado", "OCUPADA")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("OCUPADA"));
    }

    @Test
    void eliminarCamaExistente() throws Exception {
        doNothing().when(camaRepository).deleteById(300L);
        mockMvc.perform(delete("/camas/300"))
                .andExpect(status().isOk());
    }
}
