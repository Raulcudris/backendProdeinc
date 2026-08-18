package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamainformesemanalDto {

    private Integer orsPrimarykeyInse;

    private String orsIdentifkeyInse;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPsem;

    private Integer orsSemanaInse;

    private LocalDate orsFechainicioInse;

    private LocalDate orsFechafinInse;

    private BigDecimal orsValorordenInse;

    private BigDecimal orsProgramadosemanaInse;

    private BigDecimal orsEjecutadosemanaInse;

    private BigDecimal orsProgramadoacumuladoInse;

    private BigDecimal orsEjecutadoacumuladoInse;

    private BigDecimal orsCantidadprogramadasemInse;

    private BigDecimal orsCantidadejecutadasemInse;

    private BigDecimal orsCantidadprogramadaacuInse;

    private BigDecimal orsCantidadejecutadaacuInse;

    private BigDecimal orsPoravancefisicoInse;

    private BigDecimal orsPoravancefinancieroInse;

    private BigDecimal orsAtrasoadelantoInse;

    private String orsEstadoavanceInse;

    private String orsObservacionInse;

    private LocalDateTime orsFechasistemaInse;

    private String orsTiporegistInse;

    private String orsEstadoregInse;
}