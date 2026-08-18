package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsordmaordenservicioDto {

    private Integer orsPrimarykeyOrde;

    private String orsIdentifkeyOrde;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsAutorifechaOrde;

    private String orsCodservicioSebs;

    private String orsServiceventOrde;

    private String orsServiclugarOrde;

    private String orsServicobjetoOrde;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsPlanfechiniOrde;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orsPlanfechfinOrde;

    private String prvIdentifkeyMprv;

    private String prvIdentifkeyRelg;

    private BigDecimal orsValorbaseOrde;

    private BigDecimal orsValordeivaOrde;

    private BigDecimal orsValortotalOrde;

    private String orsTiporegistOrde;

    private String orsEstadoregOrde;
}