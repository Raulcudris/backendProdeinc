package com.system.crosscutting.domain.model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvanceOrdenDto {

    private String ordenKey;
    private BigDecimal cantidadPlanificada;
    private BigDecimal cantidadEjecutada;
    private BigDecimal cantidadSaldo;
    private BigDecimal porcentajeAvance;
}