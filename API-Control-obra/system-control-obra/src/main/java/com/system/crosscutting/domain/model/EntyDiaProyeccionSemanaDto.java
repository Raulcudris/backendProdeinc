package com.system.crosscutting.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyDiaProyeccionSemanaDto {

    private Integer orsPrimarykeyDpse;

    private String orsIdentifkeyDpse;

    private String orsIdentifkeyPsem;

    private String orsIdentifkeyOrde;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsFechaDpse;

    private String orsNombrediaDpse;

    private Boolean orsEshabilDpse;

    private Boolean orsEstrabajadoDpse;

    private String orsObservacionDpse;

    private String orsTiporegistDpse;

    private String orsEstadoregDpse;
}