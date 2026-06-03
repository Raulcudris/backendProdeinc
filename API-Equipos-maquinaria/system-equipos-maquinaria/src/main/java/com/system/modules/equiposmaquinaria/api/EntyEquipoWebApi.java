package com.system.modules.equiposmaquinaria.api;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.modules.equiposmaquinaria.usecase.EntyEquipoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * Controlador REST para consultar equipos, maquinaria, vehículos y herramientas.
 */
@RestController
@RequiredArgsConstructor
public class EntyEquipoWebApi {

    private final EntyEquipoService service;

    /**
     * Consulta todos los equipos registrados.
     *
     * @return listado de equipos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/equipos/pages")
    public ResponseEntity<List<EntyEquinvmaequiposDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta equipos por tipo.
     *
     * @param tipoEquipoKey identificador funcional del tipo de equipo.
     * @return listado de equipos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/equipos/by-tipo")
    public ResponseEntity<List<EntyEquinvmaequiposDto>> findByTipoEquipo(@RequestParam final String tipoEquipoKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByTipoEquipo(tipoEquipoKey));
    }

    /**
     * Consulta equipos por estado operativo.
     *
     * @param estadoOperativo estado operativo del equipo.
     * @return listado de equipos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/equipos/by-estado")
    public ResponseEntity<List<EntyEquinvmaequiposDto>> findByEstadoOperativo(@RequestParam final String estadoOperativo) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstadoOperativo(estadoOperativo));
    }

    @PostMapping("/api/equipos-maquinaria/equipos/create")
    public ResponseEntity<EntyEquinvmaequiposDto> create(@RequestBody final EntyEquinvmaequiposDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}