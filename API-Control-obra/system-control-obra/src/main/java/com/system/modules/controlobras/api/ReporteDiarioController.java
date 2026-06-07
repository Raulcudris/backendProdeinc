package com.system.modules.controlobras.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioDto;
import com.system.crosscutting.domain.model.EntyOrsplamdreportediarioResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.ReporteDiarioService;

@RestController
@RequestMapping(
        value = "/api/control-obras/reportes-diarios",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ReporteDiarioController {

    @Autowired
    private ReporteDiarioService service;

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsplamdreportediarioResponse> getAll(
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

    @GetMapping("/by-orden")
    public ResponseEntity<?> findByOrden(
            @RequestParam String ordenKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByOrden(ordenKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-plan-semanal")
    public ResponseEntity<?> findByPlanSemana(
            @RequestParam String planSemanalKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByPlanSemana(planSemanalKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-proyeccion")
    public ResponseEntity<?> findByProyeccionSemana(
            @RequestParam String proyeccionKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByProyeccionSemana(proyeccionKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyOrsplamdreportediarioDto> get(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<EntyOrsplamdreportediarioDto> create(
            @RequestBody EntyOrsplamdreportediarioDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyOrsplamdreportediarioDto> update(
            @PathVariable Integer id,
            @RequestBody EntyOrsplamdreportediarioDto dto
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