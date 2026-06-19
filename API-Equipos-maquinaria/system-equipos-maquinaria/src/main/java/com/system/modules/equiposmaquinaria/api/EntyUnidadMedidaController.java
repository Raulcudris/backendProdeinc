package com.system.modules.equiposmaquinaria.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoDto;
import com.system.crosscutting.domain.model.EntyPrvinvmdunidamedequipoResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.ResponsePayloadUtil;
import com.system.modules.equiposmaquinaria.usecase.EntyUnidadMedidaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(
        value = "/api/equipos-maquinaria/unidades",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyUnidadMedidaController {

    @Autowired
    private EntyUnidadMedidaService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return new ResponseEntity<>(
                "msvc-equipos-maquinaria unidades OK",
                HttpStatus.OK
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyPrvinvmdunidamedequipoResponse> getAll(
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

    @GetMapping("/get/{unidadKey}")
    public ResponseEntity<EntyPrvinvmdunidamedequipoDto> get(
            @PathVariable String unidadKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByKey(unidadKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyPrvinvmdunidamedequipoDto> findByKey(
            @RequestParam String unidadKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByKey(unidadKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<?> findByEstado(
            @RequestParam String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByEstado(estado),
                HttpStatus.OK
        );
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyPrvinvmdunidamedequipoDto> create(
            @RequestBody EntyPrvinvmdunidamedequipoResponse request
    ) throws EBusinessException, MicroEventException {

        EntyPrvinvmdunidamedequipoDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar una unidad de medida en rspData."
        );

        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PostMapping(
            value = "/create-list",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<EntyPrvinvmdunidamedequipoDto>> createList(
            @RequestBody EntyPrvinvmdunidamedequipoResponse request
    ) throws EBusinessException, MicroEventException {

        List<EntyPrvinvmdunidamedequipoDto> dtoList = ResponsePayloadUtil.getData(
                request.getRspData(),
                "Debe enviar unidades de medida en rspData."
        );

        return new ResponseEntity<>(
                service.saveBefore(dtoList),
                HttpStatus.CREATED
        );
    }

    @PutMapping(
            value = "/update/{unidadKey}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyPrvinvmdunidamedequipoDto> update(
            @PathVariable String unidadKey,
            @RequestBody EntyPrvinvmdunidamedequipoResponse request
    ) throws EBusinessException, MicroEventException {

        EntyPrvinvmdunidamedequipoDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar una unidad de medida en rspData."
        );

        return new ResponseEntity<>(
                service.updateByKey(unidadKey, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("/changestatus/{unidadKey}")
    public ResponseEntity<String> changestatus(
            @PathVariable String unidadKey,
            @RequestParam(value = "estado", required = false, defaultValue = "2") String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changestatusByKey(unidadKey, estado),
                HttpStatus.OK
        );
    }

    @PostMapping(
            value = "/changestatus",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> changestatusBody(
            @RequestBody List<Map<String, String>> request
    ) throws EBusinessException, MicroEventException {

        if (request == null || request.isEmpty()) {
            throw new EBusinessException("Debe enviar al menos un registro para cambiar estado.");
        }

        for (Map<String, String> item : request) {
            String unidadKey = item.get("recPKey");
            String estado = item.get("recEstreg");

            if (unidadKey == null || unidadKey.trim().isEmpty()) {
                throw new EBusinessException("recPKey es obligatorio.");
            }

            if (estado == null || estado.trim().isEmpty()) {
                throw new EBusinessException("recEstreg es obligatorio.");
            }

            service.changestatusByKey(unidadKey, estado);
        }

        return new ResponseEntity<>(
                "Estado actualizado correctamente.",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{unidadKey}")
    public ResponseEntity<String> delete(
            @PathVariable String unidadKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteByKey(unidadKey),
                HttpStatus.OK
        );
    }

    @PostMapping(
            value = "/delete",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> deleteBody(
            @RequestBody List<Map<String, String>> request
    ) throws EBusinessException, MicroEventException {

        if (request == null || request.isEmpty()) {
            throw new EBusinessException("Debe enviar al menos un registro para eliminar.");
        }

        for (Map<String, String> item : request) {
            String unidadKey = item.get("recPKey");

            if (unidadKey == null || unidadKey.trim().isEmpty()) {
                throw new EBusinessException("recPKey es obligatorio.");
            }

            service.deleteByKey(unidadKey);
        }

        return new ResponseEntity<>(
                "Registro(s) eliminado(s) correctamente.",
                HttpStatus.OK
        );
    }
}