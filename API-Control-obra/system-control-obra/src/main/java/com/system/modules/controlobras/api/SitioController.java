package com.system.modules.controlobras.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyOrssitmdsitioDto;
import com.system.crosscutting.domain.model.EntyOrssitmdsitioResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.SitioService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/control-obras/sitios",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class SitioController {

    private final SitioService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = "GET", value = "Consultar sitios de trabajo", notes = "")
    public ResponseEntity<EntyOrssitmdsitioResponse> getAll(
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
    @ApiOperation(httpMethod = "GET", value = "Consultar sitios por orden de servicio", notes = "")
    public ResponseEntity<EntyOrssitmdsitioResponse> getByOrden(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "ordenKey") String ordenKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByOrden(currentPage, pageSize, ordenKey),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = "POST", value = "Crear sitio de trabajo", notes = "")
    public ResponseEntity<EntyOrssitmdsitioDto> create(
            @RequestBody EntyOrssitmdsitioDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = "PUT", value = "Actualizar sitio de trabajo", notes = "")
    public ResponseEntity<EntyOrssitmdsitioDto> update(
            @PathVariable Integer id,
            @RequestBody EntyOrssitmdsitioDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("changestatus/{id}")
    @ApiOperation(httpMethod = "PATCH", value = "Cambiar estado de sitio", notes = "")
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
    @ApiOperation(httpMethod = "DELETE", value = "Eliminar sitio", notes = "")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}