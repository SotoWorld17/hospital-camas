package com.ticarum.hospital_camas;

import com.ticarum.hospital_camas.model.Dependencia;
import com.ticarum.hospital_camas.model.Hospital;
import com.ticarum.hospital_camas.repository.DependenciaRepository;
import com.ticarum.hospital_camas.repository.HospitalRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final HospitalRepository hospitalRepository;
    private final DependenciaRepository dependenciaRepository;

    public DataLoader(HospitalRepository hospitalRepository, DependenciaRepository dependenciaRepository) {
        this.hospitalRepository = hospitalRepository;
        this.dependenciaRepository = dependenciaRepository;
    }

    @Override
    public void run(String... args) {

        // Crear hospitales
        Hospital h1 = new Hospital();
        h1.setNombre("Hospital General de Murcia");

        Hospital h2 = new Hospital();
        h2.setNombre("Hospital Clínico San Juan");

        hospitalRepository.saveAll(Arrays.asList(h1, h2));

        // Crear dependencias para cada hospital
        Dependencia d1 = new Dependencia();
        d1.setNombre("Urgencias");
        d1.setHospital(h1);

        Dependencia d2 = new Dependencia();
        d2.setNombre("UCI");
        d2.setHospital(h1);

        Dependencia d3 = new Dependencia();
        d3.setNombre("Pediatría");
        d3.setHospital(h2);

        dependenciaRepository.saveAll(Arrays.asList(d1, d2, d3));

        System.out.println("Datos cargador con éxito");
    }
}
