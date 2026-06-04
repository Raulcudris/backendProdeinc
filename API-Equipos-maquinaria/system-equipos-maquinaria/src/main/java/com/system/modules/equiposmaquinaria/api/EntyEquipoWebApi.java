package com.system.modules.equiposmaquinaria.api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.system.crosscutting.domain.constants.ApiConstants;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposDto;
import com.system.crosscutting.domain.model.EntyEquinvmaequiposResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.usecase.EntyEquipoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/equipos-maquinaria/equipos",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyEquipoWebApi {

    private final EntyEquipoService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyEquinvmaequiposResponse> getAll(
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

    @GetMapping("disponibles")
    @ApiOperation(httpMethod = ApiConstants.GET_HTTP, value = ApiConstants.GET_ALL_DESC, notes = "")
    public ResponseEntity<EntyEquinvmaequiposResponse> getDisponibles(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(currentPage, pageSize, "DISPONIBLES", ""),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = ApiConstants.POST_HTTP, value = ApiConstants.POST_DESC, notes = "")
    public ResponseEntity<EntyEquinvmaequiposDto> create(
            @RequestBody EntyEquinvmaequiposDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = ApiConstants.PUT_HTTP, value = ApiConstants.PUT_DESC, notes = "")
    public ResponseEntity<EntyEquinvmaequiposDto> update(
            @PathVariable Integer id,
            @RequestBody EntyEquinvmaequiposDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
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

    @PatchMapping("changeoperativo/{id}")
    @ApiOperation(httpMethod = ApiConstants.PATCH_HTTP, value = ApiConstants.PATCH_DESC, notes = "")
    public ResponseEntity<String> changeEstadoOperativo(
            @PathVariable Integer id,
            @RequestParam(value = "estadoOperativo", required = false, defaultValue = "1") String estadoOperativo
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changeEstadoOperativo(id, estadoOperativo),
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