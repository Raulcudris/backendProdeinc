package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplamaplandetrabajoResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyPlanTrabajoService;

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
        value = "/api/workcontrol/plan-trabajo",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyPlanTrabajoController {

    private final EntyPlanTrabajoService planTrabajoService;

    public EntyPlanTrabajoController(
            final EntyPlanTrabajoService planTrabajoService
    ) {
        this.planTrabajoService = planTrabajoService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> create(
            @RequestBody final EntyOrsplamaplandetrabajoDto request
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.createResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-orden")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> getByOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.getByOrdenResponse(ordenKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-punto")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> getByPunto(
            @RequestParam("puntoKey") final String puntoKey
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.getByPuntoResponse(puntoKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> getAll()
            throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.getResponse(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyOrsplamaplandetrabajoDto request
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.updateResponse(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> cerrar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.cerrarResponse(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> cancelar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.cancelarResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyOrsplamaplandetrabajoResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamaplandetrabajoResponse response =
                planTrabajoService.deleteResponse(id);

        return ResponseEntity.ok(response);
    }
}