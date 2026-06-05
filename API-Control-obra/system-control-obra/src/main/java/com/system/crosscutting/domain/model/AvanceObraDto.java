package com.system.crosscutting.domain.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO de consulta para avance planeado vs ejecutado.
 *
 * No representa una tabla física. Es una respuesta calculada.
 */
@Getter
@Setter
public class AvanceObraDto {

    private String orsIdentifkeyOrde;

    private String orsIdentifkeyPltr;

    private String orsIdentifkeyPspl;

    private String descripcionPlan;

    private BigDecimal cantidadPlaneadaTotal;

    private BigDecimal cantidadPlaneadaSemana;

    private BigDecimal cantidadEjecutadaTotal;

    private BigDecimal cantidadEjecutadaSemana;

    private BigDecimal saldoPendienteTotal;

    private BigDecimal saldoPendienteSemana;

    private BigDecimal porcentajeAvanceTotal;

    private BigDecimal porcentajeAvanceSemana;

    private String unidadMedida;

    private String estadoAvance;
}