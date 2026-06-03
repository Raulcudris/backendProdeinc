package com.system.modules.equiposmaquinaria.api;
import com.system.crosscutting.domain.model.EntyEqutipmatipoequipoDto;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.equiposmaquinaria.usecase.EntyTipoEquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EntyTipoEquipoWebApi {

    private final EntyTipoEquipoService service;

    /**
     * Consulta todos los tipos de equipos.
     *
     * @return listado de tipos de equipos.
     * @throws EBusinessException excepción de negocio controlada.
     */

    @GetMapping("/api/equipos-maquinaria/tipos/pages")
    public ResponseEntity<List<EntyEqutipmatipoequipoDto>> findAll() throws EBusinessException {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/api/equipos-maquinaria/tipos/create")
    public ResponseEntity<EntyEqutipmatipoequipoDto> create(@RequestBody final EntyEqutipmatipoequipoDto dto) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
    }
}
