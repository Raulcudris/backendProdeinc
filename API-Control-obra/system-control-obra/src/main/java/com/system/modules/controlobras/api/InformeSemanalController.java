package com.system.modules.controlobras.api;

import java.util.List;

import com.system.crosscutting.domain.model.ChangeStatusRequestDto;
import com.system.crosscutting.domain.model.DeleteRequestDto;
import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalDto;
import com.system.crosscutting.domain.model.EntyOrsplamainformesemanalResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.crosscutting.utils.ResponsePayloadUtil;
import com.system.modules.controlobras.usecase.EntyInformeSemanalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/control-obras/informes-semanales")
public class InformeSemanalController {

    @Autowired
    private EntyInformeSemanalService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Informe semanal service OK");
    }

    @PostMapping("/create")
    public ResponseEntity<EntyOrsplamainformesemanalDto> create(
            @RequestBody final EntyOrsplamainformesemanalResponse request
    ) throws EBusinessException {

        EntyOrsplamainformesemanalDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un informe semanal en rspData."
        );

        return ResponseEntity.ok(service.saveBefore(dto));
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyOrsplamainformesemanalDto>> createList(
            @RequestBody final EntyOrsplamainformesemanalResponse request
    ) throws EBusinessException {

        List<EntyOrsplamainformesemanalDto> dtoList = ResponsePayloadUtil.getData(
                request.getRspData(),
                "Debe enviar informes semanales en rspData."
        );

        return ResponseEntity.ok(service.saveBefore(dtoList));
    }

    @GetMapping("/all")
    public ResponseEntity<EntyOrsplamainformesemanalResponse> getAll()
            throws EBusinessException {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyOrsplamainformesemanalResponse> getAllPages(
            @RequestParam(defaultValue = "1") final int currentPage,
            @RequestParam(defaultValue = "10") final int pageSize,
            @RequestParam(defaultValue = "TEXT") final String parameter,
            @RequestParam(defaultValue = "") final String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(service.getAll(currentPage, pageSize, parameter, filter));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EntyOrsplamainformesemanalDto> get(
            @PathVariable final Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(service.get(id));
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyOrsplamainformesemanalDto> findByKey(
            @RequestParam final String informeSemanalKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByKey(informeSemanalKey));
    }

    @GetMapping("/by-orden")
    public ResponseEntity<List<EntyOrsplamainformesemanalDto>> findByOrden(
            @RequestParam final String ordenKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByOrden(ordenKey));
    }

    @GetMapping("/by-proyeccion-semana")
    public ResponseEntity<List<EntyOrsplamainformesemanalDto>> findByProyeccionSemana(
            @RequestParam final String proyeccionSemanaKey
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByProyeccionSemana(proyeccionSemanaKey));
    }

    @GetMapping("/by-semana")
    public ResponseEntity<List<EntyOrsplamainformesemanalDto>> findBySemana(
            @RequestParam final Integer semana
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findBySemana(semana));
    }

    @GetMapping("/by-estado")
    public ResponseEntity<List<EntyOrsplamainformesemanalDto>> findByEstado(
            @RequestParam final String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyOrsplamainformesemanalDto> update(
            @PathVariable final Integer id,
            @RequestBody final EntyOrsplamainformesemanalResponse request
    ) throws EBusinessException {

        EntyOrsplamainformesemanalDto dto = ResponsePayloadUtil.getFirstData(
                request.getRspData(),
                "Debe enviar un informe semanal en rspData."
        );

        return ResponseEntity.ok(service.updateBefore(id, dto));
    }

    @PatchMapping("/changestatus/{id}")
    public ResponseEntity<EntyOrsplamainformesemanalDto> changestatus(
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