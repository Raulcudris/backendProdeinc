package com.system.modules.evidencia.api;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EvidenciaHealthController {

    @GetMapping(
            value = "/api/evidencias/health",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> health() {
        return Map.of(
                "microservicio", "system-evidencia",
                "modulo", "evidencias",
                "estado", "OK"
        );
    }
}