package com.system.modules.evidencia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.evidencia.usecase.EntyEvidenciaService;

@RestController
@RequestMapping(
        value = "/api/evidencias/evidencias",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EvidenciaController {

    @Autowired
    private EntyEvidenciaService service;

    @GetMapping("/pages")
    public ResponseEntity<EntyEvievimaevidenciaResponse> getAll(
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

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyEvievimaevidenciaDto> get(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-tipo")
    public ResponseEntity<?> findByTipo(
            @RequestParam String tipoKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByTipo(tipoKey),
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

    @PostMapping("/create")
    public ResponseEntity<EntyEvievimaevidenciaDto> create(
            @RequestBody EntyEvievimaevidenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyEvievimaevidenciaDto> update(
            @PathVariable Integer id,
            @RequestBody EntyEvievimaevidenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<String> changestatus(
            @PathVariable Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changestatus(id, estado),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.deleteBefore(id),
                HttpStatus.OK
        );
    }
}