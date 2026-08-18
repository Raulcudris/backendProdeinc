package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadtiposResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyTipoNovedadService;

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
        value = "/api/workcontrol/tipo-novedad",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyTipoNovedadController {

    private final EntyTipoNovedadService tipoNovedadService;

    public EntyTipoNovedadController(
            final EntyTipoNovedadService tipoNovedadService
    ) {
        this.tipoNovedadService = tipoNovedadService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> create(
            @RequestBody final EntyOrsconfnovedadtiposDto request
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.createResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> getByEstado(
            @RequestParam("estado") final String estado
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.getByEstadoResponse(estado);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> getAll()
            throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.getResponse(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyOrsconfnovedadtiposDto request
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.updateResponse(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> activar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.activarResponse(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> inactivar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.inactivarResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyOrsconfnovedadtiposResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadtiposResponse response =
                tipoNovedadService.deleteResponse(id);

        return ResponseEntity.ok(response);
    }
}