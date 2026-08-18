package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaDto;
import com.system.crosscutting.domain.model.EntyOrsplamdplantrabsemanaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyPlanTrabajoSemanaService;

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
        value = "/api/workcontrol/plan-trabajo-semana",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyPlanTrabajoSemanaController {

    private final EntyPlanTrabajoSemanaService planTrabajoSemanaService;

    public EntyPlanTrabajoSemanaController(
            final EntyPlanTrabajoSemanaService planTrabajoSemanaService
    ) {
        this.planTrabajoSemanaService = planTrabajoSemanaService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> create(
            @RequestBody final EntyOrsplamdplantrabsemanaDto request
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.createResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-orden")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> getByOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.getByOrdenResponse(ordenKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-plan")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> getByPlanTrabajo(
            @RequestParam("planTrabajoKey") final String planTrabajoKey
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.getByPlanTrabajoResponse(
                        planTrabajoKey
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-semana")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> getBySemana(
            @RequestParam("semanaKey") final String semanaKey
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.getBySemanaResponse(semanaKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> getAll()
            throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.getResponse(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyOrsplamdplantrabsemanaDto request
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.updateResponse(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> cerrar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.cerrarResponse(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> cancelar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.cancelarResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyOrsplamdplantrabsemanaResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdplantrabsemanaResponse response =
                planTrabajoSemanaService.deleteResponse(id);

        return ResponseEntity.ok(response);
    }
}