package com.system.modules.evidencia.api;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.usecase.EntyReferenciaEvidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para consultar referencias de evidencias.
 */
@RestController
@RequiredArgsConstructor
public class EntyReferenciaEvidenciaWebApi {

    private final EntyReferenciaEvidenciaService service;

    /**
     * Consulta todas las referencias de evidencias registradas.
     *
     * @return listado de referencias.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/evidencias/referencias/pages")
    public ResponseEntity<List<EntyEvirefmdreferenciaDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta referencias asociadas a una evidencia.
     *
     * @param evidenciaKey identificador funcional de la evidencia.
     * @return listado de referencias encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/evidencias/referencias/by-evidencia")
    public ResponseEntity<List<EntyEvirefmdreferenciaDto>> findByEvidencia(@RequestParam final String evidenciaKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByEvidencia(evidenciaKey));
    }

    /**
     * Consulta referencias por tipo y código del registro referenciado.
     *
     * @param tipoReferencia tipo de referencia.
     * @param referenciaId código del registro referenciado.
     * @return listado de referencias encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/evidencias/referencias/by-referencia")
    public ResponseEntity<List<EntyEvirefmdreferenciaDto>> findByReferencia(@RequestParam final String tipoReferencia, @RequestParam final String referenciaId) throws EBusinessException {
        return ResponseEntity.ok(service.findByReferencia(tipoReferencia, referenciaId));
    }

    @PostMapping("/api/evidencias/referencias/create")
    public ResponseEntity<EntyEvirefmdreferenciaDto> create(@RequestBody final EntyEvirefmdreferenciaDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}