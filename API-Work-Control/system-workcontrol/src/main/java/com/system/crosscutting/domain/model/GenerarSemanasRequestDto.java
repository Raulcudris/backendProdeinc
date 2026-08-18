package com.system.crosscutting.domain.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerarSemanasRequestDto {

    private String ordenKey;

    private LocalDate fechaInicial;

    private LocalDate fechaFinal;

    private Boolean incluirSabados;

    private Boolean incluirDomingos;
}