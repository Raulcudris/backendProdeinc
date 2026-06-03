package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla EVITIPMATIPOEVIDENCIA.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyEvitipmatipoevidenciaDto {

    /**
     * Código secuencial autoincremental del tipo de evidencia.
     */
    private Integer eviPrimarykeyTiev;

    /**
     * Código único funcional del tipo de evidencia.
     */
    private String eviIdentifkeyTiev;

    /**
     * Descripción del tipo de evidencia.
     */
    private String eviDescripcionTiev;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String eviEstadoregTiev;
}
