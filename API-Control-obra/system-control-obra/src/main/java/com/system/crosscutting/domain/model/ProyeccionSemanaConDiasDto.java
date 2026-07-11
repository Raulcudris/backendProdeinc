package com.system.crosscutting.domain.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProyeccionSemanaConDiasDto {

    private Integer orsPrimarykeyPsem;

    private String orsIdentifkeyPsem;

    private String orsIdentifkeyOrde;

    private Integer orsNumerosemPsem;

    private String orsTitulosemPsem;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsSemfechiniPsem;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsSemfechfinPsem;

    private String orsDiashabilesPsem;

    private String orsDiasnhabilesPsem;

    private String orsTiporegistPsem;

    private String orsEstadoregPsem;

    private List<EntyDiaProyeccionSemanaDto> dias = new ArrayList<>();
}