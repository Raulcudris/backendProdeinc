package com.system.modules.controlobras.api;

import java.util.List;

import com.system.crosscutting.domain.model.ChangeStatusRequestDto;
import com.system.crosscutting.domain.model.DeleteRequestDto;
import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleDto;
import com.system.crosscutting.domain.model.EntyOrsordmdactamodificaciondetalleResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.ResponsePayloadUtil;
import com.system.modules.controlobras.usecase.EntyActaModificacionDetalleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/control-obras/actas-modificacion-detalles")
public class ActaModificacionDetalleController {

    @Autowired
    private EntyActaModificacionDetalleService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Detalle acta modificación service OK");
    }

    @PostMapping("/create")
    public ResponseEntity<EntyOrsordmdactamodificaciondetalleDto> create(
            @RequestBody final EntyOrsordmdactamodificaciondetalleResponse request
    ) throws EBusinessException {

        EntyOrsordmdactamodificaciondetalleDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un detalle de acta de modificación en rspData."
        );

        return ResponseEntity.ok(service.saveBefore(dto));
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyOrsordmdactamodificaciondetalleDto>> createList(
            @RequestBody final EntyOrsordmdactamodificaciondetalleResponse request
    ) throws EBusinessException {

        List<EntyOrsordmdactamodificaciondetalleDto> dtoList = ResponsePayloadUtil.getData(
                request.getRspData(),
                "Debe enviar detalles de acta de modificación en rspData."
        );

        return ResponseEntity.ok(service.saveBefore(dtoList));
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsordmdactamodificaciondetalleResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsordmdactamodificaciondetalleResponse> getAllPages(
            @RequestParam(defaultValue = "1") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(defaultValue = "TEXT") final String parameter,
            @RequestParam(defaultValue = "") final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(service.getAll(currentPage, pageSize, parameter, filter));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyOrsordmdactamodificaciondetalleDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyOrsordmdactamodificaciondetalleDto> findByKey(
            @RequestParam final String detalleActaModificacionKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByKey(detalleActaModificacionKey));
    }

    @GetMapping("/by-acta")
    public ResponseEntity<List<EntyOrsordmdactamodificaciondetalleDto>> findByActa(
            @RequestParam final String actaModificacionKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByActa(actaModificacionKey));
    }

    @GetMapping("/by-orden")
    public ResponseEntity<List<EntyOrsordmdactamodificaciondetalleDto>> findByOrden(
            @RequestParam final String ordenKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByOrden(ordenKey));
    }

    @GetMapping("/by-resumen-equipo")
    public ResponseEntity<List<EntyOrsordmdactamodificaciondetalleDto>> findByResumenEquipo(
            @RequestParam final String resumenEquipoKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByResumenEquipo(resumenEquipoKey));
    }

    @GetMapping("/by-tipo-equipo")
    public ResponseEntity<List<EntyOrsordmdactamodificaciondetalleDto>> findByTipoEquipo(
            @RequestParam final String tipoEquipoKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByTipoEquipo(tipoEquipoKey));
    }

    @GetMapping("/by-estado")
    public ResponseEntity<List<EntyOrsordmdactamodificaciondetalleDto>> findByEstado(
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyOrsordmdactamodificaciondetalleDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyOrsordmdactamodificaciondetalleResponse request
    ) throws EBusinessException {

        EntyOrsordmdactamodificaciondetalleDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un detalle de acta de modificación en rspData."
        );

        return ResponseEntity.ok(service.updateBefore(id, dto));
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyOrsordmdactamodificaciondetalleDto> changestatus(
            @PathVariable final Integer id,
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.changestatus(id, estado));
    }

    @PostMapping("/changestatus")
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

        return ResponseEntity.ok("Estado actualizado correctamente.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable final Integer id
    ) throws EBusinessException {
        service.deleteBefore(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/delete")
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

        return ResponseEntity.ok("Registro(s) eliminado(s) correctamente.");
    }
}