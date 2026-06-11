package com.system.modules.controlobras.api;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionDto;
import com.system.crosscutting.domain.model.EntyOrsordmaactamodificacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.EntyActaModificacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/control-obras/actas-modificacion")
public class ActaModificacionController {

    @Autowired
    private EntyActaModificacionService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Acta modificación service OK");
    }

    @PostMapping("/create")
    public ResponseEntity<EntyOrsordmaactamodificacionDto> create(
            @RequestBody final EntyOrsordmaactamodificacionDto dto
    ) throws EBusinessException {
        return ResponseEntity.ok(service.saveBefore(dto));
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyOrsordmaactamodificacionDto>> createList(
            @RequestBody final List<EntyOrsordmaactamodificacionDto> dtoList
    ) throws EBusinessException {
        return ResponseEntity.ok(service.saveBefore(dtoList));
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsordmaactamodificacionResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsordmaactamodificacionResponse> getAllPages(
            @RequestParam(defaultValue = "1") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(defaultValue = "TEXT") final String parameter,
            @RequestParam(defaultValue = "") final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(service.getAll(currentPage, pageSize, parameter, filter));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyOrsordmaactamodificacionDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyOrsordmaactamodificacionDto> findByKey(
            @RequestParam final String actaModificacionKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByKey(actaModificacionKey));
    }

    @GetMapping("/by-orden")
    public ResponseEntity<List<EntyOrsordmaactamodificacionDto>> findByOrden(
            @RequestParam final String ordenKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByOrden(ordenKey));
    }

    @GetMapping("/by-estado-acta")
    public ResponseEntity<List<EntyOrsordmaactamodificacionDto>> findByEstadoActa(
            @RequestParam final String estadoActa
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstadoActa(estadoActa));
    }

    @GetMapping("/by-estado")
    public ResponseEntity<List<EntyOrsordmaactamodificacionDto>> findByEstado(
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyOrsordmaactamodificacionDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyOrsordmaactamodificacionDto dto
    ) throws EBusinessException {
        return ResponseEntity.ok(service.updateBefore(id, dto));
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyOrsordmaactamodificacionDto> changestatus(
            @PathVariable final Integer id,
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.changestatus(id, estado));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Integer id
    ) throws EBusinessException {
        service.deleteBefore(id);
        return ResponseEntity.noContent().build();
    }
}