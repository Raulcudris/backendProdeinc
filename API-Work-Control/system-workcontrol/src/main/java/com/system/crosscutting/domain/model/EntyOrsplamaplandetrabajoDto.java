package com.system.crosscutting.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamaplandetrabajoDto {

    private Integer orsPrimarykeyPltr;

    private String orsIdentifkeyPltr;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPunt;

    private String orsDesactividadPltr;

    private String orsIdentifkeyRseq;

    private String prvIdentifkeyInve;

    private Integer orsCantidunidadRseq;

    private BigDecimal orsValorunidadRseq;

    private BigDecimal orsValortotalRseq;

    private String orsTiporegistPltr;

    private String orsEstadoregPltr;
}