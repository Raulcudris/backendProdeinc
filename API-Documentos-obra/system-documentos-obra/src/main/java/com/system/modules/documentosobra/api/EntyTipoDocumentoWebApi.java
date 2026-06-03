package com.system.modules.documentosobra.api;
import com.system.crosscutting.domain.model.EntyDoctipmatipodocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.documentosobra.usecase.EntyTipoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para consultar tipos de documentos.
 */
@RestController
@RequiredArgsConstructor
public class EntyTipoDocumentoWebApi {
    private final EntyTipoDocumentoService service;

    /**
     * Consulta todos los tipos de documentos.
     *
     * @return listado de tipos de documentos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/tipos/pages")
    public ResponseEntity<List<EntyDoctipmatipodocumentoDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta tipos de documentos por categoría documental.
     *
     * @param categoriaKey identificador funcional de la categoría documental.
     * @return listado de tipos de documentos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/tipos/by-categoria")
    public ResponseEntity<List<EntyDoctipmatipodocumentoDto>> findByCategoria(@RequestParam final String categoriaKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByCategoria(categoriaKey));
    }


    @PostMapping("/api/documentos-obra/tipos/create")
    public ResponseEntity<EntyDoctipmatipodocumentoDto> create(@RequestBody final EntyDoctipmatipodocumentoDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}
