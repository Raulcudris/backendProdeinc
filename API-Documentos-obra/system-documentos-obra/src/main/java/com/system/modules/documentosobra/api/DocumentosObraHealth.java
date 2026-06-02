package com.system.modules.documentosobra.api;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador de prueba para validar que el microservicio msvc-documentos-obra
 * se encuentra activo y responde correctamente.
 */
@RestController
public class DocumentosObraHealth {

    /**
     * Endpoint simple para validar comunicación directa y comunicación a través del Gateway.
     *
     * @return información básica del estado del microservicio.
     */
    @GetMapping("/api/documentos-obra/health/ping")
    public ResponseEntity<Map<String, Object>> ping() {
        return ResponseEntity.ok(Map.of(
                "service", "msvc-documentos-obra",
                "status", "UP",
                "timestamp", LocalDateTime.now().toString()
        ));
    }
}
