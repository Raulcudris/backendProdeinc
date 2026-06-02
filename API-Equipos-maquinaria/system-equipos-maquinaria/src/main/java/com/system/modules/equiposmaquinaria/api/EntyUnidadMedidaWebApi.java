package com.system.modules.equiposmaquinaria.api;
import com.system.crosscutting.domain.model.EntyEqumedmaunidadmedidaDto;
import com.system.modules.equiposmaquinaria.usecase.EntyUnidadMedidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Controlador REST para consultar unidades de medida.
 */
@RestController
@RequiredArgsConstructor
public class EntyUnidadMedidaWebApi {

    private final EntyUnidadMedidaService service;

    /**
     * Consulta todas las unidades de medida.
     *
     * @return listado de unidades de medida.
     * @throws EBusinessException excepción de negocio controlada.
     */
    @GetMapping("/api/equipos-maquinaria/unidades/pages")
    public ResponseEntity<List<EntyEqumedmaunidadmedidaDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }
}