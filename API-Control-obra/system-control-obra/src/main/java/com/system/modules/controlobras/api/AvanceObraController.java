package com.system.modules.controlobras.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.AvanceObraResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.AvanceObraService;

@RestController
@RequestMapping(
        value = "/api/control-obras/avances",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class AvanceObraController {

    @Autowired
    private AvanceObraService service;

    @GetMapping("/by-orden")
    public ResponseEntity<AvanceObraResponse> getAvanceByOrden(
            @RequestParam(value = "ordenKey") String ordenKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAvanceByOrden(ordenKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-plan")
    public ResponseEntity<AvanceObraResponse> getAvanceByPlan(
            @RequestParam(value = "planKey") String planKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAvanceByPlan(planKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-plan-semanal")
    public ResponseEntity<AvanceObraResponse> getAvanceByPlanSemanal(
            @RequestParam(value = "planSemanalKey") String planSemanalKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAvanceByPlanSemanal(planSemanalKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/orden/{codigoOrden}")
    public ResponseEntity<AvanceObraResponse> calcularAvancePorOrdenLegacy(
            @PathVariable String codigoOrden
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAvanceByOrden(codigoOrden),
                HttpStatus.OK
        );
    }
}