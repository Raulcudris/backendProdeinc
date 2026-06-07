package com.system.modules.controlobras.usecase;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ControlObrasService {

    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();

        response.put("microservicio", "system-control-obra");
        response.put("modulo", "control-obras");
        response.put("estado", "OK");
        response.put("flujo", "orden-servicio -> sitios -> plan-trabajo -> plan-semanal -> reporte-diario -> novedades");

        return response;
    }
}