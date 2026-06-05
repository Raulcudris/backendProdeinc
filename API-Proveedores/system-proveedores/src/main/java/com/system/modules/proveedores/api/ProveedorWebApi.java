package com.system.modules.proveedores.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.proveedores.usecase.ProveedorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/proveedores/proveedores", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ProveedorWebApi {

    private final ProveedorService service;

    @GetMapping("pages")
    public ResponseEntity<EntyPrvmaeproveedoresmaResponse> findAll(
            @RequestParam(value = "currentpage", required = false, defaultValue = "1") int currentPage,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") int pageSize,
            @RequestParam(value = "parameter", required = false, defaultValue = "TEXT") String parameter,
            @RequestParam(value = "filter", required = false, defaultValue = "") String filter
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.findAll(currentPage, pageSize, parameter, filter)
        );
    }

    @PostMapping("create")
    public ResponseEntity<EntyPrvmaeproveedoresmaDto> create(
            @RequestBody EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                service.save(dto)
        );
    }

    @PutMapping("update/{id}")
    public ResponseEntity<EntyPrvmaeproveedoresmaDto> update(
            @PathVariable Integer id,
            @RequestBody EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.update(id, dto)
        );
    }

    @PatchMapping("changestatus/{id}")
    public ResponseEntity<String> changeStatus(
            @PathVariable Integer id,
            @RequestParam(value = "estado", required = false, defaultValue = "2") String estado
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.changeStatus(id, estado)
        );
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Integer id
    ) throws EBusinessException {
        return ResponseEntity.ok(
                service.deleteLogic(id)
        );
    }
}