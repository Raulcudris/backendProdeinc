package com.system.modules.proveedores.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Health básico del microservicio de proveedores.
 */
@RestController
public class ProveedoresHealth {

    @GetMapping("/api/proveedores/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("msvc-proveedores OK");
    }
}