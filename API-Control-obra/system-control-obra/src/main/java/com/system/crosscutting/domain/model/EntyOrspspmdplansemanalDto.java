package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla ORSPSPMDPLANSEMANAL.
 */
@Getter
@Setter
public class EntyOrspspmdplansemanalDto {

    private Integer orsPrimarykeyPspl;

    private String orsIdentifkeyPspl;

    private String orsIdentifkeyPltr;

    private Integer orsSemanaPspl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechainicioPspl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechafinPspl;

    private BigDecimal orsCantidadprogPspl;

    private String orsObservacionPspl;

    private String orsEstadoregPspl;
}