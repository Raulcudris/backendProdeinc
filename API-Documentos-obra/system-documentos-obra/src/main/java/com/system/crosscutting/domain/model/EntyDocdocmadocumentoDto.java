package com.system.crosscutting.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private Integer docPrimarykeyDocu;

    private String docIdentifkeyDocu;

    private String docIdentifkeyTido;

    private String docNombreDocu;

    private String docDescripcionDocu;

    private String docEntidadDocu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate docFechaexpDocu;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate docFechavenceDocu;

    private String docUrlarchivoDocu;

    private String docTiporeferenDocu;

    private String docReferenciaidDocu;

    private String docEstadoregDocu;
}