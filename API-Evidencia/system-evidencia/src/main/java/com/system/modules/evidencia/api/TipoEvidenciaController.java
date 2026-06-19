package com.system.modules.evidencia.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.ChangeStatusRequestDto;
import com.system.crosscutting.domain.model.DeleteRequestDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvitipmatipoevidenciaResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.ResponsePayloadUtil;
import com.system.modules.evidencia.usecase.EntyTipoEvidenciaService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(
        value = "/api/evidencias/tipos",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class TipoEvidenciaController {

    @Autowired
    private EntyTipoEvidenciaService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Tipo evidencia service OK");
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> create(
            @RequestBody final EntyEvitipmatipoevidenciaResponse request
    ) throws EBusinessException, MicroEventException {

        EntyEvitipmatipoevidenciaDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un tipo de evidencia en rspData."
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
    public ResponseEntity<List<EntyEvitipmatipoevidenciaDto>> createList(
            @RequestBody final EntyEvitipmatipoevidenciaResponse request
    ) throws EBusinessException, MicroEventException {

        List<EntyEvitipmatipoevidenciaDto> dtoList = ResponsePayloadUtil.getData(
                request.getRspData(),
                "Debe enviar tipos de evidencia en rspData."
        );

        return new ResponseEntity<>(
                service.saveBefore(dtoList),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> all()
            throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> getAll(
            @RequestParam(value = "currentPage", required = false, defaultValue = "1") final int currentPage,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") final int pageSize,
            @RequestParam(value = "parameter", required = false, defaultValue = "TEXT") final String parameter,
            @RequestParam(value = "filter", required = false, defaultValue = "") final String filter
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(currentPage, pageSize, parameter, filter),
                HttpStatus.OK
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> findByKey(
            @RequestParam final String tipoEvidenciaKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByKey(tipoEvidenciaKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyEvitipmatipoevidenciaResponse> findByEstado(
            @RequestParam final String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByEstado(estado),
                HttpStatus.OK
        );
    }

    @PutMapping(
            value = "/update/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyEvitipmatipoevidenciaResponse request
    ) throws EBusinessException, MicroEventException {

        EntyEvitipmatipoevidenciaDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un tipo de evidencia en rspData."
        );

        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyEvitipmatipoevidenciaDto> changestatus(
            @PathVariable final Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") final String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changestatus(id, estado),
                HttpStatus.OK
        );
    }

    @PostMapping(
            value = "/changestatus",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> changestatusBody(
            @RequestBody final List<ChangeStatusRequestDto> request
    ) throws EBusinessException, MicroEventException {

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
    ) throws EBusinessException, MicroEventException {
        service.deleteBefore(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(
            value = "/delete",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<String> deleteBody(
            @RequestBody final List<DeleteRequestDto> request
    ) throws EBusinessException, MicroEventException {

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