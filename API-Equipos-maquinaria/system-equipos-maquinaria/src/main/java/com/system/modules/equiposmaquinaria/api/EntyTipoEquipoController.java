package com.system.modules.equiposmaquinaria.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdequipmaquinariaResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.usecase.EntyTipoEquipoService;

@RestController
@RequestMapping(
        value = "/api/equipos-maquinaria/tipos",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyTipoEquipoController {

    @Autowired
    private EntyTipoEquipoService service;

    @GetMapping("/pages")
    public ResponseEntity<EntyPrvinvmdequipmaquinariaResponse> getAll(
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

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyPrvinvmdequipmaquinariaDto> get(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyPrvinvmdequipmaquinariaDto> findByKey(
            @RequestParam String tipoEquipoKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByKey(tipoEquipoKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-unidad")
    public ResponseEntity<?> findByUnidad(
            @RequestParam String unidadKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByUnidad(unidadKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<?> findByEstado(
            @RequestParam String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByEstado(estado),
                HttpStatus.OK
        );
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyPrvinvmdequipmaquinariaDto> create(
            @RequestBody EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyPrvinvmdequipmaquinariaDto> update(
            @PathVariable Integer id,
            @RequestBody EntyPrvinvmdequipmaquinariaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<String> changestatus(
            @PathVariable Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changestatus(id, estado),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}