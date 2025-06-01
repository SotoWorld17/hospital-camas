package com.ticarum.hospital_camas.repository;

import com.ticarum.hospital_camas.model.Cama;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CamaRepository extends JpaRepository<Cama, Long> {

    List<Cama> findByHospitalId(Long hospitalId);

    List<Cama> findByHospitalIdAndDependenciaId(Long hospitalId, Long dependenciaId);
}