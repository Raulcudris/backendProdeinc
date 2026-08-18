package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsordmaactamodificacionDto {

    private Integer orsPrimarykeyAcmo;

    private String orsIdentifkeyAcmo;

    private String orsIdentifkeyOrde;

    private String orsNumeroactaAcmo;

    private LocalDate orsFechaactaAcmo;

    private String orsTipomodificacionAcmo;

    private String orsCausalAcmo;

    private BigDecimal orsValorinicialAcmo;

    private BigDecimal orsValormodificacionAcmo;

    private BigDecimal orsValoractualizadoAcmo;

    private BigDecimal orsSaldoaliberarAcmo;

    private String orsEstadoactaAcmo;

    private LocalDateTime orsFechasistemaAcmo;

    private String orsTiporegistAcmo;

    private String orsEstadoregAcmo;
}