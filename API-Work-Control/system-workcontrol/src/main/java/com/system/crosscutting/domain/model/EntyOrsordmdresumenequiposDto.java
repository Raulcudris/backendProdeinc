package com.system.crosscutting.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsordmdresumenequiposDto {

    private Integer orsPrimarykeyRseq;

    private String orsIdentifkeyRseq;

    private String orsIdentifkeyOrde;

    private String prvTipoequipoTieq;

    private Integer orsCantidunidadRseq;

    private BigDecimal orsValorunidadRseq;

    private BigDecimal orsValortotalRseq;

    private String orsTiporegistRseq;

    private String orsEstadoregRseq;
}