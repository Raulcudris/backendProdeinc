package com.system.modules.workcontrol.api;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosDto;
import com.system.crosscutting.domain.model.EntyOrsordmdsitiospuntosResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.workcontrol.usecase.EntySitiosPuntosService;
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
        value = "/api/workcontrol/sitios-puntos",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntySitiosPuntosController {

    private final EntySitiosPuntosService sitiosPuntosService;

    public EntySitiosPuntosController(
            final EntySitiosPuntosService sitiosPuntosService
    ) {
        this.sitiosPuntosService = sitiosPuntosService;
    }

    @PostMapping(
            value = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> create(
            @RequestBody final EntyOrsordmdsitiospuntosDto request
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.createResponse(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-orden")
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> getByOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.getByOrdenResponse(ordenKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> getAll()
            throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.getAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> getAllPages(
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize,
            @RequestParam(value = "parameter", defaultValue = "TEXT")
            final String parameter,
            @RequestParam(value = "filter", defaultValue = "")
            final String filter
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.getAll(
                        currentPage,
                        pageSize,
                        parameter,
                        filter
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> get(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.getResponse(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping(
            value = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> update(
            @PathVariable("id") final Integer id,
            @RequestBody final EntyOrsordmdsitiospuntosDto request
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.updateResponse(id, request);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cerrar")
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> cerrar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.cerrarResponse(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> cancelar(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.cancelarResponse(id);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntyOrsordmdsitiospuntosResponse> delete(
            @PathVariable("id") final Integer id
    ) throws EBusinessException {
        EntyOrsordmdsitiospuntosResponse response =
                sitiosPuntosService.deleteResponse(id);

        return ResponseEntity.ok(response);
    }
}