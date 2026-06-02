package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla DOCCATMACATEGORIA.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyDoccatmacategoriaDto {

    /**
     * Código secuencial autoincremental de categoría documental.
     */
    private Integer docPrimarykeyCado;

    /**
     * Código único de categoría documental.
     */
    private String docIdentifkeyCado;

    /**
     * Descripción de la categoría documental.
     */
    private String docDescripcionCado;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String docEstadoregCado;
}
