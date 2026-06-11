package com.system.modules.proveedores.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaDto;
import com.system.crosscutting.domain.model.EntyPrvmaeproveedoresmaResponse;
import com.system.crosscutting.exceptions.MicroEventException;
import com.system.crosscutting.exceptions.Main.EBusinessException;
import com.system.modules.proveedores.usecase.ProveedorService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(
        value = "/api/proveedores/proveedores",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Proveedor service OK");
    }

    @GetMapping("/all")
    public ResponseEntity<EntyPrvmaeproveedoresmaResponse> getAll()
            throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/pages")
    public ResponseEntity<EntyPrvmaeproveedoresmaResponse> getAllPages(
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
    public ResponseEntity<EntyPrvmaeproveedoresmaDto> get(
            @PathVariable Integer id
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.get(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-key")
    public ResponseEntity<EntyPrvmaeproveedoresmaDto> findByKey(
            @RequestParam String proveedorKey
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByKey(proveedorKey),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-nit")
    public ResponseEntity<EntyPrvmaeproveedoresmaDto> findByNit(
            @RequestParam String numeroNit
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByNit(numeroNit),
                HttpStatus.OK
        );
    }

    @GetMapping("/by-estado")
    public ResponseEntity<List<EntyPrvmaeproveedoresmaDto>> findByEstado(
            @RequestParam String estado
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.findByEstado(estado),
                HttpStatus.OK
        );
    }

    @PostMapping("/create")
    public ResponseEntity<EntyPrvmaeproveedoresmaDto> create(
            @RequestBody EntyPrvmaeproveedoresmaDto dto
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dto),
                HttpStatus.CREATED
        );
    }

    @PostMapping("/create-list")
    public ResponseEntity<List<EntyPrvmaeproveedoresmaDto>> createList(
            @RequestBody List<EntyPrvmaeproveedoresmaDto> dtoList
    ) throws EBusinessException, MicroEventException {
        return new ResponseEntity<>(
                service.saveBefore(dtoList),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntyPrvmaeproveedoresmaDto> update(
            @PathVariable Integer id,
            @RequestBody EntyPrvmaeproveedoresmaDto dto
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