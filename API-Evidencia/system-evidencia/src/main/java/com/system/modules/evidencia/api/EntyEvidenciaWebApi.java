package com.system.modules.evidencia.api;


import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.usecase.EntyEvidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para consultar evidencias.
 */
@RestController
@RequiredArgsConstructor
public class EntyEvidenciaWebApi {
    private final EntyEvidenciaService service;

    /**
     * Consulta todas las evidencias registradas.
     *
     * @return listado de evidencias.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/evidencias/pages")
    public ResponseEntity<List<EntyEvievimaevidenciaDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta evidencias por tipo.
     *
     * @param tipoEvidenciaKey identificador funcional del tipo de evidencia.
     * @return listado de evidencias encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/evidencias/by-tipo")
    public ResponseEntity<List<EntyEvievimaevidenciaDto>> findByTipoEvidencia(@RequestParam final String tipoEvidenciaKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByTipoEvidencia(tipoEvidenciaKey));
    }

    /**
     * Consulta evidencias por usuario creador.
     *
     * @param usuarioCrea usuario que registró la evidencia.
     * @return listado de evidencias encontradas.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/evidencias/by-usuario")
    public ResponseEntity<List<EntyEvievimaevidenciaDto>> findByUsuarioCrea(@RequestParam final String usuarioCrea) throws EBusinessException {
        return ResponseEntity.ok(service.findByUsuarioCrea(usuarioCrea));
    }

    @PostMapping("/api/evidencias/create")
    public ResponseEntity<EntyEvievimaevidenciaDto> create(@RequestBody final EntyEvievimaevidenciaDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}
