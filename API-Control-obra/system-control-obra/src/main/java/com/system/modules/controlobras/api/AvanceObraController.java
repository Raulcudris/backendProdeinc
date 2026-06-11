package com.system.modules.controlobras.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.system.crosscutting.domain.model.AvanceObraResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.EntyAvanceObraService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(
        value = "/api/control-obras/avances",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class AvanceObraController {

    @Autowired
    private EntyAvanceObraService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Avance obra service OK");
    }

    @GetMapping("/by-orden")
    public ResponseEntity<AvanceObraResponse> getAvanceByOrden(
            @RequestParam(value = "ordenKey") final String ordenKey
    ) throws EBusinessException, MicroEventException {
        return ResponseEntity.ok(service.getAvanceByOrden(ordenKey));
    }

    @GetMapping("/by-plan")
    public ResponseEntity<AvanceObraResponse> getAvanceByPlan(
            @RequestParam(value = "planKey") final String planKey
    ) throws EBusinessException, MicroEventException {
        return ResponseEntity.ok(service.getAvanceByPlan(planKey));
    }

    @GetMapping("/by-plan-semanal")
    public ResponseEntity<AvanceObraResponse> getAvanceByPlanSemanal(
            @RequestParam(value = "planSemanalKey") final String planSemanalKey
    ) throws EBusinessException, MicroEventException {
        return ResponseEntity.ok(service.getAvanceByPlanSemanal(planSemanalKey));
    }

    @GetMapping("/orden/{codigoOrden}")
    public ResponseEntity<AvanceObraResponse> calcularAvancePorOrdenLegacy(
            @PathVariable final String codigoOrden
    ) throws EBusinessException, MicroEventException {
        return ResponseEntity.ok(service.getAvanceByOrden(codigoOrden));
    }
}