package com.system.modules.evidencia.api;

import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.modules.evidencia.usecase.EntyTipoEvidenciaService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para administrar tipos de evidencia.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/evidencias/tipos",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyTipoEvidenciaWebApi {

    private final EntyTipoEvidenciaService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = "GET", value = "Consultar tipos de evidencia", notes = "")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> getAll(
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

    @PostMapping("create")
    @ApiOperation(httpMethod = "POST", value = "Crear tipo de evidencia", notes = "")
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> create(
            @RequestBody EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = "PUT", value = "Actualizar tipo de evidencia", notes = "")
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> update(
            @PathVariable Integer id,
            @RequestBody EntyEvitipmatipoevidenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("changestatus/{id}")
    @ApiOperation(httpMethod = "PATCH", value = "Cambiar estado de tipo de evidencia", notes = "")
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
    @ApiOperation(httpMethod = "DELETE", value = "Eliminar tipo de evidencia", notes = "")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}