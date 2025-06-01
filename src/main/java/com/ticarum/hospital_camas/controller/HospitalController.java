package com.ticarum.hospital_camas.controller;

import com.ticarum.hospital_camas.model.Cama;
import com.ticarum.hospital_camas.model.Dependencia;
import com.ticarum.hospital_camas.repository.CamaRepository;
import com.ticarum.hospital_camas.repository.DependenciaRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{idHospital}")
public class HospitalController {

    private final CamaRepository camaRepository;
    private final DependenciaRepository dependenciaRepository;

    public HospitalController(CamaRepository camaRepository, DependenciaRepository dependenciaRepository) {
        this.camaRepository = camaRepository;
        this.dependenciaRepository = dependenciaRepository;
    }

    @GetMapping("/camas") // GET /{idHospital}/camas
    public List<Cama> getCamasPorHospital(@PathVariable Long idHospital) {
        return camaRepository.findByHospitalId(idHospital);
    }

    @GetMapping("/dependencias") // GET /{idHospital}/dependencias
    public List<Dependencia> getDependenciasPorHospital(@PathVariable Long idHospital) {
        return dependenciaRepository.findByHospitalId(idHospital);
    }

    @GetMapping("/{idDependencia}/camas") // GET /{idHospital}/{idDependencia}/camas
    public List<Cama> getCamasPorDependencia(@PathVariable Long idHospital, @PathVariable Long idDependencia) {
        return camaRepository.findByHospitalIdAndDependenciaId(idHospital, idDependencia);
    }

    @PutMapping("/camas/{idCama}") // PUT /{idHospital}/camas/{idCama}
    public Cama registrarCamaEnHospital(@PathVariable Long idHospital, @PathVariable Long idCama,
            @RequestParam Long idDependencia) {

        Cama cama = camaRepository.findById(idCama).orElseThrow();
        Dependencia dependencia = dependenciaRepository.findById(idDependencia).orElseThrow();

        if (!dependencia.getHospital().getId().equals(idHospital)) {
            throw new IllegalArgumentException("La dependencia no pertenece a ese hospital.");
        }

        cama.setHospital(dependencia.getHospital());
        cama.setDependencia(dependencia);
        return camaRepository.save(cama);
    }

    @DeleteMapping("/camas/{idCama}") // DELETE /{idHospital}/camas/{idCama}
    public void eliminarCamaDeHospital(@PathVariable Long idHospital, @PathVariable Long idCama) {
        camaRepository.deleteById(idCama);
    }

}
