package com.system.modules.controlobras.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioDto;
import com.system.crosscutting.domain.model.EntyOrsrdomdreporteDiarioResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.ReporteDiarioService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/control-obras/reportes-diarios",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ReporteDiarioController {

    private final ReporteDiarioService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = "GET", value = "Consultar reportes diarios", notes = "")
    public ResponseEntity<EntyOrsrdomdreporteDiarioResponse> getAll(
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

    @GetMapping("by-orden")
    @ApiOperation(httpMethod = "GET", value = "Consultar reportes por orden", notes = "")
    public ResponseEntity<EntyOrsrdomdreporteDiarioResponse> getByOrden(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "ordenKey") String ordenKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByOrden(currentPage, pageSize, ordenKey),
                HttpStatus.OK
        );
    }

    @GetMapping("by-plan")
    @ApiOperation(httpMethod = "GET", value = "Consultar reportes por plan", notes = "")
    public ResponseEntity<EntyOrsrdomdreporteDiarioResponse> getByPlan(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "planKey") String planKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByPlan(currentPage, pageSize, planKey),
                HttpStatus.OK
        );
    }

    @GetMapping("by-plan-semanal")
    @ApiOperation(httpMethod = "GET", value = "Consultar reportes por plan semanal", notes = "")
    public ResponseEntity<EntyOrsrdomdreporteDiarioResponse> getByPlanSemanal(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "planSemanalKey") String planSemanalKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByPlanSemanal(currentPage, pageSize, planSemanalKey),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = "POST", value = "Crear reporte diario", notes = "")
    public ResponseEntity<EntyOrsrdomdreporteDiarioDto> create(
            @RequestBody EntyOrsrdomdreporteDiarioDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = "PUT", value = "Actualizar reporte diario", notes = "")
    public ResponseEntity<EntyOrsrdomdreporteDiarioDto> update(
            @PathVariable Integer id,
            @RequestBody EntyOrsrdomdreporteDiarioDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("changestatus/{id}")
    @ApiOperation(httpMethod = "PATCH", value = "Cambiar estado de reporte diario", notes = "")
    public ResponseEntity<String> changestatus(
            @PathVariable Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changestatus(id, estado),
                HttpStatus.OK
        );
    }

    @DeleteMapping("delete/{id}")
    @ApiOperation(httpMethod = "DELETE", value = "Eliminar reporte diario", notes = "")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}