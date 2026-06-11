package com.system.modules.equiposmaquinaria.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposDto;
import com.system.crosscutting.domain.model.EntyPrvinvmainventarioequiposResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.usecase.EntyEquipoService;

@RestController
@RequestMapping(
        value = "/api/equipos-maquinaria/equipos",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyEquipoController {

    @Autowired
    private EntyEquipoService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>(
                "msvc-equipos-maquinaria equipos OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyPrvinvmainventarioequiposResponse> getAll(
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
    public ResponseEntity<EntyPrvinvmainventarioequiposDto> get(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyPrvinvmainventarioequiposDto> findByKey(
            @RequestParam String equipoKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByKey(equipoKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-proveedor")
    public ResponseEntity<?> findByProveedor(
            @RequestParam String proveedorKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByProveedor(proveedorKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-tipo-equipo")
    public ResponseEntity<?> findByTipoEquipo(
            @RequestParam String tipoEquipoKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByTipoEquipo(tipoEquipoKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-disponible")
    public ResponseEntity<?> findByDisponible(
            @RequestParam String disponible
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByDisponible(disponible),
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
    public ResponseEntity<EntyPrvinvmainventarioequiposDto> create(
            @RequestBody EntyPrvinvmainventarioequiposDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PostMapping(
            value = "/create-list",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> createList(
            @RequestBody java.util.List<EntyPrvinvmainventarioequiposDto> dtos
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dtos),
                HttpStatus.CREATED
        );
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyPrvinvmainventarioequiposDto> update(
            @PathVariable Integer id,
            @RequestBody EntyPrvinvmainventarioequiposDto dto
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

    @PatchMapping("/changedisponible/{id}")
    public ResponseEntity<String> cambiarDisponibilidad(
            @PathVariable Integer id,
            @RequestParam(value = "disponible", required = false, defaultValue = "2") String disponible
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.cambiarDisponibilidad(id, disponible),
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