package com.ticarum.hospital_camas.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticarum.hospital_camas.controller.HospitalController;
import com.ticarum.hospital_camas.model.Cama;
import com.ticarum.hospital_camas.model.Dependencia;
import com.ticarum.hospital_camas.model.Hospital;
import com.ticarum.hospital_camas.model.EstadoCama;
import com.ticarum.hospital_camas.repository.CamaRepository;
import com.ticarum.hospital_camas.repository.DependenciaRepository;
import com.ticarum.hospital_camas.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HospitalController.class)
class HospitalControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private CamaRepository camaRepository;

        @MockBean
        private DependenciaRepository dependenciaRepository;

        @MockBean
        private HospitalRepository hospitalRepository;

        private Hospital hospital;
        private Dependencia dependencia;
        private Cama cama;

        @BeforeEach
        void setUp() {
                hospital = new Hospital();
                hospital.setId(1L);
                hospital.setNombre("Test Hospital");

                dependencia = new Dependencia();
                dependencia.setId(1L);
                dependencia.setNombre("Test Dependencia");
                dependencia.setHospital(hospital);

                cama = new Cama();
                cama.setId(100L);
                cama.setEstado(EstadoCama.LIBRE);
                cama.setHospital(hospital);
                cama.setDependencia(dependencia);

                // Simulamos el comportamiento del repositorio en la prueba con Mockito:
                // Cada vez que se llame a hospitalRepository.findById(1L), en lugar de ir a la
                // base de datos,
                // devolverá un Optional que contiene el objeto `hospital` creado en
                // @BeforeEach:
                when(hospitalRepository.findById(1L))
                                .thenReturn(Optional.of(hospital));

                // Cada vez que se llame a dependenciaRepository.findByHospitalId(1L),
                // devolverá una lista que contiene dependencia creado en @BeforeEach:
                when(dependenciaRepository.findByHospitalId(1L))
                                .thenReturn(Arrays.asList(dependencia));

                // Cada vez que se llame a camaRepository.findByHospitalId(1L),
                // devolverá una lista que contiene cama creado en @BeforeEach:
                when(camaRepository.findByHospitalId(1L))
                                .thenReturn(Arrays.asList(cama));

                // Cada vez que se llame a camaRepository.findByHospitalIdAndDependenciaId(1L,
                // 1L),
                // devolverá una lista que contiene el objeto `cama` creado en @BeforeEach:
                when(camaRepository.findByHospitalIdAndDependenciaId(1L, 1L))
                                .thenReturn(Arrays.asList(cama));
        }

        @Test
        void getCamasPorHospital_returnsListOfCamas() throws Exception {
                mockMvc.perform(get("/1/camas")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].id").value(100));
        }

        @Test
        void getDependenciasPorHospital_returnsListOfDependencias() throws Exception {
                mockMvc.perform(get("/1/dependencias")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].nombre").value("Test Dependencia"));
        }

        @Test
        void getCamasPorDependencia_returnsListOfCamas() throws Exception {
                mockMvc.perform(get("/1/1/camas")
                                .contentType(MediaType.APPLICATION_JSON))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$[0].estado").value("LIBRE"));
        }
}
