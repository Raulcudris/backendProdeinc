package com.system.crosscutting.domain.model;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla EVIREFMDREFERENCIA.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyEvirefmdreferenciaDto {

    /**
     * Código secuencial autoincremental de la referencia de evidencia.
     */
    private Integer eviPrimarykeyEvre;

    /**
     * Código único funcional de referencia de evidencia.
     */
    private String eviIdentifkeyEvre;

    /**
     * Código único funcional de la evidencia.
     */
    private String eviIdentifkeyEvid;

    /**
     * Tipo de referencia.
     */
    private String eviTiporeferenEvre;

    /**
     * Código identificador del registro referenciado.
     */
    private String eviReferenciaidEvre;

    /**
     * Observación de la referencia.
     */
    private String eviObservacionEvre;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String eviEstadoregEvre;
}
