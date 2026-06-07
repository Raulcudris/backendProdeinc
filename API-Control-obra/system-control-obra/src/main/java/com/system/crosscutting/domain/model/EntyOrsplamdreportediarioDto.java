package com.system.crosscutting.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamdreportediarioDto {

    private Integer orsPrimarykeyPdia;

    private String orsIdentifkeyPdia;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPlse;

    private String orsIdentifkeyPsem;

    private String orsObservacionPdia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechareportPdia;

    private Integer orsEjecutunidadPdia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsFechasistemaPdia;

    private String orsTiporegistPdia;

    private String orsEstadoregPdia;
}