package com.system.modules.controlobras.api;

import java.time.LocalDate;
import java.util.List;

import com.system.crosscutting.domain.model.ChangeStatusRequestDto;
import com.system.crosscutting.domain.model.DeleteRequestDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreporteoperacionResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.ResponsePayloadUtil;
import com.system.modules.controlobras.usecase.EntyReporteOperacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/control-obras/reportes-operacion")
public class ReporteOperacionController {

    @Autowired
    private EntyReporteOperacionService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Reporte operación service OK");
    }

    @PostMapping("/create")
    public ResponseEntity<EntyOrsplamdreporteoperacionDto> create(
            @RequestBody final EntyOrsplamdreporteoperacionResponse request
    ) throws EBusinessException {

        EntyOrsplamdreporteoperacionDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un reporte de operación en rspData."
        );

        return ResponseEntity.ok(service.saveBefore(dto));
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> createList(
            @RequestBody final EntyOrsplamdreporteoperacionResponse request
    ) throws EBusinessException {

        List<EntyOrsplamdreporteoperacionDto> dtoList = ResponsePayloadUtil.getData(
                request.getRspData(),
                "Debe enviar reportes de operación en rspData."
        );

        return ResponseEntity.ok(service.saveBefore(dtoList));
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsplamdreporteoperacionResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsplamdreporteoperacionResponse> getAllPages(
            @RequestParam(defaultValue = "1") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(defaultValue = "TEXT") final String parameter,
            @RequestParam(defaultValue = "") final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(service.getAll(currentPage, pageSize, parameter, filter));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyOrsplamdreporteoperacionDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyOrsplamdreporteoperacionDto> findByKey(
            @RequestParam final String reporteOperacionKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByKey(reporteOperacionKey));
    }

    @GetMapping("/by-orden")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> findByOrden(
            @RequestParam final String ordenKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByOrden(ordenKey));
    }

    @GetMapping("/by-proyeccion-semana")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> findByProyeccionSemana(
            @RequestParam final String proyeccionSemanaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByProyeccionSemana(proyeccionSemanaKey));
    }

    @GetMapping("/by-plan-semanal")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> findByPlanSemanal(
            @RequestParam final String planSemanalKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByPlanSemanal(planSemanalKey));
    }

    @GetMapping("/by-punto")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> findByPunto(
            @RequestParam final String puntoKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByPunto(puntoKey));
    }

    @GetMapping("/by-proveedor")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> findByProveedor(
            @RequestParam final String proveedorKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByProveedor(proveedorKey));
    }

    @GetMapping("/by-fecha")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> findByFecha(
            @RequestParam final String fecha
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.findByFechaReporte(LocalDate.parse(fecha))
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<List<EntyOrsplamdreporteoperacionDto>> findByEstado(
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyOrsplamdreporteoperacionDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyOrsplamdreporteoperacionResponse request
    ) throws EBusinessException {

        EntyOrsplamdreporteoperacionDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un reporte de operación en rspData."
        );

        return ResponseEntity.ok(service.updateBefore(id, dto));
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyOrsplamdreporteoperacionDto> changestatus(
            @PathVariable final Integer id,
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.changestatus(id, estado));
    }

    @PostMapping("/changestatus")
    public ResponseEntity<String> changestatusBody(
            @RequestBody final List<ChangeStatusRequestDto> request
    ) throws EBusinessException {

        if (request == null || request.isEmpty()) {
            throw new EBusinessException("Debe enviar al menos un registro para cambiar estado.");
        }

        for (ChangeStatusRequestDto item : request) {
            if (item.getRecPKey() == null) {
                throw new EBusinessException("recPKey es obligatorio.");
            }

            if (item.getRecEstreg() == null || item.getRecEstreg().trim().isEmpty()) {
                throw new EBusinessException("recEstreg es obligatorio.");
            }

            service.changestatus(item.getRecPKey(), item.getRecEstreg());
        }

        return ResponseEntity.ok("Estado actualizado correctamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Integer id
    ) throws EBusinessException {
        service.deleteBefore(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteBody(
            @RequestBody final List<DeleteRequestDto> request
    ) throws EBusinessException {

        if (request == null || request.isEmpty()) {
            throw new EBusinessException("Debe enviar al menos un registro para eliminar.");
        }

        for (DeleteRequestDto item : request) {
            if (item.getRecPKey() == null) {
                throw new EBusinessException("recPKey es obligatorio.");
            }

            service.deleteBefore(item.getRecPKey());
        }

        return ResponseEntity.ok("Registro(s) eliminado(s) correctamente.");
    }
}