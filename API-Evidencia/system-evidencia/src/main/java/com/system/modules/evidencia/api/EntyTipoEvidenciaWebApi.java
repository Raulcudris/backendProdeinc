package com.system.modules.evidencia.api;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.usecase.EntyTipoEvidenciaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para consultar tipos de evidencia.
 */
@RestController
@RequiredArgsConstructor
public class EntyTipoEvidenciaWebApi {

    private final EntyTipoEvidenciaService service;

    /**
     * Consulta todos los tipos de evidencia.
     *
     * @return listado de tipos de evidencia.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/evidencias/tipos/pages")
    public ResponseEntity<List<EntyEvitipmatipoevidenciaDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/api/evidencias/tipos/create")
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> create(@RequestBody final EntyEvitipmatipoevidenciaDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }

}
