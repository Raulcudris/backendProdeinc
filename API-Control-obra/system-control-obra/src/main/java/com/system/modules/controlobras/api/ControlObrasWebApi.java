package com.system.modules.controlobras.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.system.modules.controlobras.usecase.ControlObrasService;

@RestController
@RequestMapping(
        value = "/api/control-obras",
        produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ControlObrasWebApi {

    @Autowired
    private ControlObrasService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> status() {
        return new ResponseEntity<>(
                service.status(),
                HttpStatus.OK
        );
    }
}