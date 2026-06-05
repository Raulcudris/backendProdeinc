package com.system.crosscutting.persistence.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad JPA que representa la tabla DOCTIPMATIPODOCUMENTO.
 *
 * Esta tabla almacena los tipos de documentos asociados a una categoría documental,
 * indicando si requieren o no control de vencimiento.
 */
@Getter
@Setter
@Entity
@Table(name = "DOCTIPMATIPODOCUMENTO")
public class EntyDoctipmatipodocumento {

    /**
     * Código secuencial autoincremental de tipo documento.
     */
    @Id
    @Column(name = "DOC_PRIMARYKEY_TIDO")
    private Integer docPrimarykeyTido;

    /**
     * Código único del tipo de documento.
     */
    @Column(name = "DOC_IDENTIFKEY_TIDO", length = 20, nullable = false)
    private String docIdentifkeyTido;

    /**
     * Código único de la categoría documental.
     */
    @Column(name = "DOC_IDENTIFKEY_CADO", length = 20, nullable = false)
    private String docIdentifkeyCado;

    /**
     * Descripción del tipo de documento.
     */
    @Column(name = "DOC_DESCRIPCION_TIDO", length = 150, nullable = false)
    private String docDescripcionTido;

    /**
     * Indica si el tipo de documento requiere vencimiento: 1=Sí, 2=No.
     */
    @Column(name = "DOC_REQUIEVENCE_TIDO", length = 1, nullable = false)
    private String docRequievenceTido;

    /**
     * Estado del registro: 1=Activo, 2=Inactivo.
     */
    @Column(name = "DOC_ESTADOREG_TIDO", length = 1, nullable = false)
    private String docEstadoregTido;
}