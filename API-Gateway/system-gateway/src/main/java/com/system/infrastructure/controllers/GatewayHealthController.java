package com.system.infrastructure.controllers;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayHealthController {

    private static final String SERVICE_NAME = "msvc-gateway";
    private static final String HEALTH_STATUS = "UP";
    private static final String HEALTH_MESSAGE = "Gateway Loochon funcionando correctamente";

    @GetMapping(value = "/api/health", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();

        response.put("status", HEALTH_STATUS);
        response.put("service", SERVICE_NAME);
        response.put("message", HEALTH_MESSAGE);
        response.put("timestamp", LocalDateTime.now().toString());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}