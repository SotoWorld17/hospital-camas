package com.ticarum.hospital_camas.controller;

import com.ticarum.hospital_camas.model.Cama;
import com.ticarum.hospital_camas.model.EstadoCama;
import com.ticarum.hospital_camas.repository.CamaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/camas")
public class CamaController {

    private final CamaRepository camaRepository;

    public CamaController(CamaRepository camaRepository) {
        this.camaRepository = camaRepository;
    }

    @GetMapping("/{idCama}") // GET /camas/{idCama}
    public Optional<Cama> getCama(@PathVariable Long idCama) {
        return camaRepository.findById(idCama);
    }

    @PostMapping("/{idCama}") // POST /camas/{idCama}
    public Cama crearCama(@PathVariable Long idCama) {
        Cama cama = new Cama();
        cama.setId(idCama);
        cama.setEstado(EstadoCama.LIBRE); // Por defecto estará libre
        return camaRepository.save(cama);
    }

    @PutMapping("/{idCama}") // PUT /camas/{idCama}
    public Cama actualizarEstadoCama(@PathVariable Long idCama, @RequestParam EstadoCama nuevoEstado) {
        Cama cama = camaRepository.findById(idCama).orElseThrow();

        if (cama.getEstado() == EstadoCama.AVERIADA && nuevoEstado == EstadoCama.OCUPADA) {
            throw new IllegalArgumentException(
                    "No se puede cambiar de AVERIADA a OCUPADA.");
        }

        if (cama.getHospital() != null) {
            throw new IllegalArgumentException(
                    "No se puede actualizar esta cama desde aquí porque ya está asignada a un hospital.");
        }

        cama.setEstado(nuevoEstado);
        return camaRepository.save(cama);
    }

    @DeleteMapping("/{idCama}") // DELETE /camas/{idCama}
    public void eliminarCama(@PathVariable Long idCama) {
        camaRepository.deleteById(idCama);
    }
}
