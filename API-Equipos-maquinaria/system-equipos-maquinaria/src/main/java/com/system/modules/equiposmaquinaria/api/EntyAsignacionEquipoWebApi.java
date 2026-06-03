package com.system.modules.equiposmaquinaria.api;
import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.usecase.EntyAsignacionEquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para consultar asignaciones de equipos, maquinaria, vehículos o herramientas.
 */
@RestController
@RequiredArgsConstructor
public class EntyAsignacionEquipoWebApi {

    private final EntyAsignacionEquipoService service;

    @GetMapping("/api/equipos-maquinaria/asignaciones/pages")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/api/equipos-maquinaria/asignaciones/by-equipo")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findByEquipo(@RequestParam final String equipoKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByEquipo(equipoKey));
    }

    @GetMapping("/api/equipos-maquinaria/asignaciones/by-orden")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findByOrden(@RequestParam final String ordenKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByOrden(ordenKey));
    }

    @GetMapping("/api/equipos-maquinaria/asignaciones/by-plan")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findByPlan(@RequestParam final String planKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByPlan(planKey));
    }

    @PostMapping("/api/equipos-maquinaria/asignaciones/create")
    public ResponseEntity<EntyEquasimdasignequipoDto> create(@RequestBody final EntyEquasimdasignequipoDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}
