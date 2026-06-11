package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsordmdactamodificaciondetalleDto {

    private Integer orsPrimarykeyAcmd;

    private String orsIdentifkeyAcmd;

    private String orsIdentifkeyAcmo;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyRseq;

    private String prvTipoequipoTieq;

    private String orsDescripcionEquipoAcmd;

    private String orsUnidadAcmd;

    private BigDecimal orsCantidadoriginalAcmd;

    private BigDecimal orsValorunitarioAcmd;

    private BigDecimal orsValororiginalAcmd;

    private BigDecimal orsCantidadanteriorAcmd;

    private BigDecimal orsValoranteriorAcmd;

    private BigDecimal orsCantidadmodificadaAcmd;

    private BigDecimal orsValormodificadoAcmd;

    private BigDecimal orsCantidadactualizadaAcmd;

    private BigDecimal orsValoractualizadoAcmd;

    private String orsObservacionAcmd;

    private LocalDateTime orsFechasistemaAcmd;

    private String orsTiporegistAcmd;

    private String orsEstadoregAcmd;
}