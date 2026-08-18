package com.system.modules.workcontrol.api;

import com.system.crosscutting.domain.model.AvanceOrdenDetalleResponse;
import com.system.crosscutting.domain.model.AvanceOrdenResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.ReporteAvanceObraService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/workcontrol/reportes",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ReporteAvanceObraController {

    private final ReporteAvanceObraService reporteAvanceObraService;

    public ReporteAvanceObraController(
            final ReporteAvanceObraService reporteAvanceObraService
    ) {
        this.reporteAvanceObraService = reporteAvanceObraService;
    }

    @GetMapping("/avance-orden")
    public ResponseEntity<AvanceOrdenResponse> getAvancePorOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) throws EBusinessException {

        return ResponseEntity.ok(
                reporteAvanceObraService.getAvancePorOrden(ordenKey)
        );
    }

    @GetMapping("/avance-detalle-orden")
    public ResponseEntity<AvanceOrdenDetalleResponse> getDetalleAvancePorOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) throws EBusinessException {

        return ResponseEntity.ok(
                reporteAvanceObraService.getDetalleAvancePorOrden(ordenKey)
        );
    }
}