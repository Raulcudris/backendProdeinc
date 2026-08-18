package com.system.modules.workcontrol.api;
import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalApiResponse;
import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalPersistenciaResponse;
import com.system.crosscutting.domain.model.EntyOrsProyeccionSemanalRequestDto;
import com.system.modules.workcontrol.usecase.EntyOrsProyeccionSemanalService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
        value = "/api/workcontrol/proyeccion-semanal",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class EntyOrsProyeccionSemanalController {

    private final EntyOrsProyeccionSemanalService proyeccionSemanalService;

    public EntyOrsProyeccionSemanalController(
            final EntyOrsProyeccionSemanalService proyeccionSemanalService
    ) {
        this.proyeccionSemanalService = proyeccionSemanalService;
    }

    @PostMapping(
            value = "/calcular",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsProyeccionSemanalApiResponse> calcular(
            @RequestBody final EntyOrsProyeccionSemanalRequestDto request
    ) {
        EntyOrsProyeccionSemanalApiResponse response =
                proyeccionSemanalService.calcular(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping(
            value = "/guardar",
            consumes = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EntyOrsProyeccionSemanalPersistenciaResponse> guardar(
            @RequestBody final EntyOrsProyeccionSemanalRequestDto request
    ) {
        EntyOrsProyeccionSemanalPersistenciaResponse response =
                proyeccionSemanalService.guardar(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-orden")
    public ResponseEntity<EntyOrsProyeccionSemanalPersistenciaResponse>
    consultarPorOrden(
            @RequestParam("ordenKey") final String ordenKey
    ) {
        EntyOrsProyeccionSemanalPersistenciaResponse response =
                proyeccionSemanalService.consultarPorOrden(ordenKey);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-orden/pages")
    public ResponseEntity<EntyOrsProyeccionSemanalPersistenciaResponse>
    consultarPorOrdenPaginado(
            @RequestParam("ordenKey") final String ordenKey,
            @RequestParam(value = "currentPage", defaultValue = "1")
            final int currentPage,
            @RequestParam(value = "pageSize", defaultValue = "10")
            final int pageSize
    ) {
        EntyOrsProyeccionSemanalPersistenciaResponse response =
                proyeccionSemanalService.consultarPorOrdenPaginado(
                        ordenKey,
                        currentPage,
                        pageSize
                );

        return ResponseEntity.ok(response);
    }
}