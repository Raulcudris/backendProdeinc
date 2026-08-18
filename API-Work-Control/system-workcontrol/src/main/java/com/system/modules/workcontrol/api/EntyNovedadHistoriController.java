package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriDto;
import com.system.crosscutting.domain.model.EntyOrsconfnovedadhistoriResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyNovedadHistoriService;

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
        value = "/api/workcontrol/novedades",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyNovedadHistoriController {

    private final EntyNovedadHistoriService novedadHistoriService;

    public EntyNovedadHistoriController(
            final EntyNovedadHistoriService novedadHistoriService
    ) {
        this.novedadHistoriService = novedadHistoriService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> create(
            @RequestBody final EntyOrsconfnovedadhistoriDto request
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.createResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-orden")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> getByOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.getByOrdenResponse(ordenKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-tipo")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> getByTipo(
            @RequestParam("tipoNovedad") final String tipoNovedad
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.getByTipoResponse(tipoNovedad);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> getAll()
            throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.getResponse(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyOrsconfnovedadhistoriDto request
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.updateResponse(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> activar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.activarResponse(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> inactivar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.inactivarResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyOrsconfnovedadhistoriResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsconfnovedadhistoriResponse response =
                novedadHistoriService.deleteResponse(id);

        return ResponseEntity.ok(response);
    }
}