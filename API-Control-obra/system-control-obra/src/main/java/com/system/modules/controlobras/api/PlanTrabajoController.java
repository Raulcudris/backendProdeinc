package com.system.modules.controlobras.api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.system.crosscutting.domain.model.EntyOrsplnmaplantrabajoDto;
import com.system.crosscutting.domain.model.EntyOrsplnmaplantrabajoResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.PlanTrabajoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/control-obras/planes",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class PlanTrabajoController {

    private final PlanTrabajoService service;

    @GetMapping("pages")
    @ApiOperation(httpMethod = "GET", value = "Consultar planes de trabajo", notes = "")
    public ResponseEntity<EntyOrsplnmaplantrabajoResponse> getAll(
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
    @ApiOperation(httpMethod = "GET", value = "Consultar planes por orden", notes = "")
    public ResponseEntity<EntyOrsplnmaplantrabajoResponse> getByOrden(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "ordenKey") String ordenKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getByOrden(currentPage, pageSize, ordenKey),
                HttpStatus.OK
        );
    }

    @GetMapping("by-sitio")
    @ApiOperation(httpMethod = "GET", value = "Consultar planes por sitio", notes = "")
    public ResponseEntity<EntyOrsplnmaplantrabajoResponse> getBySitio(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "sitioKey") String sitioKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getBySitio(currentPage, pageSize, sitioKey),
                HttpStatus.OK
        );
    }

    @PostMapping("create")
    @ApiOperation(httpMethod = "POST", value = "Crear plan de trabajo", notes = "")
    public ResponseEntity<EntyOrsplnmaplantrabajoDto> create(
            @RequestBody EntyOrsplnmaplantrabajoDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("update/{id}")
    @ApiOperation(httpMethod = "PUT", value = "Actualizar plan de trabajo", notes = "")
    public ResponseEntity<EntyOrsplnmaplantrabajoDto> update(
            @PathVariable Integer id,
            @RequestBody EntyOrsplnmaplantrabajoDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("changestatus/{id}")
    @ApiOperation(httpMethod = "PATCH", value = "Cambiar estado de plan", notes = "")
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
    @ApiOperation(httpMethod = "DELETE", value = "Eliminar plan", notes = "")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}