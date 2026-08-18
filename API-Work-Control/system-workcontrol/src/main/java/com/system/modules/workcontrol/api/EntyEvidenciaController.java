package com.system.modules.workcontrol.api;

import java.io.IOException;

import com.system.crosscutting.domain.model.EntyEvievimaevidenciaDto;
import com.system.crosscutting.domain.model.EntyEvievimaevidenciaResponse;
import com.system.crosscutting.domain.model.EvidenciaUploadResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyEvidenciaService;
import com.system.modules.workcontrol.usecase.EvidenciaStorageService;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(
        value = "/api/workcontrol/evidencias",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyEvidenciaController {

    private final EntyEvidenciaService evidenciaService;
    private final EvidenciaStorageService evidenciaStorageService;

    public EntyEvidenciaController(
            final EntyEvidenciaService evidenciaService,
            final EvidenciaStorageService evidenciaStorageService
    ) {
        this.evidenciaService = evidenciaService;
        this.evidenciaStorageService = evidenciaStorageService;
    }

    @PostMapping(
            value = "/upload",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<EvidenciaUploadResponse> upload(
            @RequestPart("file") final MultipartFile file
    ) throws IOException {

        return ResponseEntity.ok(
                evidenciaStorageService.upload(file)
        );
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvievimaevidenciaResponse> create(
            @RequestBody final EntyEvievimaevidenciaDto request
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.createResponse(request)
        );
    }

    @PostMapping(
            value = "/create-reference",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvievimaevidenciaResponse> createAndReference(
            @RequestBody final EntyEvievimaevidenciaDto request,
            @RequestParam("tipoRegistro") final String tipoRegistro,
            @RequestParam("identificadorRegistro")
            final String identificadorRegistro,
            @RequestParam(value = "observacionReferencia", required = false)
            final String observacionReferencia
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.createAndReferenceResponse(
                        request,
                        tipoRegistro,
                        identificadorRegistro,
                        observacionReferencia
                )
        );
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyEvievimaevidenciaResponse> getByKey(
            @RequestParam("evidenciaKey") final String evidenciaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.getByKeyResponse(evidenciaKey)
        );
    }

    @GetMapping("/by-tipo")
    public ResponseEntity<EntyEvievimaevidenciaResponse> getByTipo(
            @RequestParam("tipoEvidenciaKey") final String tipoEvidenciaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.getByTipoResponse(tipoEvidenciaKey)
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyEvievimaevidenciaResponse> getByEstado(
            @RequestParam("estado") final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.getByEstadoResponse(estado)
        );
    }

    @GetMapping("/all")
    public ResponseEntity<EntyEvievimaevidenciaResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.getAll()
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyEvievimaevidenciaResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyEvievimaevidenciaResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.getResponse(id)
        );
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyEvievimaevidenciaResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyEvievimaevidenciaDto request
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.updateResponse(id, request)
        );
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<EntyEvievimaevidenciaResponse> activar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.activarResponse(id)
        );
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<EntyEvievimaevidenciaResponse> inactivar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.inactivarResponse(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyEvievimaevidenciaResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                evidenciaService.deleteResponse(id)
        );
    }
}