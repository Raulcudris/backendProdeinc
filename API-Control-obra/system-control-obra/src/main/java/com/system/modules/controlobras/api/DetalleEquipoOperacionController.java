package com.system.modules.controlobras.api;
import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamddetalleequipooperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.EntyDetalleEquipoOperacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/control-obras/detalles-equipos-operacion")
public class DetalleEquipoOperacionController {

    @Autowired
    private EntyDetalleEquipoOperacionService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Detalle equipo operación service OK");
    }

    @PostMapping("/create")
    public ResponseEntity<EntyOrsplamddetalleequipooperacionDto> create(
            @RequestBody final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {
        return ResponseEntity.ok(service.saveBefore(dto));
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> createList(
            @RequestBody final List<EntyOrsplamddetalleequipooperacionDto> dtoList
    ) throws EBusinessException {
        return ResponseEntity.ok(service.saveBefore(dtoList));
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsplamddetalleequipooperacionResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsplamddetalleequipooperacionResponse> getAllPages(
            @RequestParam(defaultValue = "1") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(defaultValue = "TEXT") final String parameter,
            @RequestParam(defaultValue = "") final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(service.getAll(currentPage, pageSize, parameter, filter));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyOrsplamddetalleequipooperacionDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyOrsplamddetalleequipooperacionDto> findByKey(
            @RequestParam final String detalleEquipoOperacionKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByKey(detalleEquipoOperacionKey));
    }

    @GetMapping("/by-reporte-operacion")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByReporteOperacion(
            @RequestParam final String reporteOperacionKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByReporteOperacion(reporteOperacionKey));
    }

    @GetMapping("/by-orden")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByOrden(
            @RequestParam final String ordenKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByOrden(ordenKey));
    }

    @GetMapping("/by-proyeccion-semana")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByProyeccionSemana(
            @RequestParam final String proyeccionSemanaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByProyeccionSemana(proyeccionSemanaKey));
    }

    @GetMapping("/by-plan-semanal")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByPlanSemanal(
            @RequestParam final String planSemanalKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByPlanSemanal(planSemanalKey));
    }

    @GetMapping("/by-punto")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByPunto(
            @RequestParam final String puntoKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByPunto(puntoKey));
    }

    @GetMapping("/by-equipo")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByEquipo(
            @RequestParam final String equipoKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEquipo(equipoKey));
    }

    @GetMapping("/by-tipo-equipo")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByTipoEquipo(
            @RequestParam final String tipoEquipoKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByTipoEquipo(tipoEquipoKey));
    }

    @GetMapping("/by-fecha")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByFechaTrabajo(
            @RequestParam final String fechaTrabajo
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByFechaTrabajo(LocalDate.parse(fechaTrabajo)));
    }

    @GetMapping("/by-estado")
    public ResponseEntity<List<EntyOrsplamddetalleequipooperacionDto>> findByEstado(
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyOrsplamddetalleequipooperacionDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyOrsplamddetalleequipooperacionDto dto
    ) throws EBusinessException {
        return ResponseEntity.ok(service.updateBefore(id, dto));
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyOrsplamddetalleequipooperacionDto> changestatus(
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
