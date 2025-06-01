package com.ticarum.hospital_camas.repository;

import com.ticarum.hospital_camas.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {

}