package com.system.modules.evidencia.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.usecase.EntyReferenciaEvidenciaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/evidencias/referencias")
public class ReferenciaEvidenciaController {

    private final EntyReferenciaEvidenciaService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Referencia evidencia service OK");
    }

    @PostMapping("/create")
    public ResponseEntity<EntyEvirefmdreferenciaDto> create(
            @RequestBody final EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        return ResponseEntity.ok(service.saveBefore(dto));
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyEvirefmdreferenciaDto>> createList(
            @RequestBody final List<EntyEvirefmdreferenciaDto> dto
    ) throws EBusinessException {
        return ResponseEntity.ok(service.saveBefore(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getPages(
            @RequestParam(defaultValue = "1") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(defaultValue = "TEXT") final String parameter,
            @RequestParam(defaultValue = "") final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.getAll(currentPage, pageSize, parameter, filter)
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyEvirefmdreferenciaDto> byKey(
            @RequestParam final String referenciaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByKey(referenciaKey));
    }

    @GetMapping("/by-evidencia")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byEvidencia(
            @RequestParam final String evidenciaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEvidencia(evidenciaKey));
    }

    @GetMapping("/by-registro")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byRegistro(
            @RequestParam final String registroKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByRegistro(registroKey));
    }

    @GetMapping("/by-tipo-registro")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byTipoRegistro(
            @RequestParam final String tipoRegistro
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByTipoRegistro(tipoRegistro));
    }

    @GetMapping("/by-tipo-registro-and-registro")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byTipoRegistroAndRegistro(
            @RequestParam final String tipoRegistro,
            @RequestParam final String registroKey
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.findByTipoRegistroAndRegistro(tipoRegistro, registroKey)
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byEstado(
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyEvirefmdreferenciaDto dto
    ) throws EBusinessException {
        return ResponseEntity.ok(service.updateBefore(id, dto));
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaDto> changestatus(
            @PathVariable final Integer id,
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.changestatus(id, estado));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Integer id
    ) throws EBusinessException {
        service.deleteBefore(id);
        return ResponseEntity.noContent().build();
    }
}