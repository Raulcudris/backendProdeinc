package com.system.modules.documentosobra.api;


import com.system.crosscutting.domain.model.EntyDocvenmdvencimientoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.documentosobra.usecase.EntyVencimientoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST para consultar vencimientos documentales.
 */
@RestController
@RequiredArgsConstructor
public class EntyVencimientoDocumentoWebApi {

    private final EntyVencimientoDocumentoService service;

    /**
     * Consulta todos los vencimientos documentales.
     *
     * @return listado de vencimientos documentales.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/vencimientos/pages")
    public ResponseEntity<List<EntyDocvenmdvencimientoDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    /**
     * Consulta vencimientos asociados a un documento.
     *
     * @param documentoKey identificador funcional del documento.
     * @return listado de vencimientos encontrados.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/vencimientos/by-documento")
    public ResponseEntity<List<EntyDocvenmdvencimientoDto>> findByDocumento(@RequestParam final String documentoKey) throws EBusinessException {
        return ResponseEntity.ok(service.findByDocumento(documentoKey));
    }

    /**
     * Consulta vencimientos vencidos.
     *
     * @return listado de vencimientos vencidos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/vencimientos/vencidos")
    public ResponseEntity<List<EntyDocvenmdvencimientoDto>> findVencidos() throws EBusinessException {
        return ResponseEntity.ok(service.findVencidos());
    }

    /**
     * Consulta vencimientos próximos dentro de un número de días.
     *
     * @param dias número de días a consultar. Por defecto 30.
     * @return listado de vencimientos próximos.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/documentos-obra/vencimientos/proximos")
    public ResponseEntity<List<EntyDocvenmdvencimientoDto>> findProximos(@RequestParam(defaultValue = "30") final int dias) throws EBusinessException {
        return ResponseEntity.ok(service.findProximos(dias));
    }
}
