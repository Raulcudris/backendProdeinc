package com.system.modules.equiposmaquinaria.api;
import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.usecase.EntyAsignacionEquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para consultar asignaciones de equipos, maquinaria, vehículos o herramientas.
 */
@RestController
@RequiredArgsConstructor
public class EntyAsignacionEquipoWebApi {

    private final EntyAsignacionEquipoService service;

    /**
     * Consulta todas las asignaciones de equipos registradas.
     *
     * @return listado de asignaciones.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/asignaciones/pages")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta asignaciones por equipo.
     *
     * @param equipoKey identificador funcional del equipo.
     * @return listado de asignaciones encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/asignaciones/by-equipo")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findByEquipo(@RequestParam final String equipoKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByEquipo(equipoKey));
    }

    /**
     * Consulta asignaciones por orden de servicio.
     *
     * @param ordenKey identificador funcional de la orden.
     * @return listado de asignaciones encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/asignaciones/by-orden")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findByOrden(@RequestParam final String ordenKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByOrden(ordenKey));
    }

    /**
     * Consulta asignaciones por plan de trabajo.
     *
     * @param planKey identificador funcional del plan.
     * @return listado de asignaciones encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/asignaciones/by-plan")
    public ResponseEntity<List<EntyEquasimdasignequipoDto>> findByPlan(@RequestParam final String planKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByPlan(planKey));
    }
}
