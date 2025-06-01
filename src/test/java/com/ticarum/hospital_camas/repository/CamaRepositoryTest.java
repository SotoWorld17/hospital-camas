package com.ticarum.hospital_camas.repository;

import com.ticarum.hospital_camas.model.Cama;
import com.ticarum.hospital_camas.model.Dependencia;
import com.ticarum.hospital_camas.model.Hospital;
import com.ticarum.hospital_camas.model.EstadoCama;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CamaRepositoryTest {

    @Autowired
    private CamaRepository camaRepository;

    @Autowired
    private DependenciaRepository dependenciaRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    private Hospital hospital;
    private Dependencia dependencia;

    @BeforeEach
    void setUp() {

        hospital = new Hospital();
        hospital.setNombre("Test Hospital");
        hospital = hospitalRepository.save(hospital);

        dependencia = new Dependencia();
        dependencia.setNombre("Test Dependencia");
        dependencia.setHospital(hospital);
        dependencia = dependenciaRepository.save(dependencia);

        // Crea dos camas asociadas a ese hospital y dependencia
        Cama c1 = new Cama();
        c1.setId(1L);
        c1.setEstado(EstadoCama.LIBRE);
        c1.setHospital(hospital);
        c1.setDependencia(dependencia);

        Cama c2 = new Cama();
        c2.setId(2L);
        c2.setEstado(EstadoCama.OCUPADA);
        c2.setHospital(hospital);
        c2.setDependencia(dependencia);

        camaRepository.save(c1);
        camaRepository.save(c2);
    }

    @Test
    void findByHospitalId_shouldReturnAllCamasForThatHospital() {
        List<Cama> camas = camaRepository.findByHospitalId(hospital.getId());
        assertThat(camas)
                .hasSize(2)
                .extracting(Cama::getId)
                .containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void findByHospitalIdAndDependenciaId_shouldReturnOnlyMatchingCamas() {
        List<Cama> camas = camaRepository.findByHospitalIdAndDependenciaId(
                hospital.getId(), dependencia.getId());
        assertThat(camas).hasSize(2);
    }
}
