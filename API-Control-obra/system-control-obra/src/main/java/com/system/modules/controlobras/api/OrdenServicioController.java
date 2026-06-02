package com.system.modules.controlobras.api;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.controlobras.usecase.OrdenServicioService;
import lombok.RequiredArgsConstructor;
import java.util.List;

/**
 * Controlador REST para consultar órdenes de servicio.
 */
@RestController
@RequiredArgsConstructor
public class OrdenServicioController {

    private final OrdenServicioService service;

    /**
     * Consulta todas las órdenes de servicio.
     *
     * @return listado de órdenes de servicio.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/control-obras/ordenes/pages")
    public ResponseEntity<List<EntyOrsordmaordenservicioDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }
}