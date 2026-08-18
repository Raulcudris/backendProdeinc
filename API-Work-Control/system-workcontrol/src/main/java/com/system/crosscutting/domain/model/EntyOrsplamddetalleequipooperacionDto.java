package com.system.crosscutting.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntyOrsplamddetalleequipooperacionDto {

    private Integer orsPrimarykeyDeop;

    private String orsIdentifkeyDeop;

    private String orsIdentifkeyRope;

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPsem;

    private String orsIdentifkeyPlse;

    private String orsIdentifkeyPunt;

    private String prvIdentifkeyInve;

    private String prvTipoequipoTieq;

    private String orsNombrequipoDeop;

    private String orsRefermodeloDeop;

    private String orsNroregistroDeop;

    private String orsUnidadDeop;

    private String orsTipocontrolDeop;

    private BigDecimal orsHorometroiniDeop;

    private BigDecimal orsHorometrofinDeop;

    private BigDecimal orsHorastrabajadasDeop;

    private BigDecimal orsKminicialDeop;

    private BigDecimal orsKmfinalDeop;

    private BigDecimal orsKmrecorridoDeop;

    private BigDecimal orsDiatrabajadoDeop;

    private BigDecimal orsValorunidadDeop;

    private BigDecimal orsValorejecutadoDeop;

    private LocalDate orsFechatrabajoDeop;

    private String orsObservacionDeop;

    private String orsFirmasuministroDeop;

    private String orsFirmaseguimientoDeop;

    private LocalDateTime orsFechasistemaDeop;

    private String orsTiporegistDeop;

    private String orsEstadoregDeop;
}