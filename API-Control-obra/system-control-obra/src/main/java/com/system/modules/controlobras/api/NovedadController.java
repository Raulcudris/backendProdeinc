package com.system.modules.controlobras.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyOrsnovmdnovedadDto;
import com.system.crosscutting.domain.model.EntyOrsnovmdnovedadResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.NovedadService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/control-obras/novedades",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class NovedadController {

    private final NovedadService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = "GET", value = "Consultar novedades", notes = "")
    public ResponseEntity<EntyOrsnovmdnovedadResponse> getAll(
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

    @GetMapping("by-reporte")
    @ApiOperation(httpMethod = "GET", value = "Consultar novedades por reporte diario", notes = "")
    public ResponseEntity<EntyOrsnovmdnovedadResponse> getByReporte(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "reporteKey") String reporteKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByReporte(currentPage, pageSize, reporteKey),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = "POST", value = "Crear novedad", notes = "")
    public ResponseEntity<EntyOrsnovmdnovedadDto> create(
            @RequestBody EntyOrsnovmdnovedadDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = "PUT", value = "Actualizar novedad", notes = "")
    public ResponseEntity<EntyOrsnovmdnovedadDto> update(
            @PathVariable Integer id,
            @RequestBody EntyOrsnovmdnovedadDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("changestatus/{id}")
    @ApiOperation(httpMethod = "PATCH", value = "Cambiar estado de novedad", notes = "")
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
    @ApiOperation(httpMethod = "DELETE", value = "Eliminar novedad", notes = "")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}