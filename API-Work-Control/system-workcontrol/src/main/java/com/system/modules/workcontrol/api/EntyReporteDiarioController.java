package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyReporteDiarioService;

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
        value = "/api/workcontrol/reporte-diario",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyReporteDiarioController {

    private final EntyReporteDiarioService reporteDiarioService;

    public EntyReporteDiarioController(
            final EntyReporteDiarioService reporteDiarioService
    ) {
        this.reporteDiarioService = reporteDiarioService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsplamdreportediarioResponse> create(
            @RequestBody final EntyOrsplamdreportediarioDto request
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.createResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-orden")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> getByOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.getByOrdenResponse(ordenKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-plan-semana")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> getByPlanSemana(
            @RequestParam("planSemanaKey") final String planSemanaKey
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.getByPlanSemanaResponse(planSemanaKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-semana")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> getBySemana(
            @RequestParam("semanaKey") final String semanaKey
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.getBySemanaResponse(semanaKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> getAll()
            throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.getResponse(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsplamdreportediarioResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyOrsplamdreportediarioDto request
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.updateResponse(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> cerrar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.cerrarResponse(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> cancelar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.cancelarResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsplamdreportediarioResponse response =
                reporteDiarioService.deleteResponse(id);

        return ResponseEntity.ok(response);
    }
}