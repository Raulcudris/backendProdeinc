package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla EQUTIPMATIPOEQUIPO.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * la conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyEqutipmatipoequipoDto {

    /**
     * Código secuencial autoincremental del tipo de equipo.
     */
    private Integer equPrimarykeyTieq;

    /**
     * Código único del tipo de equipo.
     */
    private String equIdentifkeyTieq;

    /**
     * Descripción del tipo de equipo.
     */
    private String equDescripcionTieq;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String equEstadoregTieq;
}