package com.system.modules.proveedores.api;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProveedoresHealthController {

    @GetMapping(
            value = "/api/proveedores/health",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> health() {
        return Map.of(
                "microservicio", "system-proveedores",
                "modulo", "proveedores",
                "estado", "OK"
        );
    }
}