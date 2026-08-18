package com.system.crosscutting.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamdreporteoperacionDto {

    private Integer orsPrimarykeyRope;

    private String orsIdentifkeyRope;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPsem;

    private String orsIdentifkeyPlse;

    private String orsIdentifkeyPunt;

    private LocalDate orsFechareportRope;

    private String orsDepartamentoRope;

    private String orsMunicipioRope;

    private String orsSitioRope;

    private String prvIdentifkeyMprv;

    private Integer orsSemanaRope;

    private LocalDate orsSemfechiniRope;

    private LocalDate orsSemfechfinRope;

    private String orsDescsuministroRope;

    private String orsActividadhidraulicaRope;

    private String orsActividadjarillonesRope;

    private String orsActividadtaludesRope;

    private String orsActividadviasRope;

    private String orsActividadotroRope;

    private String orsObservacionRope;

    private String orsFirmasuministroRope;

    private String orsFirmaseguimientoRope;

    private LocalDateTime orsFechasistemaRope;

    private String orsTiporegistRope;

    private String orsEstadoregRope;
}