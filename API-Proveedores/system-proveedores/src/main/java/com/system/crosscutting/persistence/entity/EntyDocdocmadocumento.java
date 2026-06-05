package com.system.crosscutting.persistence.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

/**
 * Entidad JPA que representa la tabla DOCDOCMADOCUMENTO.
 *
 * Esta tabla almacena los documentos legales, técnicos, contractuales,
 * administrativos o de maquinaria cargados al sistema.
 */
@Getter
@Setter
@Entity
@Table(name = "DOCDOCMADOCUMENTO")
public class EntyDocdocmadocumento {

    /**
     * Código secuencial autoincremental del documento.
     */
    @Id
    @Column(name = "DOC_PRIMARYKEY_DOCU")
    private Integer docPrimarykeyDocu;

    /**
     * Código único funcional del documento.
     */
    @Column(name = "DOC_IDENTIFKEY_DOCU", length = 30, nullable = false)
    private String docIdentifkeyDocu;

    /**
     * Código único funcional del tipo de documento.
     */
    @Column(name = "DOC_IDENTIFKEY_TIDO", length = 20, nullable = false)
    private String docIdentifkeyTido;

    /**
     * Nombre del documento.
     */
    @Column(name = "DOC_NOMBRE_DOCU", length = 150, nullable = false)
    private String docNombreDocu;

    /**
     * Descripción del documento.
     */
    @Column(name = "DOC_DESCRIPCION_DOCU", columnDefinition = "TEXT")
    private String docDescripcionDocu;

    /**
     * Entidad emisora del documento.
     */
    @Column(name = "DOC_ENTIDAD_DOCU", length = 150)
    private String docEntidadDocu;

    /**
     * Fecha de expedición del documento.
     */
    @Column(name = "DOC_FECHAEXP_DOCU")
    private LocalDate docFechaexpDocu;

    /**
     * Fecha de vencimiento del documento.
     */
    @Column(name = "DOC_FECHAVENCE_DOCU")
    private LocalDate docFechavenceDocu;

    /**
     * URL o ruta del archivo documental.
     */
    @Column(name = "DOC_URLARCHIVO_DOCU", columnDefinition = "TEXT", nullable = false)
    private String docUrlarchivoDocu;

    /**
     * Tipo de referencia asociada al documento: EMPRESA, PROVEEDOR, EQUIPO, ORDEN_SERVICIO.
     */
    @Column(name = "DOC_TIPOREFEREN_DOCU", length = 40)
    private String docTiporeferenDocu;

    /**
     * Código identificador del registro referenciado.
     */
    @Column(name = "DOC_REFERENCIAID_DOCU", length = 30)
    private String docReferenciaidDocu;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "DOC_ESTADOREG_DOCU", length = 1, nullable = false)
    private String docEstadoregDocu;
}