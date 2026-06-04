package com.system.modules.equiposmaquinaria.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.constants.ApiConstants;
import com.system.crosscutting.domain.model.EntyEquasimdasignequipoDto;
import com.system.crosscutting.domain.model.EntyEquasimdasignequipoResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.usecase.EntyAsignacionEquipoService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/equipos-maquinaria/asignaciones",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyAsignacionEquipoWebApi {

    private final EntyAsignacionEquipoService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyEquasimdasignequipoResponse> getAll(
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

    @GetMapping("by-equipo")
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyEquasimdasignequipoResponse> getByEquipo(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "equipoKey") String equipoKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByEquipo(currentPage, pageSize, equipoKey),
                HttpStatus.OK
        );
    }

    @GetMapping("by-orden")
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyEquasimdasignequipoResponse> getByOrden(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "ordenKey") String ordenKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByOrden(currentPage, pageSize, ordenKey),
                HttpStatus.OK
        );
    }

    @GetMapping("by-responsable")
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyEquasimdasignequipoResponse> getByResponsable(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "responsable") String responsable
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByResponsable(currentPage, pageSize, responsable),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = ApiConstants.POST_HTTP, value = ApiConstants.POST_DESC, notes = "")
    public ResponseEntity<EntyEquasimdasignequipoDto> create(
            @RequestBody EntyEquasimdasignequipoDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = ApiConstants.PUT_HTTP, value = ApiConstants.PUT_DESC, notes = "")
    public ResponseEntity<EntyEquasimdasignequipoDto> update(
            @PathVariable Integer id,
            @RequestBody EntyEquasimdasignequipoDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("cerrar/{id}")
    @ApiOperation(httpMethod = ApiConstants.PATCH_HTTP, value = ApiConstants.PATCH_DESC, notes = "")
    public ResponseEntity<String> cerrar(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.cerrarAsignacion(id),
                HttpStatus.OK
        );
    }

    @PatchMapping("changestatus/{id}")
    @ApiOperation(httpMethod = ApiConstants.PATCH_HTTP, value = ApiConstants.PATCH_DESC, notes = "")
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
    @ApiOperation(httpMethod = ApiConstants.DELETE_HTTP, value = ApiConstants.DELETE_DESC, notes = "")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}