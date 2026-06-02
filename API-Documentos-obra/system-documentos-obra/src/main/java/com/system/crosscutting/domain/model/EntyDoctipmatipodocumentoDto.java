package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO que representa la información de la tabla DOCTIPMATIPODOCUMENTO.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyDoctipmatipodocumentoDto {

    /**
     * Código secuencial autoincremental de tipo documento.
     */
    private Integer docPrimarykeyTido;

    /**
     * Código único del tipo de documento.
     */
    private String docIdentifkeyTido;

    /**
     * Código único de la categoría documental.
     */
    private String docIdentifkeyCado;

    /**
     * Descripción del tipo de documento.
     */
    private String docDescripcionTido;

    /**
     * Indica si el tipo de documento requiere vencimiento: 1=Sí, 2=No.
     */
    private String docRequievenceTido;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String docEstadoregTido;
}
