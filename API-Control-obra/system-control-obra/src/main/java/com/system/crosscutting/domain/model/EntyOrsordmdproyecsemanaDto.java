package com.system.crosscutting.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsordmdproyecsemanaDto {

    private Integer orsPrimarykeyPsem;

    private String orsIdentifkeyPsem;

    private String orsIdentifkeyOrde;

    private Integer orsNumerosemPsem;

    private String orsTitulosemPsem;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsSemfechiniPsem;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate orsSemfechfinPsem;

    private String orsDiashabilesPsem;

    private String orsDiasnhabilesPsem;

    private String orsTiporegistPsem;

    private String orsEstadoregPsem;
}