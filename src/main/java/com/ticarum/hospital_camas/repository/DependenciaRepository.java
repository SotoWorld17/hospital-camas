package com.ticarum.hospital_camas.repository;

import com.ticarum.hospital_camas.model.Dependencia;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DependenciaRepository extends JpaRepository<Dependencia, Long> {
    // Buscar las dependencias de un hospital concreto
    List<Dependencia> findByHospitalId(Long hospitalId);
}
