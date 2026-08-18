package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyTipoEvidenciaService;

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
        value = "/api/workcontrol/tipo-evidencia",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyTipoEvidenciaController {

    private final EntyTipoEvidenciaService tipoEvidenciaService;

    public EntyTipoEvidenciaController(
            final EntyTipoEvidenciaService tipoEvidenciaService
    ) {
        this.tipoEvidenciaService = tipoEvidenciaService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> create(
            @RequestBody final EntyEvitipmatipoevidenciaDto request
    ) throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.createResponse(request)
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> getByEstado(
            @RequestParam("estado") final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.getByEstadoResponse(estado)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.getAll()
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> getAllPages(
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
                tipoEvidenciaService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.getResponse(id)
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyEvitipmatipoevidenciaDto request
    ) throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.updateResponse(id, request)
        );
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> activar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.activarResponse(id)
        );
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> inactivar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.inactivarResponse(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                tipoEvidenciaService.deleteResponse(id)
        );
    }
}