package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla EQUMEDMAUNIDADMEDIDA.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * la conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyEqumedmaunidadmedidaDto {

    /**
     * Código secuencial autoincremental de unidad de medida.
     */
    private Integer equPrimarykeyUnme;

    /**
     * Código único de unidad de medida.
     */
    private String equIdentifkeyUnme;

    /**
     * Código corto de unidad de medida.
     */
    private String equCodigoUnme;

    /**
     * Descripción de la unidad de medida.
     */
    private String equDescripcionUnme;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String equEstadoregUnme;
}
