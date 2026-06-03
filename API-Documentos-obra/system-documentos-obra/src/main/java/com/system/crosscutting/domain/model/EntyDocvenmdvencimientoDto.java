package com.system.crosscutting.domain.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla DOCVENMDVENCIMIENTO.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyDocvenmdvencimientoDto {

    /**
     * Código secuencial autoincremental del vencimiento documental.
     */
    private Integer docPrimarykeyVedo;

    /**
     * Código único funcional del vencimiento documental.
     */
    private String docIdentifkeyVedo;

    /**
     * Código único funcional del documento asociado.
     */
    private String docIdentifkeyDocu;

    /**
     * Fecha de vencimiento registrada.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate docFechavenceVedo;

    /**
     * Días antes del vencimiento para generar alerta.
     */
    private Integer docDiasalertaVedo;

    /**
     * Estado del vencimiento: 1=Vigente, 2=Próximo, 3=Vencido, 4=Renovado.
     */
    private String docEstadovencVedo;

    /**
     * Fecha de renovación del documento.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate docFecharenovaVedo;

    /**
     * Observaciones del vencimiento documental.
     */
    private String docObservacionVedo;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String docEstadoregVedo;
}