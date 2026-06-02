package com.system.crosscutting.domain.model;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

/**
 * DTO que representa la información de la tabla DOCDOCMADOCUMENTO.
 *
 * Se mantienen los mismos nombres de atributos de la entidad para permitir
 * conversión automática mediante GsonUtil y el patrón Translator.
 */
@Getter
@Setter
public class EntyDocdocmadocumentoDto {

    /**
     * Código secuencial autoincremental del documento.
     */
    private Integer docPrimarykeyDocu;

    /**
     * Código único funcional del documento.
     */
    private String docIdentifkeyDocu;

    /**
     * Código único funcional del tipo de documento.
     */
    private String docIdentifkeyTido;

    /**
     * Nombre del documento.
     */
    private String docNombreDocu;

    /**
     * Descripción del documento.
     */
    private String docDescripcionDocu;

    /**
     * Entidad emisora del documento.
     */
    private String docEntidadDocu;

    /**
     * Fecha de expedición del documento.
     */
    private LocalDate docFechaexpDocu;

    /**
     * Fecha de vencimiento del documento.
     */
    private LocalDate docFechavenceDocu;

    /**
     * URL o ruta del archivo documental.
     */
    private String docUrlarchivoDocu;

    /**
     * Tipo de referencia asociada al documento.
     */
    private String docTiporeferenDocu;

    /**
     * Código identificador del registro referenciado.
     */
    private String docReferenciaidDocu;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    private String docEstadoregDocu;
}