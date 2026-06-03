package com.system.modules.documentosobra.api;
import com.system.crosscutting.domain.model.EntyDocdocmadocumentoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.documentosobra.usecase.EntyDocumentoObraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para consultar documentos de obra.
 */
@RestController
@RequiredArgsConstructor
public class EntyDocumentoObraWebApi {

    private final EntyDocumentoObraService service;

    /**
     * Consulta todos los documentos registrados.
     *
     * @return listado de documentos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/documentos/pages")
    public ResponseEntity<List<EntyDocdocmadocumentoDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta documentos por tipo documental.
     *
     * @param tipoDocumentoKey identificador funcional del tipo documental.
     * @return listado de documentos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/documentos/by-tipo")
    public ResponseEntity<List<EntyDocdocmadocumentoDto>> findByTipoDocumento(@RequestParam final String tipoDocumentoKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByTipoDocumento(tipoDocumentoKey));
    }

    /**
     * Consulta documentos por referencia.
     *
     * @param tipoReferencia tipo de referencia asociada al documento.
     * @param referenciaId identificador del registro referenciado.
     * @return listado de documentos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/documentos/by-referencia")
    public ResponseEntity<List<EntyDocdocmadocumentoDto>> findByReferencia(@RequestParam final String tipoReferencia, @RequestParam final String referenciaId) throws EBusinessException {
        return ResponseEntity.ok(service.findByReferencia(tipoReferencia, referenciaId));
    }

    /**
     * Consulta documentos vencidos.
     *
     * @return listado de documentos vencidos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/documentos/vencidos")
    public ResponseEntity<List<EntyDocdocmadocumentoDto>> findVencidos() throws EBusinessException {
        return ResponseEntity.ok(service.findVencidos());
    }

    @PostMapping("/api/documentos-obra/documentos/create")
    public ResponseEntity<EntyDocdocmadocumentoDto> create(@RequestBody final EntyDocdocmadocumentoDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}
