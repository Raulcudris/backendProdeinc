package com.system.modules.evidencia.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Controlador de prueba para validar que el microservicio msvc-evidencias
 * se encuentra activo y responde correctamente.
 */
@RestController
public class EvidenciasHealth {

    /**
     * Endpoint simple para validar comunicación directa y comunicación a través del Gateway.
     *
     * @return información básica del estado del microservicio.
     */
    @GetMapping("/api/evidencias/health/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        return ResponseEntity.ok(Map.of(
                "service", "msvc-evidencias",
                "status", "UP",
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}
