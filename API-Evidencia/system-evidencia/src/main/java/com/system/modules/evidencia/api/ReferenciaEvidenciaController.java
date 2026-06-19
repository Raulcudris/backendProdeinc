package com.system.modules.evidencia.api;

import java.util.List;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.ChangeStatusRequestDto;
import com.system.crosscutting.domain.model.DeleteRequestDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaDto;
import com.system.crosscutting.domain.model.EntyEvirefmdreferenciaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.ResponsePayloadUtil;
import com.system.modules.evidencia.usecase.EntyReferenciaEvidenciaService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(
        value = "/api/evidencias/referencias",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ReferenciaEvidenciaController {

    private final EntyReferenciaEvidenciaService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Referencia evidencia service OK");
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvirefmdreferenciaDto> create(
            @RequestBody final EntyEvirefmdreferenciaResponse request
    ) throws EBusinessException {

        EntyEvirefmdreferenciaDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar una referencia de evidencia en rspData."
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
    public ResponseEntity<List<EntyEvirefmdreferenciaDto>> createList(
            @RequestBody final EntyEvirefmdreferenciaResponse request
    ) throws EBusinessException {

        List<EntyEvirefmdreferenciaDto> dtoList = ResponsePayloadUtil.getData(
                request.getRspData(),
                "Debe enviar referencias de evidencia en rspData."
        );

        return new ResponseEntity<>(
                service.saveBefore(dtoList),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> getPages(
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") final int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize,
            @RequestParam(value = "parameter", required = false, defaultValue = "TEXT") final String parameter,
            @RequestParam(value = "filter", required = false, defaultValue = "") final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.getAll(currentPage, pageSize, parameter, filter)
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyEvirefmdreferenciaDto> byKey(
            @RequestParam final String referenciaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByKey(referenciaKey));
    }

    @GetMapping("/by-evidencia")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byEvidencia(
            @RequestParam final String evidenciaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEvidencia(evidenciaKey));
    }

    @GetMapping("/by-registro")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byRegistro(
            @RequestParam final String registroKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByRegistro(registroKey));
    }

    @GetMapping("/by-tipo-registro")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byTipoRegistro(
            @RequestParam final String tipoRegistro
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByTipoRegistro(tipoRegistro));
    }

    @GetMapping("/by-tipo-registro-and-registro")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byTipoRegistroAndRegistro(
            @RequestParam final String tipoRegistro,
            @RequestParam final String registroKey
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.findByTipoRegistroAndRegistro(tipoRegistro, registroKey)
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyEvirefmdreferenciaResponse> byEstado(
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvirefmdreferenciaDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyEvirefmdreferenciaResponse request
    ) throws EBusinessException {

        EntyEvirefmdreferenciaDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar una referencia de evidencia en rspData."
        );

        return ResponseEntity.ok(service.updateBefore(id, dto));
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyEvirefmdreferenciaDto> changestatus(
            @PathVariable final Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.changestatus(id, estado));
    }

    @PostMapping(
            value = "/changestatus",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
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

        return new ResponseEntity<>(
                "Estado actualizado correctamente.",
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Integer id
    ) throws EBusinessException {
        service.deleteBefore(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/delete",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
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

        return new ResponseEntity<>(
                "Registro(s) eliminado(s) correctamente.",
                HttpStatus.OK
        );
    }
}