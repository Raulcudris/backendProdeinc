package com.system.crosscutting.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvanceObraDto {

    private String orsIdentifkeyOrde;
    private String orsIdentifkeyPltr;
    private String orsIdentifkeyPlse;
    private String orsIdentifkeyPsem;

    private String descripcionActividad;
    private String equipoInventario;

    private BigDecimal cantidadPlaneadaTotal;
    private BigDecimal cantidadEjecutadaTotal;
    private BigDecimal saldoPendienteTotal;
    private BigDecimal porcentajeAvanceTotal;

    private BigDecimal cantidadPlaneadaSemana;
    private BigDecimal cantidadEjecutadaSemana;
    private BigDecimal saldoPendienteSemana;
    private BigDecimal porcentajeAvanceSemana;

    private BigDecimal valorPlaneadoSemana;
    private BigDecimal valorEjecutadoSemana;
    private BigDecimal saldoValorSemana;

    private String estadoAvance;
}