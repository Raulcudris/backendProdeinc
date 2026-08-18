package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyReferenciaEvidenciaService;

import org.springframework.http.MediaType;
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

@RestController
@RequestMapping(
        value = "/api/workcontrol/evidencias/referencias",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyReferenciaEvidenciaController {

    private final EntyReferenciaEvidenciaService referenciaEvidenciaService;

    public EntyReferenciaEvidenciaController(
            final EntyReferenciaEvidenciaService referenciaEvidenciaService
    ) {
        this.referenciaEvidenciaService = referenciaEvidenciaService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvirefmdreferenciaResponse> create(
            @RequestBody final EntyEvirefmdreferenciaDto request
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.createResponse(request)
        );
    }

    @GetMapping("/by-evidencia")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getByEvidencia(
            @RequestParam("evidenciaKey") final String evidenciaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.getByEvidenciaResponse(
                        evidenciaKey
                )
        );
    }

    @GetMapping("/by-registro")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getByRegistro(
            @RequestParam("tipoRegistro") final String tipoRegistro,
            @RequestParam("identificadorRegistro")
            final String identificadorRegistro
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.getByRegistroResponse(
                        tipoRegistro,
                        identificadorRegistro
                )
        );
    }

    @GetMapping("/all")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.getAll()
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.getResponse(id)
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvirefmdreferenciaResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyEvirefmdreferenciaDto request
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.updateResponse(id, request)
        );
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> activar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.activarResponse(id)
        );
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> inactivar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.inactivarResponse(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                referenciaEvidenciaService.deleteResponse(id)
        );
    }
}