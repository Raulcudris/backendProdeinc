package com.system.crosscutting.domain.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla EVIEVIMAEVIDENCIA.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyEvievimaevidenciaDto {

    /**
     * Código secuencial autoincremental de la evidencia.
     */
    private Integer eviPrimarykeyEvid;

    /**
     * Código único funcional de la evidencia.
     */
    private String eviIdentifkeyEvid;

    /**
     * Código único funcional del tipo de evidencia.
     */
    private String eviIdentifkeyTiev;

    /**
     * Nombre de la evidencia.
     */
    private String eviNombreEvid;

    /**
     * Descripción de la evidencia.
     */
    private String eviDescripcionEvid;

    /**
     * URL o ruta del archivo en almacenamiento externo.
     */
    private String eviUrlarchivoEvid;

    /**
     * Latitud donde fue capturada la evidencia.
     */
    private Float eviLatitudEvid;

    /**
     * Longitud donde fue capturada la evidencia.
     */
    private Float eviLongitudEvid;

    /**
     * Fecha y hora de captura de la evidencia.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eviFechacapturaEvid;

    /**
     * Usuario que registró la evidencia.
     */
    private String eviUsuariocreaEvid;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String eviEstadoregEvid;
}