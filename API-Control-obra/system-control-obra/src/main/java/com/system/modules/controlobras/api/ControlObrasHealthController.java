package com.system.modules.controlobras.api;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControlObrasHealthController {

    @GetMapping(
            value = "/api/control-obras/health",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Map<String, Object> health() {
        return Map.of(
                "microservicio", "system-control-obra",
                "modulo", "control-obras",
                "estado", "OK"
        );
    }
}