package com.system.crosscutting.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamdplantrabsemanaDto {

    private Integer orsPrimarykeyPlse;

    private String orsIdentifkeyPlse;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPltr;

    private String orsIdentifkeyPsem;

    private Integer orsCantidunidadPlse;

    private BigDecimal orsValorunidadPlse;

    private BigDecimal orsValortotalPlse;

    private Integer orsEjecutunidadPlse;

    private BigDecimal orsValorejecutPlse;

    private String orsTiporegistPlse;

    private String orsEstadoregPlse;
}