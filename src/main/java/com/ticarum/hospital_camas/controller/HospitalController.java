package com.ticarum.hospital_camas.controller;

import com.ticarum.hospital_camas.model.Cama;
import com.ticarum.hospital_camas.model.Dependencia;
import com.ticarum.hospital_camas.repository.CamaRepository;
import com.ticarum.hospital_camas.repository.DependenciaRepository;

import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.http.MediaType;
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

    @Operation(summary = "Lista todas las camas de un hospital", parameters = {
            @Parameter(name = "idHospital", description = "ID del hospital", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Listado de camas", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Cama.class))),
            @ApiResponse(responseCode = "404", description = "Hospital no encontrado")
    })
    @GetMapping("/camas") // GET /{idHospital}/camas
    public List<Cama> getCamasPorHospital(@PathVariable Long idHospital) {
        return camaRepository.findByHospitalId(idHospital);
    }

    @Operation(summary = "Lista todas las dependencias de un hospital", description = "Devuelve todas las dependencias (unidades) que existen en el hospital indicado.", parameters = {
            @Parameter(name = "idHospital", description = "ID del hospital", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Listado de dependencias del hospital", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Dependencia.class))),
            @ApiResponse(responseCode = "404", description = "Hospital no encontrado")
    })
    @GetMapping("/dependencias") // GET /{idHospital}/dependencias
    public List<Dependencia> getDependenciasPorHospital(@PathVariable Long idHospital) {
        return dependenciaRepository.findByHospitalId(idHospital);
    }

    @Operation(summary = "Lista todas las camas de una dependencia en un hospital", description = "Devuelve todas las camas que hay en la dependencia `idDependencia` dentro del hospital `idHospital`.", parameters = {
            @Parameter(name = "idHospital", description = "ID del hospital", required = true),
            @Parameter(name = "idDependencia", description = "ID de la dependencia", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Listado de camas en la dependencia", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Cama.class))),
            @ApiResponse(responseCode = "404", description = "Hospital o dependencia no encontrado")
    })
    @GetMapping("/{idDependencia}/camas") // GET /{idHospital}/{idDependencia}/camas
    public List<Cama> getCamasPorDependencia(@PathVariable Long idHospital, @PathVariable Long idDependencia) {
        return camaRepository.findByHospitalIdAndDependenciaId(idHospital, idDependencia);
    }

    @Operation(summary = "Registra una cama en un hospital y dependencia", description = "Asocia la cama `idCama` al hospital `idHospital` y la dependencia `idDependencia`. La cama debe existir previamente.", parameters = {
            @Parameter(name = "idHospital", description = "ID del hospital", required = true),
            @Parameter(name = "idCama", description = "ID de la cama a registrar", required = true),
            @Parameter(name = "idDependencia", description = "ID de la dependencia donde se ubicará la cama", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Cama registrada correctamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Cama.class))),
            @ApiResponse(responseCode = "400", description = "La dependencia no pertenece al hospital o petición inválida"),
            @ApiResponse(responseCode = "404", description = "Cama o dependencia no encontrada")
    })
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

    @Operation(summary = "Elimina una cama de un hospital", description = "Borra la cama con `idCama` del hospital `idHospital`. Solo se necesita el ID de la cama.", parameters = {
            @Parameter(name = "idHospital", description = "ID del hospital", required = true),
            @Parameter(name = "idCama", description = "ID de la cama que se va a eliminar", required = true)
    }, responses = {
            @ApiResponse(responseCode = "204", description = "Cama eliminada con éxito"),
            @ApiResponse(responseCode = "404", description = "Cama no encontrada")
    })
    @DeleteMapping("/camas/{idCama}") // DELETE /{idHospital}/camas/{idCama}
    public void eliminarCamaDeHospital(@PathVariable Long idHospital, @PathVariable Long idCama) {
        camaRepository.deleteById(idCama);
    }

}
