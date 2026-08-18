package com.system.crosscutting.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamdreportediarioDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orsPrimarykeyPdia;

    private String orsIdentifkeyPdia;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPlse;

    private String orsIdentifkeyPsem;

    private String orsObservacionPdia;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsFechareportPdia;

    private Integer orsEjecutunidadPdia;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsFechasistemaPdia;

    private String orsTiporegistPdia;

    private String orsEstadoregPdia;
}