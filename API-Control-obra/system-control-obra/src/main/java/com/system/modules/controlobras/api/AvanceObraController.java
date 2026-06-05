package com.system.modules.controlobras.api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.system.crosscutting.domain.model.AvanceObraResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.AvanceObraService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/control-obras/avances",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class AvanceObraController {

    private final AvanceObraService service;

    @GetMapping("by-orden")
    @ApiOperation(httpMethod = "GET", value = "Consultar avance por orden de servicio", notes = "")
    public ResponseEntity<AvanceObraResponse> getAvanceByOrden(
            @RequestParam(value = "ordenKey") String ordenKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAvanceByOrden(ordenKey),
                HttpStatus.OK
        );
    }

    @GetMapping("by-plan")
    @ApiOperation(httpMethod = "GET", value = "Consultar avance por plan de trabajo", notes = "")
    public ResponseEntity<AvanceObraResponse> getAvanceByPlan(
            @RequestParam(value = "planKey") String planKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAvanceByPlan(planKey),
                HttpStatus.OK
        );
    }

    @GetMapping("by-plan-semanal")
    @ApiOperation(httpMethod = "GET", value = "Consultar avance por plan semanal", notes = "")
    public ResponseEntity<AvanceObraResponse> getAvanceByPlanSemanal(
            @RequestParam(value = "planSemanalKey") String planSemanalKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAvanceByPlanSemanal(planSemanalKey),
                HttpStatus.OK
        );
    }
}