package com.ticarum.hospital_camas.controller;

import com.ticarum.hospital_camas.model.Cama;
import com.ticarum.hospital_camas.model.EstadoCama;
import com.ticarum.hospital_camas.repository.CamaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/camas")
public class CamaController {

    private final CamaRepository camaRepository;

    public CamaController(CamaRepository camaRepository) {
        this.camaRepository = camaRepository;
    }

    @Operation(summary = "Obtiene información de una cama", description = "Devuelve un objeto Cama con todos sus datos (hospital, dependencia, estado).", parameters = {
            @Parameter(name = "idCama", description = "ID de la cama a consultar", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Objeto Cama encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Cama.class))),
            @ApiResponse(responseCode = "404", description = "Cama no encontrada")
    })
    @GetMapping("/{idCama}") // GET /camas/{idCama}
    public Optional<Cama> getCama(@PathVariable Long idCama) {
        return camaRepository.findById(idCama);
    }

    @Operation(summary = "Crea una nueva cama en el sistema", description = "Inserta una cama con ID `idCama` en la base de datos. Por defecto, el estado será LIBRE y no estará asignada a ningún hospital ni dependencia.", parameters = {
            @Parameter(name = "idCama", description = "ID que se asignará a la nueva cama", required = true)
    }, responses = {
            @ApiResponse(responseCode = "201", description = "Cama creada con éxito", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Cama.class))),
            @ApiResponse(responseCode = "400", description = "ID inválido o ya existe una cama con ese ID")
    })
    @PostMapping("/{idCama}") // POST /camas/{idCama}
    public Cama crearCama(@PathVariable Long idCama) {
        Cama cama = new Cama();
        cama.setId(idCama);
        cama.setEstado(EstadoCama.LIBRE); // Por defecto estará libre
        return camaRepository.save(cama);
    }

    @Operation(summary = "Actualiza el estado de una cama", description = "Cambia el estado de la cama `idCama` al `nuevoEstado`. No se pueden cambiar estilísticamente de AVERIADA→OCUPADA y tampoco si ya está asignada a un hospital.", parameters = {
            @Parameter(name = "idCama", description = "ID de la cama a actualizar", required = true),
            @Parameter(name = "nuevoEstado", description = "Nuevo estado de la cama (LIBRE, OCUPADA, EN_REPARACION, AVERIADA, BAJA)", required = true)
    }, responses = {
            @ApiResponse(responseCode = "200", description = "Estado actualizado correctamente", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Cama.class))),
            @ApiResponse(responseCode = "400", description = "Cambio de estado no permitido o cama ya asignada"),
            @ApiResponse(responseCode = "404", description = "Cama no encontrada")
    })
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

    @Operation(summary = "Elimina una cama del sistema", description = "Borra la cama con `idCama`. No se requiere validación adicional.", parameters = {
            @Parameter(name = "idCama", description = "ID de la cama a eliminar", required = true)
    }, responses = {
            @ApiResponse(responseCode = "204", description = "Cama eliminada"),
            @ApiResponse(responseCode = "404", description = "Cama no encontrada")
    })
    @DeleteMapping("/{idCama}") // DELETE /camas/{idCama}
    public void eliminarCama(@PathVariable Long idCama) {
        camaRepository.deleteById(idCama);
    }
}
