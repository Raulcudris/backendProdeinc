package com.system.modules.controlobras.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyOrspspmdplansemanalDto;
import com.system.crosscutting.domain.model.EntyOrspspmdplansemanalResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.PlanSemanalService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/control-obras/planes-semanales",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class PlanSemanalController {

    private final PlanSemanalService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = "GET", value = "Consultar proyecciones semanales", notes = "")
    public ResponseEntity<EntyOrspspmdplansemanalResponse> getAll(
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

    @GetMapping("by-plan")
    @ApiOperation(httpMethod = "GET", value = "Consultar proyecciones semanales por plan", notes = "")
    public ResponseEntity<EntyOrspspmdplansemanalResponse> getByPlan(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "planKey") String planKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByPlan(currentPage, pageSize, planKey),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = "POST", value = "Crear proyección semanal", notes = "")
    public ResponseEntity<EntyOrspspmdplansemanalDto> create(
            @RequestBody EntyOrspspmdplansemanalDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = "PUT", value = "Actualizar proyección semanal", notes = "")
    public ResponseEntity<EntyOrspspmdplansemanalDto> update(
            @PathVariable Integer id,
            @RequestBody EntyOrspspmdplansemanalDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("changestatus/{id}")
    @ApiOperation(httpMethod = "PATCH", value = "Cambiar estado de proyección semanal", notes = "")
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
    @ApiOperation(httpMethod = "DELETE", value = "Eliminar proyección semanal", notes = "")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}