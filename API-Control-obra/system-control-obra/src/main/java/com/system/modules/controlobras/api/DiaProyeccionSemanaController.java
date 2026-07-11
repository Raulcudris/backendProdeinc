package com.system.modules.controlobras.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.crosscutting.domain.model.EntyDiaProyeccionSemanaDto;
import com.system.crosscutting.domain.model.GenerarSemanasRequestDto;
import com.system.crosscutting.domain.model.ProyeccionSemanaConDiasDto;
import com.system.modules.controlobras.usecase.DiaProyeccionSemanaService;

@RestController
@RequestMapping("/api/control-obras/proyeccion-semanal")
public class DiaProyeccionSemanaController {

    private final DiaProyeccionSemanaService diaProyeccionSemanaService;

    public DiaProyeccionSemanaController(
            final DiaProyeccionSemanaService diaProyeccionSemanaService
    ) {
        this.diaProyeccionSemanaService = diaProyeccionSemanaService;
    }

    @GetMapping("/dias/by-proyeccion")
    public ResponseEntity<List<EntyDiaProyeccionSemanaDto>> getDiasByProyeccion(
            @RequestParam("proyeccionKey") final String proyeccionKey
    ) {
        List<EntyDiaProyeccionSemanaDto> response =
                diaProyeccionSemanaService.getDiasByProyeccion(proyeccionKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dias/by-orden")
    public ResponseEntity<List<EntyDiaProyeccionSemanaDto>> getDiasByOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) {
        List<EntyDiaProyeccionSemanaDto> response =
                diaProyeccionSemanaService.getDiasByOrden(ordenKey);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/generar-semanas")
    public ResponseEntity<List<ProyeccionSemanaConDiasDto>> generarSemanas(
            @RequestBody final GenerarSemanasRequestDto request
    ) {
        List<ProyeccionSemanaConDiasDto> response =
                diaProyeccionSemanaService.generarSemanas(request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/dias/{diaKey}/habilitar")
    public ResponseEntity<EntyDiaProyeccionSemanaDto> cambiarHabilitado(
            @PathVariable("diaKey") final String diaKey,
            @RequestParam("habilitado") final Boolean habilitado
    ) {
        EntyDiaProyeccionSemanaDto response =
                diaProyeccionSemanaService.cambiarHabilitado(
                        diaKey,
                        habilitado
                );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/dias/{diaKey}/trabajado")
    public ResponseEntity<EntyDiaProyeccionSemanaDto> cambiarTrabajado(
            @PathVariable("diaKey") final String diaKey,
            @RequestParam("trabajado") final Boolean trabajado
    ) {
        EntyDiaProyeccionSemanaDto response =
                diaProyeccionSemanaService.cambiarTrabajado(
                        diaKey,
                        trabajado
                );

        return ResponseEntity.ok(response);
    }
}