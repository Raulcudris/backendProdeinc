package com.system.modules.workcontrol.api;
import java.util.Map;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioDto;
import com.system.crosscutting.domain.model.EntyOrsordmaordenservicioResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntyOrdenServicioService;
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

@RestController
@RequestMapping(
        value = "/api/workcontrol",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyOrdenServicioController {

    private final EntyOrdenServicioService ordenServicioService;

    public EntyOrdenServicioController(
            final EntyOrdenServicioService ordenServicioService
    ) {
        this.ordenServicioService = ordenServicioService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> statusRoot() {
        return new ResponseEntity<>(
                ordenServicioService.status(),
                HttpStatus.OK
        );
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        return new ResponseEntity<>(
                ordenServicioService.status(),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> create(
            @RequestBody final EntyOrsordmaordenservicioDto request
    ) {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.createResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> getByKey(
            @RequestParam("ordenKey") final String ordenKey
    ) {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.getByKeyResponse(ordenKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-estado")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> getByEstado(
            @RequestParam("estado") final String estado
    ) {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.getByEstadoResponse(estado);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> getAll()
            throws EBusinessException {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.getResponse(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyOrsordmaordenservicioDto request
    ) throws EBusinessException {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.updateResponse(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/inactivar")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> inactivar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.inactivarResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyOrsordmaordenservicioResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsordmaordenservicioResponse response =
                ordenServicioService.deleteResponse(id);

        return ResponseEntity.ok(response);
    }
}