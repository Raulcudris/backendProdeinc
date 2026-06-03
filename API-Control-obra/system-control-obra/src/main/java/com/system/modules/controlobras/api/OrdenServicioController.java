package com.system.modules.controlobras.api;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * Crea una nueva orden de servicio.
     *
     * @param dto información de la orden de servicio.
     * @return orden de servicio creada.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @PostMapping("/api/control-obras/ordenes/create")
    public ResponseEntity<EntyOrsordmaordenservicioDto> create(@RequestBody final EntyOrsordmaordenservicioDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}