package com.system.modules.evidencia.api;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Evidencia service OK");
    }

    @PostMapping("/create")
    public ResponseEntity<EntyEvievimaevidenciaDto> create(
            @RequestBody final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyEvievimaevidenciaDto>> createList(
            @RequestBody final List<EntyEvievimaevidenciaDto> dtos
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dtos),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/all")
    public ResponseEntity<EntyEvievimaevidenciaResponse> all()
            throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyEvievimaevidenciaResponse> getAll(
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
    public ResponseEntity<EntyEvievimaevidenciaDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyEvievimaevidenciaDto> findByKey(
            @RequestParam final String evidenciaKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByKey(evidenciaKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-tipo")
    public ResponseEntity<EntyEvievimaevidenciaResponse> findByTipo(
            @RequestParam final String tipoEvidenciaKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByTipo(tipoEvidenciaKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyEvievimaevidenciaResponse> findByEstado(
            @RequestParam final String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByEstado(estado),
                HttpStatus.OK
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyEvievimaevidenciaDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyEvievimaevidenciaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.updateBefore(id, dto),
                HttpStatus.OK
        );
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyEvievimaevidenciaDto> changestatus(
            @PathVariable final Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") final String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.changestatus(id, estado),
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
}