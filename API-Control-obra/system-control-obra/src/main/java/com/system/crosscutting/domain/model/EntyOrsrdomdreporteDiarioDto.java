package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla ORSRDOMDREPORTEDIARIO.
 */
@Getter
@Setter
public class EntyOrsrdomdreporteDiarioDto {

    private Integer orsPrimarykeyRedi;

    private String orsIdentifkeyRedi;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPltr;

    private String orsIdentifkeyPspl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechareporteRedi;

    private String orsActividadRedi;

    private BigDecimal orsCantidadprogRedi;

    private BigDecimal orsCantidadejecRedi;

    private String orsUnidadmedidaRedi;

    private String orsResponsableRedi;

    private String orsObservacionRedi;

    private String orsEstadoregRedi;
}