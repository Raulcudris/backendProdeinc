package com.system.modules.evidencia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.usecase.EntyReferenciaEvidenciaService;

@RestController
@RequestMapping(
        value = "/api/evidencias/referencias",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ReferenciaEvidenciaController {

    @Autowired
    private EntyReferenciaEvidenciaService service;

    @GetMapping("/pages")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getAll(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "parameter", required = false, defaultValue = "TEXT") String parameter,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(currentPage, pageSize, parameter, filter),
                HttpStatus.OK
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaDto> get(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-evidencia")
    public ResponseEntity<?> findByEvidencia(
            @RequestParam String evidenciaKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByEvidencia(evidenciaKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-registro")
    public ResponseEntity<?> findByRegistro(
            @RequestParam String registroKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByRegistro(registroKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-tipo-registro")
    public ResponseEntity<?> findByTipoRegistro(
            @RequestParam String tipoRegistro
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByTipoRegistro(tipoRegistro),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-tipo-registro-and-registro")
    public ResponseEntity<?> findByTipoRegistroAndRegistro(
            @RequestParam String tipoRegistro,
            @RequestParam String registroKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByTipoRegistroAndRegistro(tipoRegistro, registroKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<?> findByEstado(
            @RequestParam String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByEstado(estado),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<EntyEvirefmdreferenciaDto> create(
            @RequestBody EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaDto> update(
            @PathVariable Integer id,
            @RequestBody EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<String> changestatus(
            @PathVariable Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changestatus(id, estado),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}